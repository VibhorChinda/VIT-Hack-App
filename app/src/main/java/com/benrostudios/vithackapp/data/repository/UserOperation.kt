package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.vithackapp.data.models.User

interface UserOperation {
    suspend fun checkUser(uid: String)
    suspend fun upsertUser(user: User, uid: String)
    suspend fun fetchUser(uid: String)
    val userCheckStatus: LiveData<Boolean>
    val upsertUserStatus: LiveData<Boolean>
    val fetchedUser : LiveData<User>
}