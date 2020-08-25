package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import com.benrostudios.vithackapp.data.models.TimeLine

interface TimelineRepository {
    suspend fun fetchTimeline()
    val fetchedTimeline : LiveData<List<TimeLine>>
}