package com.benrostudios.vithackapp.ui.fragments.userDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.benrostudios.vithackapp.R
import kotlinx.android.synthetic.main.user_login_fragment.*
import kotlinx.android.synthetic.main.vit_details_form_fragment.*

class VitDetailsFormFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.vit_details_form_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initialize()
    }

    private fun initialize() {
        sharedPreferences = this.requireActivity().getSharedPreferences("UserSharedPreference", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        submit_vit_details_button.setOnClickListener {
            navigate()
        }
    }

    private fun navigate() {
        if(reg_number_edit_text.text != null && reg_number_edit_text.text.isNotEmpty() && room_number_edit_text.text != null
            && room_number_edit_text.text.isNotEmpty()) {
            editor.putString("Registration Number", reg_number_edit_text.text.toString())
            editor.putString("Room Number", room_number_edit_text.text.toString())
            editor.apply()
            navController.navigate(R.id.action_vitDetailsForm_to_companyDetailsFragment2)
        }
        else {
            Toast.makeText(context, "Enter all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
