package com.benrostudios.vithackapp.ui.home.faq

import android.view.ViewManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.FaqRepository


@Suppress("Unchecked_cast")
class FaqViewModelFactory(private val faqRepository: FaqRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FaqViewModel(faqRepository) as T
    }
}