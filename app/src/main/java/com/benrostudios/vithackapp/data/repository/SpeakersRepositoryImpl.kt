package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.data.models.Speaker
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SpeakersRepositoryImpl : SpeakersRepository {
    private val _speakerListLive = MutableLiveData<List<Speaker>>()
    private val _speakersList: MutableList<Speaker> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference

    override suspend fun fetchSpeakers() {
        databaseReference = Firebase.database.getReference("/speakers")
        val speakerFetcher = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (speakers in snapshot.children) {
                        val currentSpeaker = speakers.getValue(Speaker::class.java)
                        currentSpeaker?.let {
                            if (!_speakersList.contains(it)) {
                                _speakersList.add(it)
                            }
                        }

                    }
                    _speakerListLive.postValue(_speakersList)
                }
            }

        }
        databaseReference.addValueEventListener(speakerFetcher)
    }

    override val speakersList: LiveData<List<Speaker>>
        get() = _speakerListLive
}