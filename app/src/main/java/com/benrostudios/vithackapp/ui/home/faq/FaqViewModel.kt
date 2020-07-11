package com.benrostudios.vithackapp.ui.home.faq

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benrostudios.vithackapp.data.models.FAQ
import com.benrostudios.vithackapp.data.repository.FaqRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

     fun postFaq(question: String, time: String) {
         viewModelScope.launch(Dispatchers.IO){
             faqRepository.postFaq(question,time)
         }

    }

}