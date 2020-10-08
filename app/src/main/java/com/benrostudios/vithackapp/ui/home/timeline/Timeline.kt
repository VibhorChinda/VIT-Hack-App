package com.benrostudios.vithackapp.ui.home.timeline

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.adapters.TimelineAdapter
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.timeline_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception

class Timeline : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: TimelineViewModelFactory by instance()
    private lateinit var adapter: TimelineAdapter
    private var firstVisibleInListview = 0

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
        viewModel = ViewModelProvider(this, viewModelFactory).get(TimelineViewModel::class.java)
        btn_join_discord.setOnClickListener {
            launchDiscord()
        }
        val layoutManager = LinearLayoutManager(requireContext())
        timeline_recycler_view.layoutManager = layoutManager
        timeline_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    if (btn_join_discord != null) {
                        btn_join_discord.visibility = View.GONE
                    }
                } else {
                    // Scrolling down
                    if (btn_join_discord != null) {
                        btn_join_discord.visibility = View.VISIBLE
                    }
                }
            }
        })
        fetchTimeLine()
    }

    private fun fetchTimeLine() = launch {
        viewModel.fetchTimeline()
        viewModel.fetchedTimeline.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                adapter = TimelineAdapter(it)
                timeline_recycler_view.adapter = adapter
            }
        })
    }

    private fun launchDiscord() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/UX26KdG"))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "No Internet Browser found to handle request",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
