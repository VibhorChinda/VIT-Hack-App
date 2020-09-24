package com.benrostudios.vithackapp.ui.splash

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.auth.AuthActivity
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.ui.onBoarding.OnBoardingActivity
import com.benrostudios.vithackapp.utils.EventObserver
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.benrostudios.vithackapp.utils.show
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_splash.*
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

    private val SPLASH_TIME_OUT = 3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        if (sharedPrefUtils.getFirstTimeOpen()) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    sharedPrefUtils.setUiMode(true)
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    sharedPrefUtils.setUiMode(false)
                }
            }
        } else {
            val uiMode = sharedPrefUtils.getUiMode()
            if (uiMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SplashActivityViewModel::class.java)
        initialize()
    }

    private fun initialize() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash)
        val splashImageView = iv_splash
        Glide.with(this)
            .asGif()
            .load(R.drawable.vithack_animation)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource is GifDrawable) {
                        resource.setLoopCount(1)
                    }
                    return false;
                }

            })
            .into(splashImageView)
        if (sharedPrefUtils.getFirstTimeOpen()) {
            Handler().postDelayed(
                {
                    sharedPrefUtils.setFirstTimeOpen(false)
                    startActivity(Intent(this, OnBoardingActivity::class.java))
                    finish()
                }, SPLASH_TIME_OUT
            )
        } else {
            FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    val token = task.result?.token
                    if (token != null) {
                        sharedPrefUtils.setFCMToken(token)
                    }
                    userChecker()
                })
        }

    }

    private fun userChecker() {
        if (firebaseAuth.currentUser == null) {
            Handler().postDelayed(
                {
                    navigationHelper(false)
                }, SPLASH_TIME_OUT
            )

        } else {
            splash_progress.show()
            user_info_fetcher_display.show()
            viewModel.checkUser(firebaseAuth.uid.toString())
            viewModel.checkerUser.observe(this, EventObserver {
                val navTruth = if (it) {
                    true
                } else {
                    firebaseAuth.signOut()
                    false
                }
                navigationHelper(navTruth)
            })
        }
    }


    private fun navigationHelper(truth: Boolean) {
        //False = Auth , True = HomeActivity
        val intent: Intent =
            if (truth) {
                Intent(this, HomeActivity::class.java)
            } else {
                firebaseAuth.signOut()
                Intent(this, AuthActivity::class.java)
            }
        sharedPrefUtils.setFirstTimeOpen(false)
        startActivity(intent)
        finish()
    }
}