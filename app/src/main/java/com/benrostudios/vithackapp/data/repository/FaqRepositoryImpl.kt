package com.benrostudios.vithackapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.vithackapp.data.models.FAQ
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FaqRepositoryImpl : FaqRepository {

    private lateinit var databaseReference : DatabaseReference
    private var _fetchedFaqs = MutableLiveData<List<FAQ>>()
    private var _faqList = mutableListOf<FAQ>()
    private var _postFaqStatus = MutableLiveData<Boolean>()


    override suspend fun fetchFaqs() {
        databaseReference = Firebase.database.getReference("/FAQs")
        val faqFetcher = object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(x in snapshot.children){
                        val faqObj = x.getValue(FAQ::class.java)
                        faqObj?.let {
                            if(!_faqList.contains(it)){
                                _faqList.add(it)
                            }
                        }
                    }
                    _fetchedFaqs.postValue(_faqList)
                }
            }
        }
        databaseReference.addValueEventListener(faqFetcher)
    }



    override suspend fun postFaq(question: String) {
        databaseReference = Firebase.database.getReference("/QFAQs")
        databaseReference.child("question").setValue(question).addOnSuccessListener {
            _postFaqStatus.postValue(true)
        }
    }

    override val fetchedFaqs: LiveData<List<FAQ>>
        get() = _fetchedFaqs

    override val postFaqStatus: LiveData<Boolean>
        get() = _postFaqStatus
}