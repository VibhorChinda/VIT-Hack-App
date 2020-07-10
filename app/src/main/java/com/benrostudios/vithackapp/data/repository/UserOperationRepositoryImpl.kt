package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.data.models.User
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserOperationRepositoryImpl : UserOperationRepository {

    private lateinit var databaseReference: DatabaseReference
    private var _checkUserStatus = MutableLiveData<Boolean>()
    private var _upsertUserStatus = MutableLiveData<Boolean>()
    private var _fetchedUser = MutableLiveData<User>()

    override suspend fun checkUser(uid: String) {
        databaseReference = Firebase.database.getReference("/users/$uid")
        val checkUserFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                _checkUserStatus.postValue(false)
            }
            override fun onDataChange(snapshot: DataSnapshot) {
               if(snapshot.exists()){
                   _checkUserStatus.postValue(true)
               }else{
                   _checkUserStatus.postValue(false)
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
        val userFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val user: User? = snapshot.getValue(User::class.java)
                    user.let {
                        _fetchedUser.postValue(it)
                    }
                }
            }

        }
    }


    override val fetchedUser: LiveData<User>
        get() = _fetchedUser
    override val userCheckStatus: LiveData<Boolean>
        get() = _checkUserStatus
    override val upsertUserStatus: LiveData<Boolean>
        get() = _upsertUserStatus
}