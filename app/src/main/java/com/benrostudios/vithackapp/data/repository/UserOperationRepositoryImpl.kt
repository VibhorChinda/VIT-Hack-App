package com.benrostudios.vithackapp.data.repository

import android.util.EventLog
import android.util.Log
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.data.models.User
import com.benrostudios.vithackapp.utils.Event
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserOperationRepositoryImpl : UserOperationRepository {

    private lateinit var databaseReference: DatabaseReference
    private var _checkUserStatus = MutableLiveData<Event<Boolean>>()
    private var _upsertUserStatus = MutableLiveData<Boolean>()
    private var _fetchedUser = MutableLiveData<User>()

    override suspend fun checkUser(uid: String) {
        databaseReference = Firebase.database.getReference("/users/$uid")
        val checkUserFetcher = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                _checkUserStatus.postValue(Event(false))
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _checkUserStatus.postValue(Event(true))
                } else {
                    _checkUserStatus.postValue(Event(false))
                }
            }
        }
        databaseReference.addListenerForSingleValueEvent(checkUserFetcher)
    }

    override suspend fun upsertUser(user: User) {
        databaseReference = Firebase.database.getReference("/users")
        databaseReference.child(user.uid).setValue(user).addOnSuccessListener {
            _upsertUserStatus.postValue(true)
        }
    }

    override suspend fun fetchUser(uid: String) {
        databaseReference = Firebase.database.getReference("/users/$uid")
        val userFetcher = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseCancelled","User Cancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user: User? = snapshot.getValue(User::class.java)
                    user.let {
                        _fetchedUser.postValue(it)
                    }
                } else {
                    _fetchedUser.postValue(User())
                }
            }

        }
        databaseReference.addListenerForSingleValueEvent(userFetcher)
    }


    override val fetchedUser: LiveData<User>
        get() = _fetchedUser

    override fun updateFcmToken(token: String, uid: String) {
        Firebase.database.getReference("/users/$uid").child("fcmToken").setValue(token)
            .addOnFailureListener {
                Log.d("tokenUpdate", "$it")
            }
    }

    override val userCheckStatus: LiveData<Event<Boolean>>
        get() = _checkUserStatus
    override val upsertUserStatus: LiveData<Boolean>
        get() = _upsertUserStatus
}