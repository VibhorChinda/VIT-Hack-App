package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.vithackapp.data.models.Domain

interface DomainRepository {
    fun fetchDomains()
    val fetchedDomains:  LiveData<List<Domain>>
}