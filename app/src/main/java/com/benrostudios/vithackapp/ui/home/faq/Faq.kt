package com.benrostudios.vithackapp.ui.home.faq

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.adapters.FaqAdapter
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.dynamicfaq.DynamicFaq
import com.benrostudios.vithackapp.utils.hide
import com.benrostudios.vithackapp.utils.show
import kotlinx.android.synthetic.main.faq_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class Faq : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FaqViewModelFactory by instance()
    private lateinit var adapter: FaqAdapter

    companion object {
        fun newInstance() = Faq()
    }

    private lateinit var viewModel: FaqViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.faq_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FaqViewModel::class.java)
        faq_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        faq_recyclerView.hide()
        faq_progress.show()
        fetchFaq()
        ask_faq_btn.setOnClickListener {
            val dialogFragment = DynamicFaq()
            dialogFragment.show(activity?.supportFragmentManager!!, dialogFragment.tag)
        }
        searchViewImplementation()
    }

    private fun fetchFaq() = launch {
        viewModel.fetchFaqs()
        viewModel.fetchedFaqs.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                faq_progress.hide()
                faq_recyclerView.show()
                adapter = FaqAdapter(it)
                faq_recyclerView.adapter = adapter
            }
        })
    }

    private fun searchViewImplementation() {
        search_container.setOnClickListener {
            faq_search_view.isIconified = false
        }
        faq_search_view.setOnCloseListener {
            search_title.show()
            false
        }
        faq_search_view.setOnSearchClickListener {
            search_title.hide()
        }

        faq_search_view.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                adapter.filter.filter(newText)

                return true
            }
        })


    }

}