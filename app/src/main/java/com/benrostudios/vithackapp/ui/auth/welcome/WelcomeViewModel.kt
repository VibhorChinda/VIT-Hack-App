package com.benrostudios.vithackapp.ui.auth.welcome

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class WelcomeViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val response
        get() = authRepository.getAuthStatus

    suspend fun firebaseCreateWithGoogle(acct: GoogleSignInAccount) {
        authRepository.firebaseCreateWithGoogle(acct)
    }
}