package com.benrostudios.vithackapp.ui.auth.userLogin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class UserLoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val response = MutableLiveData<Boolean>()

    fun FirebaseCreateWithEmailPassword(email: String, password: String): LiveData<Boolean> {
        authRepository.FirebaseCreateWithEmailPassword(email, password).observeForever {
            response.postValue(it)
            Log.d("Login","ViewModel Says "+ it.toString())
        }
        return response
    }

    fun FirebaseSignInWithEmailPassword(email: String, password: String){
        authRepository.FirebaseSignInWithEmailPassword(email, password).observeForever {
            response.postValue(it)
        }

    }

    fun FirebaseCreateWithGoogle(acct: GoogleSignInAccount) {
        authRepository.FirebaseCreateWithGoogle(acct).observeForever {
            response.postValue(it)
        }
    }



}
