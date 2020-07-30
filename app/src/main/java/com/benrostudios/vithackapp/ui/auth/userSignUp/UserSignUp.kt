package com.benrostudios.vithackapp.ui.auth.userSignUp



import android.os.Bundle
import android.util.Log
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
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.benrostudios.vithackapp.utils.isValidAlphaNumeric
import com.benrostudios.vithackapp.utils.isValidEmail
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
    private lateinit var emailId: String
    private val sharedPrefUtils: SharedPrefUtils by instance()

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


        //EmailPasswordButton
        sign_up_button.setOnClickListener {
            if (email_input.isValidEmail() && password_input.isValidAlphaNumeric("Password")) {
                authListener()
                firebaseCreateWithEmailPassword(
                    email_input.text.toString(),
                    password_input.text.toString()
                )
            }
        }

    }

    private fun firebaseCreateWithEmailPassword(email: String, password: String) = launch {
        emailId = email
        viewModel.firebaseCreateWithEmailPassword(email, password)

    }

    private fun authListener() = launch {
        viewModel.response.observe(viewLifecycleOwner, Observer {
            if (it) {
                Log.d("Login", "Success")
                sharedPrefUtils.setEmailId(emailId)
                updateUI()
            } else {
                Log.d("Login", "Failure  from UserLogin")
                Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun updateUI() {
        Log.d("Login", "CalledFromUser")
        navController.navigate(R.id.action_userSignUp_to_userSetup)
    }

}
