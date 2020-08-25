package com.benrostudios.vithackapp.ui.home.timeline

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.adapters.FaqAdapter
import com.benrostudios.vithackapp.adapters.TimelineAdapter
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.faq.FaqViewModel
import com.benrostudios.vithackapp.ui.home.faq.FaqViewModelFactory
import kotlinx.android.synthetic.main.faq_fragment.*
import kotlinx.android.synthetic.main.timeline_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Timeline : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: TimelineViewModelFactory by instance()
    private lateinit var adapter: TimelineAdapter

    companion object {
        fun newInstance() =
            Timeline()
    }

    private lateinit var viewModel: TimelineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timeline_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TimelineViewModel :: class.java)
        timeline_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        fetchTimeLine()
    }

    private fun fetchTimeLine() = launch {
        viewModel.fetchTimeline()
        viewModel.fetchedTimeline.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()) {
                adapter = TimelineAdapter(it)
                timeline_recycler_view.adapter = adapter
            }
        })
    }
}
