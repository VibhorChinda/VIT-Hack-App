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

open class BaseActivity : AppCompatActivity() {

    val networkState = MutableLiveData<Event<Boolean>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkManager()
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
                    networkState.postValue(Event(true))
                }

                override fun onLost(network: Network) {
                    if (cm.activeNetwork == null) {
                        Log.d("Base", "OFFLINE")
                        networkState.postValue(Event(false))
                    }
                }
            }
        )
    }
}