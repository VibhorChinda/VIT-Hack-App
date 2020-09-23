package com.benrostudios.vithackapp.ui.home.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.DomainRepository


@Suppress("UNCHECKED_CAST")
class DomainViewModelFactory(private val domainRepository: DomainRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DomainViewModel(domainRepository) as T
    }

}