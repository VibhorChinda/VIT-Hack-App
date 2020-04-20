package com.benrostudios.vithackapp.ui.auth.userLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class UserLoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val response = MutableLiveData<Boolean>()

    suspend fun firebaseCreateWithEmailPassword(email: String, password: String) {
        authRepository.firebaseCreateWithEmailPassword(email,password)
    }


    suspend fun firebaseSignInWithEmailPassword(email: String, password: String){
        authRepository.firebaseSignInWithEmailPassword(email,password)
    }

    suspend fun firebaseCreateWithGoogle(acct: GoogleSignInAccount) {
       authRepository.firebaseCreateWithGoogle(acct)
    }

    fun getAuthStatus(): LiveData<Boolean>{
        authRepository.getAuthStatus().observeForever {
            response.postValue(it)
        }
        return response
    }



}
