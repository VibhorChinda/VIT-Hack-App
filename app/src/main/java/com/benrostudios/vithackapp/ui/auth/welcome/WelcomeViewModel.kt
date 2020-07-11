package com.benrostudios.vithackapp.ui.auth.welcome

import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.AuthRepository
import com.benrostudios.vithackapp.data.repository.UserOperationRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class WelcomeViewModel(
    private val authRepository: AuthRepository,
    private val userOperationRepository: UserOperationRepository
) : ViewModel() {

    val response
        get() = authRepository.getAuthStatus
    val userChecker
        get() = userOperationRepository.userCheckStatus

    suspend fun firebaseCreateWithGoogle(acct: GoogleSignInAccount) {
        authRepository.firebaseCreateWithGoogle(acct)
    }

    suspend fun checkUser(uid: String){
        userOperationRepository.checkUser(uid)
    }
}