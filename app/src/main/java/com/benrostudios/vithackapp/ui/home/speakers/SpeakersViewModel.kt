package com.benrostudios.vithackapp.ui.home.speakers

import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.SpeakersRepository

class SpeakersViewModel(private val speakersRepository: SpeakersRepository) : ViewModel() {
    val speakersList
        get() = speakersRepository.speakersList

    val sponsorList
        get() = speakersRepository.sponsors

    val collaboratorsList
        get() = speakersRepository.collaborators

    suspend fun fetchSpeakers() {
        speakersRepository.fetchSpeakers()
    }

    suspend fun fetchSponsors() {
        speakersRepository.fetchSponsors()
    }

    suspend fun fetchCollaborators() {
        speakersRepository.fetchCollaborators()
    }
}