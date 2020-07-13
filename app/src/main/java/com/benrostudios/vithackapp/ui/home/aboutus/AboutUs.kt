package com.benrostudios.vithackapp.ui.home.aboutus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.adapters.TeamAdapter
import com.benrostudios.vithackapp.data.premade.devData
import kotlinx.android.synthetic.main.fragment_about_us.*


class AboutUs : Fragment() {

    private lateinit var adapter: TeamAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        team_recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        fetchTeam()
    }

    private fun fetchTeam(){
        adapter = TeamAdapter(devData)
        team_recyclerView.adapter = adapter
    }
}