package com.benrostudios.vithackapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.auth.AuthActivity
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.google.firebase.auth.FirebaseAuth
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SplashActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val sharedPrefUtils: SharedPrefUtils by instance()
    private val viewModelFactory: SplashActivityViewModelFactory by instance()
    private lateinit var viewModel: SplashActivityViewModel
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var navigationSwitch: Boolean = false //False = Auth , True = HomeActivity

    private val SPLASH_TIME_OUT = 1000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SplashActivityViewModel::class.java)
    }

    private fun initialize() {
        userChecker()
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(
            {
                val intent: Intent =
                    if (navigationSwitch) {
                        Intent(this, HomeActivity::class.java)
                    } else {
                        Intent(this, AuthActivity::class.java)
                    }
                startActivity(intent)
                sharedPrefUtils.setFirstTimeOpen(false)
                finish()
            }, SPLASH_TIME_OUT
        )
    }

    private fun userChecker() {
        if (firebaseAuth.currentUser == null) {
            navigationSwitch = false
        } else {
            viewModel.checkUser(firebaseAuth.uid.toString())
            viewModel.checkerUser.observeForever {
                navigationSwitch = if (it) {
                    true
                } else {
                    firebaseAuth.signOut()
                    false
                }
            }
        }


    }
}