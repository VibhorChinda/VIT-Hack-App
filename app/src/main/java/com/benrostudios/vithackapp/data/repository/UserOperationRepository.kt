package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.vithackapp.data.models.User
import com.benrostudios.vithackapp.utils.Event

interface UserOperationRepository {
    suspend fun checkUser(uid: String)
    suspend fun upsertUser(user: User)
    suspend fun fetchUser(uid: String)
    val userCheckStatus: LiveData<Event<Boolean>>
    val upsertUserStatus: LiveData<Boolean>
    val fetchedUser : LiveData<User>
    fun updateFcmToken(token: String, uid: String)
}