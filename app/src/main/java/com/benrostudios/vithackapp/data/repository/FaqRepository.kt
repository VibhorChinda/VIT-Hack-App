package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.vithackapp.data.models.FAQ

interface FaqRepository {
    suspend fun fetchFaqs()
    val fetchedFaqs : LiveData<List<FAQ>>
    suspend fun postFaq(question: String)
    val postFaqStatus: LiveData<Boolean>
}