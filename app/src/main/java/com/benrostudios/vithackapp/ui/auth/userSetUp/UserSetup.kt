package com.benrostudios.vithackapp.ui.auth.userSetUp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benrostudios.vithackapp.R

class UserSetup : Fragment() {

    companion object {
        fun newInstance() = UserSetup()
    }

    private lateinit var viewModel: UserSetupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserSetupViewModel::class.java)
        // TODO: Use the ViewModel
    }

}