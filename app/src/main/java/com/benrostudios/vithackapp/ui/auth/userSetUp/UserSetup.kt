package com.benrostudios.vithackapp.ui.auth.userSetUp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.utils.isValidAlphaNumeric
import kotlinx.android.synthetic.main.user_setup_fragment.*

class UserSetup : Fragment() {

    private lateinit var navController: NavController

    companion object {
        fun newInstance() = UserSetup()
        const val NAME = "name"
        const val INSTITUTION_NAME = "institutionName"
        const val REGISTRATION_NUMBER = "regNo"
    }

    private lateinit var viewModel: UserSetupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_setup_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserSetupViewModel::class.java)
        about_you_continue_btn.setOnClickListener {
            if (validateInputs()) {
                var bundle: Bundle = Bundle()
                bundle.putString(NAME, name_input.text.toString())
                bundle.putString(INSTITUTION_NAME, institution_input.text.toString())
                bundle.putString(REGISTRATION_NUMBER, reg_input.text.toString())
                navController.navigate(R.id.action_userSetup_to_userPhone, bundle)
            }
        }
    }

    private fun validateInputs(): Boolean {
        return name_input.isValidAlphaNumeric("Name") &&
                institution_input.isValidAlphaNumeric("Institution Name") &&
                reg_input.isValidAlphaNumeric("Registration Number")
    }
}