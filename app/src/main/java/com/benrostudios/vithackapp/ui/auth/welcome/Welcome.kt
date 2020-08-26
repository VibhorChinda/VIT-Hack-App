package com.benrostudios.vithackapp.ui.auth.welcome

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
import com.benrostudios.vithackapp.ui.auth.AuthActivity
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.welcome_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Welcome : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: WelcomeViewModelFactory by instance()
    private lateinit var navController: NavController
    private val RC_SIGN_IN = 7
    private lateinit var googleSignInClient: GoogleSignInClient
    private val sharedPrefUtils: SharedPrefUtils by instance()
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun newInstance() = Welcome()
    }

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel::class.java)
        sign_up_now_button.setOnClickListener {
            navController.navigate(R.id.action_welcome_to_userSignUp)
        }
        login_text.setOnClickListener {
            navController.navigate(R.id.action_welcome_to_userSignIn)
        }
        continue_with_google_button.setOnClickListener {
            googleSignIn()
        }

    }


    private fun googleSignIn() {
        welcome_progress.show()
        welcome_progress_text.show()
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

    private fun signInWithGoogle(account: GoogleSignInAccount) = launch {
        viewModel.firebaseCreateWithGoogle(account)
        viewModel.response.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                welcome_container.successSnackBar("Successful Authentication!")
                welcome_progress_text.text = resources.getString(R.string.fetching_user_info)

                firebaseAuth = FirebaseAuth.getInstance()
                val user = firebaseAuth.currentUser
                user?.let { data ->
                    sharedPrefUtils.setEmailId(data.email ?: "")
                }
                updateUI()
            } else {
                welcome_container.errorSnackBar("Error Signing In")
                welcome_progress.hide()
                welcome_progress_text.hide()
            }
        })

    }

    private fun updateUI() = launch {
        viewModel.checkUser(firebaseAuth.uid.toString())
        viewModel.userChecker.observe(viewLifecycleOwner, Observer {
            if (it) {
                val intent = Intent(context, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            } else {
                navController.navigate(R.id.action_welcome_to_userSetup)
            }
        })

    }


}