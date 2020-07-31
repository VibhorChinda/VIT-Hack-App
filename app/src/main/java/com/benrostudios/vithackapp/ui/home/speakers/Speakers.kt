package com.benrostudios.vithackapp.ui.home.speakers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.vithackapp.R
import kotlinx.android.synthetic.main.speakers_fragment.*
import kotlinx.android.synthetic.main.user_setup_fragment.*

class Speakers : Fragment() {
    private lateinit var navController: NavController
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
        viewModel = ViewModelProviders.of(this).get(SpeakersViewModel::class.java)
        about_us_btn.setOnClickListener {
            navController.navigate(R.id.action_speakers_to_aboutUs)
        }
        // TODO: Use the ViewModel
    }

}