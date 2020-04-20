package com.benrostudios.vithackapp.data.repository

import android.app.Activity
import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface AuthRepository {
    suspend fun firebaseCreateWithEmailPassword(email: String, password: String)
    suspend fun firebaseSignInWithEmailPassword(email: String, password: String)
    suspend fun firebaseCreateWithGoogle(acct: GoogleSignInAccount)
    fun getAuthStatus(): LiveData<Boolean>
}