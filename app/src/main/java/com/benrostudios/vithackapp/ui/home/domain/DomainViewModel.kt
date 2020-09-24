package com.benrostudios.vithackapp.ui.home.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.models.Domain
import com.benrostudios.vithackapp.data.repository.DomainRepository

class DomainViewModel(
    private val domainRepository: DomainRepository
) : ViewModel() {

    val fetchedDomains
        get() = domainRepository.fetchedDomains

    fun fetchDomains() {
        domainRepository.fetchDomains()
    }

}