package com.benrostudios.vithackapp.ui.auth.userPhone

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.benrostudios.vithackapp.R

class UserPhone : Fragment() {

    companion object {
        fun newInstance() = UserPhone()
    }

    private lateinit var viewModel: UserPhoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_phone_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserPhoneViewModel::class.java)
        // TODO: Use the ViewModel
    }

}