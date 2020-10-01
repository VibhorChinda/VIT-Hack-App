package com.benrostudios.vithackapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.data.models.Company
import com.benrostudios.vithackapp.data.models.Speaker
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SpeakersRepositoryImpl : SpeakersRepository {
    private val _speakerListLive = MutableLiveData<List<Speaker>>()
    private val _speakersList: MutableList<Speaker> = mutableListOf()
    private val _fetchedSponsors = MutableLiveData<List<Company>>()
    private val _fetchedCollaborators = MutableLiveData<List<Company>>()
    private val _sponsorsList: MutableList<Company> = mutableListOf()
    private val _collaboratorsList: MutableList<Company> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference

    override suspend fun fetchSpeakers() {
        databaseReference = Firebase.database.getReference("/speakers")
        val speakerFetcher = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseCancelled", "Speakers Cancelled")
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

    override suspend fun fetchCollaborators() {
        databaseReference = Firebase.database.getReference("/collaborators")
        val fetchCollaborators = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Database", "Collaborators cancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (collaboratorData in snapshot.children) {
                        val collaborator = collaboratorData.getValue(Company::class.java)
                        collaborator?.let {
                            if (!_collaboratorsList.contains(it)) {
                                _collaboratorsList.add(it)
                            }
                        }
                    }
                    _fetchedCollaborators.postValue(_collaboratorsList)
                }
            }

        }
        databaseReference.addValueEventListener(fetchCollaborators)
    }

    override suspend fun fetchSponsors() {
        databaseReference = Firebase.database.getReference("/sponsors")
        val fetchCollaborators = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Database", "Sponsor cancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (sponsorData in snapshot.children) {
                        val sponsor = sponsorData.getValue(Company::class.java)
                        sponsor?.let {
                            if (!_sponsorsList.contains(it)) {
                                _sponsorsList.add(it)
                            }
                        }
                    }
                    _fetchedSponsors.postValue(_sponsorsList)
                }
            }

        }
        databaseReference.addValueEventListener(fetchCollaborators)
    }

    override val speakersList: LiveData<List<Speaker>>
        get() = _speakerListLive

    override val collaborators: LiveData<List<Company>>
        get() = _fetchedCollaborators

    override val sponsors: LiveData<List<Company>>
        get() = _fetchedSponsors
}