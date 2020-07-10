package com.benrostudios.vithackapp.ui.auth.userPhone

import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.models.User
import com.benrostudios.vithackapp.data.repository.UserOperationRepository

class UserPhoneViewModel(
    private val userOperationRepository: UserOperationRepository
) : ViewModel() {

    val userUpsertStatus
        get() = userOperationRepository.upsertUserStatus

    suspend fun userUpsert(user: User){
        userOperationRepository.upsertUser(user)
    }
}