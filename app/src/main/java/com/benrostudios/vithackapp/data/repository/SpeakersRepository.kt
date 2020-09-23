package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.vithackapp.data.models.Speaker


interface SpeakersRepository {
    suspend fun fetchSpeakers()
    val speakersList: LiveData<List<Speaker>>
}