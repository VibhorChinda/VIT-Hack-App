package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.vithackapp.data.models.Company
import com.benrostudios.vithackapp.data.models.Speaker


interface SpeakersRepository {
    suspend fun fetchSpeakers()
    val speakersList: LiveData<List<Speaker>>
    suspend fun fetchCollaborators()
    suspend fun fetchSponsors()
    val collaborators: LiveData<List<Company>>
    val sponsors: LiveData<List<Company>>
}