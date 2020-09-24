package com.benrostudios.vithackapp.ui.home.domain

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
import com.benrostudios.vithackapp.adapters.DomainAdapter
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.domain_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DomainFragment : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: DomainViewModelFactory by instance()
    private lateinit var adapter: DomainAdapter

    companion object {
        fun newInstance() = DomainFragment()
    }

    private lateinit var viewModel: DomainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.domain_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(DomainViewModel::class.java)
        domains_recycler_view.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL ,false)
        fetchDomains()
        listenFetchedDomains()
    }

    private fun listenFetchedDomains() = launch{
        viewModel.fetchedDomains.observe(viewLifecycleOwner, Observer {
            if(it != null){
                adapter = DomainAdapter(it)
                domains_recycler_view.adapter = adapter
            }
        })
    }
    private fun fetchDomains() = launch{
        viewModel.fetchDomains()
    }

}