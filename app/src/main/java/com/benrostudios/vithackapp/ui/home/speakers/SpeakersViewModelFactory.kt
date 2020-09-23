package com.benrostudios.vithackapp.ui.home.speakers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.SpeakersRepository


@Suppress("Unchecked_cast")
class SpeakersViewModelFactory(private val speakersRepository: SpeakersRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SpeakersViewModel(speakersRepository) as T
    }
}