package com.benrostudios.vithackapp.ui.home.speakers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.adapters.CompanyAdapter
import com.benrostudios.vithackapp.adapters.SpeakerAdapter
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.aboutus.AboutUs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_about_us.*
import kotlinx.android.synthetic.main.speakers_fragment.*
import kotlinx.android.synthetic.main.user_setup_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Speakers : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SpeakersViewModelFactory by instance()
    private lateinit var navController: NavController
    private var collaboratorsList: MutableList<String> = mutableListOf()
    private var sponsorsList: MutableList<String> = mutableListOf()
    private lateinit var collaboratorsAdapter: CompanyAdapter
    private lateinit var sponsorsAdapter: CompanyAdapter
    private lateinit var speakersAdapter: SpeakerAdapter

    companion object {
        fun newInstance() = Speakers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private lateinit var viewModel: SpeakersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.speakers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SpeakersViewModel::class.java)
        about_us_btn.setOnClickListener {
            navController.navigate(R.id.action_speakers_to_aboutUs)
        }
        val linearLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager3 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        collborators_recyclerView.layoutManager = linearLayoutManager
        sponsor_recycler.layoutManager = linearLayoutManager2
        speakers_recyclerView.layoutManager = linearLayoutManager3
        inflateCollaborators()
        inflateSponsors()
        fetchSpeakers()
    }

    private fun inflateCollaborators() {
        for (i in 1..3) {
            collaboratorsList.add("Testing/collaborator$i.png")
        }
        collaboratorsAdapter = CompanyAdapter(collaboratorsList)
        collborators_recyclerView.adapter = collaboratorsAdapter

    }

    private fun inflateSponsors() {
        for (i in 1..3) {
            sponsorsList.add("Testing/sponsor$i.png")
        }
        sponsorsAdapter = CompanyAdapter(sponsorsList)
        sponsor_recycler.adapter = sponsorsAdapter
    }

    private fun fetchSpeakers() = launch {
        viewModel.fetchSpeakers()
        viewModel.speakersList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                speakersAdapter = SpeakerAdapter(it.asReversed())
                speakers_recyclerView.adapter = speakersAdapter
            }
        })
    }

}