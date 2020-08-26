package com.benrostudios.vithackapp.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.utils.Event
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val response = MutableLiveData<Event<Boolean>>()

    override suspend fun firebaseCreateWithEmailPassword(
        email: String,
        password: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    response.postValue(Event(true))
                } else {
                    response.postValue(Event(false))
                }
                Log.d("Login", "It says: " + it.isSuccessful + "Response is: " + response)
            }
    }

    override suspend fun firebaseSignInWithEmailPassword(
        email: String,
        password: String
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    response.postValue(Event(true))
                } else {
                    response.postValue(Event(false))
                }
            }
    }

    override suspend fun firebaseCreateWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    response.postValue(Event(true))
                } else {
                    response.postValue(Event(false))
                }
            }
    }

    override val getAuthStatus: LiveData<Event<Boolean>>
        get() = response

}