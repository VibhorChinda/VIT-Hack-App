package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.data.models.FAQ
import com.benrostudios.vithackapp.data.models.TimeLine
import com.benrostudios.vithackapp.ui.home.timeline.Timeline
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TimelineRepositoryImpl : TimelineRepository {

    private lateinit var databaseReference: DatabaseReference
    private var _fetchedTimeline = MutableLiveData<List<TimeLine>>()
    private var timelineList = mutableListOf<TimeLine>()

    override suspend fun fetchTimeline() {
        databaseReference = Firebase.database.getReference("/timeline")
        val fetchTimeline = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for(x in snapshot.children) {
                        val timelineObj = x.getValue(TimeLine :: class.java)
                        timelineObj?.let {
                            timelineList.add(timelineObj)
                        }
                    }
                    _fetchedTimeline.postValue(timelineList)
                }
            }
        }
        databaseReference.addValueEventListener(fetchTimeline)
    }

    override val fetchedTimeline: LiveData<List<TimeLine>>
        get() = _fetchedTimeline
}