package com.benrostudios.vithackapp.ui.fragments.userDetails

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.general_details_form_fragment.*

class GeneralDetailsFormFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.general_details_form_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initialize()
    }

    @SuppressLint("CommitPrefEdits")
    private fun initialize() {
        sharedPreference = this.requireActivity().getSharedPreferences("UserSharedPreference", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        submit_general_details_button.setOnClickListener {
            navigate()
        }
    }

    private fun navigate() {
        if (user_name_edit_text.text != null && user_name_edit_text.text.isNotEmpty() && phone_number_edit_text.text != null
            && phone_number_edit_text.text.isNotEmpty()) {
            if (vitian_check_box.isChecked) {
                editor.putString("User Name", user_name_edit_text.text.toString())
                editor.putString("Phone Number", phone_number_edit_text.text.toString())
                editor.apply()
                navController.navigate(R.id.action_generalDetailsForm_to_vitDetailsForm)
            }
            else {
                editor.putString("Registration Number", null)
                editor.putString("Room Number", null)
                editor.apply()
                navController.navigate(R.id.action_generalDetailsForm_to_companyDetailsFragment)
            }
        }
        else {
            Toast.makeText(context, "Enter all the text fields", Toast.LENGTH_SHORT).show()
        }
    }
}
