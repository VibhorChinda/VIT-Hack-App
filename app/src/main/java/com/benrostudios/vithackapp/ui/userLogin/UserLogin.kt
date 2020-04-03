package com.benrostudios.vithackapp.ui.userLogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.MainActivity
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.user_login_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class UserLogin : ScopedFragment(), KodeinAware {
    private val RC_SIGN_IN = 7
    private lateinit var googleSignInClient: GoogleSignInClient
    override val kodein by closestKodein()
    private val viewModelFactory: ViewModelProvider.Factory by instance()
    private var mCallback: SwitchAuthenticatedUserFrag? = null
    private lateinit var viewModel: UserLoginViewModel

    companion object {
        fun newInstance() = UserLogin()
    }

    interface SwitchAuthenticatedUserFrag {
        fun switchAuthenticatedUser()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_login_fragment, container, false)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mCallback = activity as SwitchAuthenticatedUserFrag
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement TextClicked"
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserLoginViewModel::class.java)
        //GoogleButton
        google_sign_in.setOnClickListener {
            googleSignIn()
        }
        fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
        //EmailPasswordButton
        login_button.setOnClickListener {
            var email = email_input.text
            if(email.isValidEmail()) {
                FirebaseCreateWithEmailPassword(
                    email.toString(),
                    password_input.text.toString()
                )
            }else{
                Toast.makeText(activity,R.string.invalid_email_toast,Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun FirebaseCreateWithEmailPassword(email: String , password:String)= launch{
        viewModel.FirebaseCreateWithEmailPassword(email, password).observeForever {
            if (it) {
                Log.d("Login", "Success")
                upadateUI()
            } else {
                Log.d("Login", "Failure  from UserLogin")
                Toast.makeText(activity,"Error",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun googleSignIn() {
        initGoogleSignInClient()
        signIn2()

    }

    private fun initGoogleSignInClient() {
        val googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(activity as MainActivity, googleSignInOptions)
    }

    private fun signIn2() {
        val signInIntent: Intent = googleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                viewModel.FirebaseCreateWithGoogle(account!!)
                upadateUI()
            } catch (e: ApiException) {
                Log.w("Login", "Google sign in failed", e)
            }

        }
    }

    fun upadateUI(){
        Log.d("Login","CalledFromUser")
        mCallback?.switchAuthenticatedUser()
    }


}
