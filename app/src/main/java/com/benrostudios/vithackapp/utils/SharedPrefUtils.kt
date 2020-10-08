package com.benrostudios.vithackapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPrefUtils(
    private val context: Context
) {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Data", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor

    init {
        editor = sharedPreferences.edit()
    }

    companion object {
        const val SHARED_PREFERENCE_REACHED_HOME = "reachedHome"
        const val SHARED_PREFERENCE_FIRST_TIME_OPEN = "firstTimeOpen"
        const val SHARED_PREFERENCE_FCM_TOKEN = "fcmToken"
        const val SHARED_PREFERENCE_MAIL_ID = "email"
        const val SHARED_PREFERENCE_UI_MODE = "ui"
    }

    fun setEmailId(email: String) {
        editor.putString(SHARED_PREFERENCE_MAIL_ID, email).commit()
    }

    fun getEmailId(): String? = sharedPreferences.getString(SHARED_PREFERENCE_MAIL_ID, "")

    fun setFCMToken(fcmToken: String) {
        editor.putString(SHARED_PREFERENCE_FCM_TOKEN, fcmToken).commit()
    }

    fun getFCMToken(): String? = sharedPreferences.getString(SHARED_PREFERENCE_FCM_TOKEN, "")

    fun setFirstTimeOpen(truth: Boolean) {
        editor.putBoolean(SHARED_PREFERENCE_FIRST_TIME_OPEN, truth).commit()
    }

    fun getFirstTimeOpen(): Boolean =
        sharedPreferences.getBoolean(SHARED_PREFERENCE_FIRST_TIME_OPEN, true)

    fun setHomeReached(truth: Boolean) {
        editor.putBoolean(SHARED_PREFERENCE_REACHED_HOME, truth).commit()
    }

    fun getHomeReached(): Boolean =
        sharedPreferences.getBoolean(SHARED_PREFERENCE_REACHED_HOME, false)

    fun setUiMode(truth: Boolean) {
        Log.d("setUI","$truth")
        editor.putBoolean(SHARED_PREFERENCE_UI_MODE, truth).commit()
    }

    fun getUiMode(): Boolean =
        sharedPreferences.getBoolean(SHARED_PREFERENCE_UI_MODE, true) //True = Light Mode


    fun nuke() {
        editor.clear().commit()
    }
}