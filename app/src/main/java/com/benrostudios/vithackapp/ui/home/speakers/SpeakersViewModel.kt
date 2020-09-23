package com.benrostudios.vithackapp.ui.home.speakers

import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.SpeakersRepository

class SpeakersViewModel(private val speakersRepository: SpeakersRepository) : ViewModel() {
    val speakersList
        get() = speakersRepository.speakersList

    suspend fun fetchSpeakers() {
        speakersRepository.fetchSpeakers()
    }
}