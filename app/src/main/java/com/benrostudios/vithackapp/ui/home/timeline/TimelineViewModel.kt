package com.benrostudios.vithackapp.ui.home.timeline

import androidx.lifecycle.ViewModel
import com.benrostudios.vithackapp.data.repository.TimelineRepository

class TimelineViewModel(
    private val timelineRepository: TimelineRepository)
    : ViewModel() {

    val fetchedTimeline
        get() = timelineRepository.fetchedTimeline

    suspend fun fetchTimeline() {
        timelineRepository.fetchTimeline()
    }
}
