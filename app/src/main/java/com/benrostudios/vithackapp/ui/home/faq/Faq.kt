package com.benrostudios.vithackapp.ui.home.faq

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
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.dynamicfaq.DynamicFaq
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
        viewModel = ViewModelProvider(this,viewModelFactory).get(FaqViewModel::class.java)
        faq_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        fetchFaq()
        test_faq_btn.setOnClickListener {
            val dialogFragment = DynamicFaq()
            dialogFragment.show(activity?.supportFragmentManager!!,dialogFragment.tag)
        }
    }

    private fun fetchFaq() = launch {
        viewModel.fetchFaqs()
        viewModel.fetchedFaqs.observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                adapter = FaqAdapter(it)
                faq_recyclerView.adapter = adapter
            }
        })
    }

}