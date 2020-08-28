package com.benrostudios.vithackapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.usersetup.ProfileSetupActivity
import com.benrostudios.vithackapp.ui.auth.userSignUp.UserSignUp
import com.benrostudios.vithackapp.ui.base.BaseActivity
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.utils.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_home.*

class AuthActivity : BaseActivity() {
    private var initOpen: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        networkState.observe(this, EventObserver {
            if (it) {
                if (initOpen != 0) {
                    no_connection_view.hide()
                    nav_host_fragment_auth_activity.show()
                    auth_activity_container.successSnackBar("You are back online!")
                } else {
                    initOpen = 1
                }
            } else {
                nav_host_fragment_auth_activity.hide()
                no_connection_view.show()
                val snack = Snackbar.make(
                    no_connection_view,
                    "You are offline!",
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setBackgroundTint(resources.getColor(R.color.error_red))
                snack.anchorView = bottom_navigation_view_home_activity
                snack.show()
            }
        })
    }

}
