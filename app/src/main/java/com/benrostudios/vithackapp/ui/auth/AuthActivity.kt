package com.benrostudios.vithackapp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.auth.authenticatedLogin.AuthenticatedUser
import com.benrostudios.vithackapp.ui.auth.userLogin.UserLogin
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity(),UserLogin.SwitchAuthenticatedUserFrag,AuthenticatedUser.SwitchLoginFrag {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (firebaseAuth.currentUser == null) {
            replaceFragment(UserLogin())
        } else {
            replaceFragment(AuthenticatedUser())
        }

    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().addToBackStack("")
            .replace(R.id.auth_frame, fragment).commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.auth_frame, fragment).commit()
    }

    override fun switchAuthenticatedUser() {
        replaceFragment(AuthenticatedUser())
    }

    override fun switchToLogin() {
        firebaseAuth.signOut()
        replaceFragment(UserLogin())
    }


}
