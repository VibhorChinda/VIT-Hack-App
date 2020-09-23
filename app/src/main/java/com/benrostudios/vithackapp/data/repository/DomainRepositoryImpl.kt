package com.benrostudios.vithackapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.data.models.Domain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DomainRepositoryImpl : DomainRepository {

    private val _domainsListData = MutableLiveData<List<Domain>>()
    private var _domainsList: MutableList<Domain> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference

    override fun fetchDomains() {
        databaseReference = Firebase.database.getReference("/domains")
        val domainFetcher = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("database", "Domains Cancelled")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (domain in snapshot.children) {
                        Log.d("repo","lolol ${snapshot.children}")
                        val currentDomain = domain.getValue(Domain::class.java)
                        currentDomain?.let {
                            if (!_domainsList.contains(it)) {
                                _domainsList.add(it)

                            }
                        }
                    }
                    _domainsListData.postValue(_domainsList)
                }else{
                    Log.d("repo","empty")
                }
            }

        }
        databaseReference.addListenerForSingleValueEvent(domainFetcher)
    }

    override val fetchedDomains: LiveData<List<Domain>>
        get() = _domainsListData
}