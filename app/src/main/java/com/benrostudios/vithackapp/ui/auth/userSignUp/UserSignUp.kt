package com.benrostudios.vithackapp.ui.auth.userSignUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.usersetup.ProfileSetupActivity
import kotlinx.android.synthetic.main.user_sign_up_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class UserSignUp : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: UserSignUpViewModelFactory by instance()
    private lateinit var viewModel: UserSignUpViewModel
    private lateinit var navController: NavController
    companion object {
        fun newInstance() = UserSignUp()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_sign_up_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserSignUpViewModel::class.java)


        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
        //EmailPasswordButton
        sign_up_button.setOnClickListener {
            val email = email_input.text
            if (email.isValidEmail()) {
                authListener()
                firebaseCreateWithEmailPassword(
                    email.toString(),
                    password_input.text.toString()
                )
            } else {
                Toast.makeText(activity, R.string.invalid_email_toast, Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun firebaseCreateWithEmailPassword(email: String, password: String) = launch {
        viewModel.firebaseCreateWithEmailPassword(email, password)

    }

    private fun authListener() = launch {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it) {
                Log.d("Login", "Success")
                updateUI()
            } else {
                Log.d("Login", "Failure  from UserLogin")
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }


    fun updateUI() {
        Log.d("Login", "CalledFromUser")
        val intent = Intent(context,ProfileSetupActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }


}
