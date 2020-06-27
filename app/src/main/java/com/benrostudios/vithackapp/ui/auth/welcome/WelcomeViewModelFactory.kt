package com.benrostudios.vithackapp.ui.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.AuthRepository


@Suppress("UNCHECKED_CAST")
class WelcomeViewModelFactory(
    private val authRepository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WelcomeViewModel(authRepository) as T
    }
}