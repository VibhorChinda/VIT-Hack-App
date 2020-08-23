package com.benrostudios.vithackapp.ui.auth.userSignIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.utils.*
import kotlinx.android.synthetic.main.user_sign_in_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UserSignIn : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: UserSignInViewModelFactory by instance()
    private lateinit var navController: NavController
    private val sharedPrefUtils: SharedPrefUtils by instance()
    private lateinit var emailId: String

    companion object {
        fun newInstance() = UserSignIn()
    }

    private lateinit var viewModel: UserSignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_sign_in_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserSignInViewModel::class.java)



        sign_in_button.setOnClickListener {
            if (email_input.isValidEmail() && password_input.isValidAlphaNumeric(
                    "Password"
                )
            ) {
                sign_in_button.hide()
                sign_in_progress.show()
                authListener()
                signInWithEmailPassword(
                    email_input.text.toString(),
                    password_input.text.toString()
                )
            }
        }


    }

    private fun authListener() = launch {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it) {
                Log.d("Login", "Success")
                sharedPrefUtils.setEmailId(emailId)
                updateUI()
            } else {
                sign_in_progress.hide()
                sign_in_button.show()
                Log.d("Login", "Failure  from UserLogin")
                sign_in_container.errorSnackBar("Invalid Credentials")
                //Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun signInWithEmailPassword(email: String, password: String) = launch {
        emailId = email
        viewModel.firebaseSignInWithEmailPassword(email, password)

    }


    private fun updateUI() {
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
