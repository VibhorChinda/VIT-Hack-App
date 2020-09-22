package com.benrostudios.vithackapp.utils

import android.content.Context
import android.content.SharedPreferences

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
        const val SHARED_PREFERENCE_FIRST_TIME_OPEN = "firstTimeOpen"
        const val SHARED_PREFERENCE_FCM_TOKEN = "fcmToken"
        const val SHARED_PREFERENCE_MAIL_ID = "email"
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
        editor.putBoolean(SHARED_PREFERENCE_FIRST_TIME_OPEN, truth)
    }

    fun getFirstTimeOpen(): Boolean =
        sharedPreferences.getBoolean(SHARED_PREFERENCE_FIRST_TIME_OPEN, true)

    fun nuke() {
        editor.clear()
    }
}