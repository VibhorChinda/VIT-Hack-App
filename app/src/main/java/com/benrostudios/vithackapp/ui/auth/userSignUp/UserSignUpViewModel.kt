package com.benrostudios.vithackapp.ui.auth.userSignUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class UserSignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    val response = MutableLiveData<Boolean>()

    init {
        authRepository.getAuthStatus.observeForever {
            response.postValue(it)
        }
    }

    suspend fun firebaseCreateWithEmailPassword(email: String, password: String) {
        authRepository.firebaseCreateWithEmailPassword(email, password)
    }
}
