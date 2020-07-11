package com.benrostudios.vithackapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.UserOperationRepository



@Suppress("UNCHECKED_CAST")
class SplashActivityViewModelFactory(
    private val userOperationRepository: UserOperationRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashActivityViewModel(userOperationRepository) as T
    }
}