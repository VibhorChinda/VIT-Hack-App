package com.benrostudios.vithackapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.vithackapp.data.repository.UserOperationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivityViewModel(
    private val userOperationRepository: UserOperationRepository
): ViewModel() {
    val checkerUser
        get() = userOperationRepository.userCheckStatus

    fun checkUser(uid: String){
        viewModelScope.launch(Dispatchers.IO){
            userOperationRepository.checkUser(uid)
        }
    }
}