package com.benrostudios.vithackapp.data.repository



import android.app.Activity
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class AuthRepositoryImpl: AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val response = MutableLiveData<Boolean>()

    override fun FirebaseCreateWithEmailPassword(
        email: String,
        password: String
    ): LiveData<Boolean> {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    response.postValue(true)
                } else {
                    response.postValue(false)
                }
                Log.d("Login","It says: "+it.isSuccessful+"Response is: "+response)
            }
        return response
    }

    override fun FirebaseSignInWithEmailPassword(
        email: String,
        password: String
    ): LiveData<Boolean> {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    response.postValue(true)
                } else {
                    response.postValue(false)
                }
            }
        return response
    }

    override fun FirebaseCreateWithGoogle(acct: GoogleSignInAccount): LiveData<Boolean> {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    response.postValue(true)
                } else {
                    response.postValue(false)
                }
            }
        return response
    }

}