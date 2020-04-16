package com.benrostudios.vithackapp.ui.auth.userLogin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.AuthRepository

@Suppress("UNCHECKED_CAST")
class UserLoginViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserLoginViewModel(authRepository) as T
    }
}