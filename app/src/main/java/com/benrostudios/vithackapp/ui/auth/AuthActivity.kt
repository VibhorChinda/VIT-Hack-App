package com.benrostudios.vithackapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.usersetup.ProfileSetupActivity
import com.benrostudios.vithackapp.ui.auth.userSignUp.UserSignUp
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

}
