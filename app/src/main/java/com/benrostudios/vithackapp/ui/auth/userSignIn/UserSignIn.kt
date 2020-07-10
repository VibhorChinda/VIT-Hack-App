package com.benrostudios.vithackapp.ui.auth.userSignIn

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
import com.benrostudios.vithackapp.ui.auth.AuthActivity
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.ui.usersetup.ProfileSetupActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.user_sign_in_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UserSignIn : ScopedFragment(), KodeinAware {
    private val RC_SIGN_IN = 7
    private lateinit var googleSignInClient: GoogleSignInClient
    override val kodein by closestKodein()
    private val viewModelFactory: UserSignInViewModelFactory by instance()
    private lateinit var navController: NavController

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

        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


        sign_in_button.setOnClickListener {
            authListener()
            val email = email_input.text
            if (email.isValidEmail()) {
                authListener()
                signInWithEmailPassword(
                    email.toString(),
                    password_input.text.toString()
                )
            } else {
                Toast.makeText(activity, R.string.invalid_email_toast, Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun googleSignIn() {
        initGoogleSignInClient()
        startGoogleSignIn()
    }

    private fun initGoogleSignInClient() {
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(activity as AuthActivity, googleSignInOptions)
    }

    private fun startGoogleSignIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                signInWithGoogle(account!!)
            } catch (e: ApiException) {
                Log.w("Login", "Google sign in failed", e)
            }

        }
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

    private fun signInWithEmailPassword(email: String, password: String) = launch {
        viewModel.firebaseSignInWithEmailPassword(email, password)

    }

    private fun signInWithGoogle(account: GoogleSignInAccount) = launch {
        viewModel.firebaseCreateWithGoogle(account)
        updateUI()
    }

    private fun updateUI() {
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}
