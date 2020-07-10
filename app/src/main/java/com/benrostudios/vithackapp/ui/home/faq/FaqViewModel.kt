package com.benrostudios.vithackapp.ui.home.faq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.models.FAQ
import com.benrostudios.vithackapp.data.repository.FaqRepository

class FaqViewModel(
    private val faqRepository: FaqRepository
) : ViewModel() {

    val fetchedFaqs
        get() = faqRepository.fetchedFaqs

    val postFaqStatus
        get() = faqRepository.postFaqStatus

    suspend fun fetchFaqs() {
        faqRepository.fetchFaqs()
    }

    suspend fun postFaq(question: String) {
        faqRepository.postFaq(question)
    }

}