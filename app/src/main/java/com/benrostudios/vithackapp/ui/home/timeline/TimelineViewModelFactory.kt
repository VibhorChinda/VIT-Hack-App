package com.benrostudios.vithackapp.ui.home.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.data.repository.TimelineRepository

@Suppress("UNCHECKED_CAST")
class TimelineViewModelFactory(private val timelineRepository: TimelineRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimelineViewModel(timelineRepository) as T
    }
}