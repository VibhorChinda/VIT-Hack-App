package com.benrostudios.vithackapp.ui.auth.userPhone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.UserOperationRepository


@Suppress("UNCHECKED_CAST")
class UserPhoneViewModelFactory(
    private val userOperationRepository: UserOperationRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserPhoneViewModel(userOperationRepository) as T
    }
}