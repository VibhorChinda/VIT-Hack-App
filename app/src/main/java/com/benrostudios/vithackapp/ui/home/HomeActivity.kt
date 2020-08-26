package com.benrostudios.vithackapp.ui.home

import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.base.BaseActivity
import com.benrostudios.vithackapp.utils.errorSnackBar
import com.benrostudios.vithackapp.utils.successSnackBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private lateinit var navController: NavController
    private var initOpen : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navController = findNavController(R.id.nav_host_fragment_home_activity)
        bottom_navigation_view_home_activity.setupWithNavController(navController)
        networkState.observeForever {
            if(it){
                if(initOpen!=0){
                    val snack = Snackbar.make(home_activity_container, "You are online!", Snackbar.LENGTH_LONG)
                    snack.setBackgroundTint(resources.getColor(R.color.success_green))
                    snack.anchorView = bottom_navigation_view_home_activity
                    snack.show()
                }else{
                    initOpen = 1
                }
            }else{
                val snack = Snackbar.make(home_activity_container, "You are offline!", Snackbar.LENGTH_INDEFINITE)
                snack.setBackgroundTint(resources.getColor(R.color.error_red))
                snack.anchorView = bottom_navigation_view_home_activity
                snack.show()
            }
        }
    }
}
