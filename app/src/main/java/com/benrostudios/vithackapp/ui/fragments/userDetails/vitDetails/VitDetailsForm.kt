package com.benrostudios.vithackapp.ui.fragments.userDetails.vitDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.benrostudios.vithackapp.R

class VitDetailsForm : Fragment() {

    companion object {
        fun newInstance() = VitDetailsForm()
    }

    private lateinit var viewModel: VitDetailsFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vit_details_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VitDetailsFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
