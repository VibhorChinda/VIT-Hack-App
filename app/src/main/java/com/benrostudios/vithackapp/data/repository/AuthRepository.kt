package com.benrostudios.vithackapp.data.repository

import android.app.Activity
import androidx.lifecycle.LiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface AuthRepository {
    fun FirebaseCreateWithEmailPassword(email: String, password: String): LiveData<Boolean>
    fun FirebaseSignInWithEmailPassword(email: String, password: String): LiveData<Boolean>
    fun FirebaseCreateWithGoogle(acct: GoogleSignInAccount): LiveData<Boolean>
}