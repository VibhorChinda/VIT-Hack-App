package com.benrostudios.vithackapp.ui.home.domain.tracks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.adapters.TracksAdapter
import com.benrostudios.vithackapp.data.models.Domain
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_tracks_bottom_sheet.*


class TracksBottomSheet : BottomSheetDialogFragment() {

    private lateinit var domain: Domain
    private lateinit var adapter: TracksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_tracks_bottom_sheet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        domain = arguments?.getSerializable("domains_list") as Domain
        tracks_recycler_view.layoutManager = LinearLayoutManager(requireActivity())
        adapter = TracksAdapter(domain.problemStatements, domain.abbreviation)
        tracks_recycler_view.adapter = adapter
    }
}