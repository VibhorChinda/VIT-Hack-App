package com.benrostudios.vithackapp.ui.home

import android.content.res.Configuration
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.base.BaseActivity
import com.benrostudios.vithackapp.utils.EventObserver
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.benrostudios.vithackapp.utils.errorSnackBar
import com.benrostudios.vithackapp.utils.successSnackBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class HomeActivity : BaseActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private lateinit var navController: NavController
    private val sharedPrefUtils: SharedPrefUtils by instance()
    private var initOpen: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPrefUtils.setFirstTimeOpen(false)
        val uiMode = sharedPrefUtils.getUiMode()
        if (!uiMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController = findNavController(R.id.nav_host_fragment_home_activity)
        bottom_navigation_view_home_activity.setupWithNavController(navController)
        networkState.observe(this, EventObserver {
            if (it) {
                if (initOpen != 0) {
                    val snack = Snackbar.make(
                        home_activity_container,
                        "You are online!",
                        Snackbar.LENGTH_LONG
                    )
                    snack.setBackgroundTint(resources.getColor(R.color.success_green))
                    snack.anchorView = bottom_navigation_view_home_activity
                    snack.show()
                } else {
                    initOpen = 1
                }
            } else {
                val snack = Snackbar.make(
                    home_activity_container,
                    "You are offline!",
                    Snackbar.LENGTH_INDEFINITE
                )
                snack.setBackgroundTint(resources.getColor(R.color.error_red))
                snack.anchorView = bottom_navigation_view_home_activity
                snack.show()
            }
        })
    }

    fun switchUiMode() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                sharedPrefUtils.setUiMode(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                sharedPrefUtils.setUiMode(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
        this.recreate()
    }

}
