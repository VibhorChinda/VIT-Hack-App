package com.benrostudios.vithackapp.ui.auth

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log.d
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.base.BaseActivity
import com.benrostudios.vithackapp.utils.EventObserver
import com.benrostudios.vithackapp.utils.hide
import com.benrostudios.vithackapp.utils.show
import com.benrostudios.vithackapp.utils.successSnackBar
import com.google.android.gms.common.util.IOUtils.copyStream
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_home.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class AuthActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        networkState.observe(this, EventObserver {
            if (it) {
                    game_web_view.hide()
                    nav_host_fragment_auth_activity.show()
                    auth_activity_container.successSnackBar("You are back online!")

            } else {
                nav_host_fragment_auth_activity.hide()
                game_web_view.show()
                initialiseGame()
                val snack = Snackbar.make(
                    game_web_view,
                    "You are offline!",
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setBackgroundTint(resources.getColor(R.color.error_red))
                snack.anchorView = bottom_navigation_view_home_activity
                snack.show()
            }
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initialiseGame() {
        game_web_view.loadUrl("file:///android_asset/index.html")
        val webSettings: WebSettings = game_web_view.settings
        webSettings.javaScriptEnabled = true
    }

}
