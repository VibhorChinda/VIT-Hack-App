package com.benrostudios.vithackapp.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.utils.Event
import com.benrostudios.vithackapp.utils.hide
import com.benrostudios.vithackapp.utils.show
import com.benrostudios.vithackapp.utils.successSnackBar
import kotlinx.android.synthetic.main.activity_auth.*

open class BaseActivity : AppCompatActivity() {
    private var initOpen: Int = 0

    val networkState = MutableLiveData<Event<Boolean>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkManager()
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo == null) {
            networkState.postValue(Event(false))
            initOpen = 1
        }
    }


    private fun networkManager() {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder: NetworkRequest.Builder = NetworkRequest.Builder()
        cm.registerNetworkCallback(
            builder.build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Log.d("Base", "ONLINE")
                    if (initOpen != 0) {
                        networkState.postValue(Event(true))
                    } else {
                        initOpen = 1
                    }
                }

                override fun onLost(network: Network) {
                    Log.d("Base", "Offline " + cm.activeNetwork)
                    if (cm.activeNetwork == null) {
                        Log.d("Base", "OFFLINE")
                        networkState.postValue(Event(false))
                    }
                }
            }
        )
    }
}