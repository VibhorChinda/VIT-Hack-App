package com.benrostudios.vithackapp.ui.auth.userSignIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class UserSignInViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val response
        get() = authRepository.getAuthStatus

    suspend fun firebaseSignInWithEmailPassword(email: String, password: String) {
        authRepository.firebaseSignInWithEmailPassword(email, password)
    }

    suspend fun firebaseCreateWithGoogle(acct: GoogleSignInAccount) {
        authRepository.firebaseCreateWithGoogle(acct)
    }

}