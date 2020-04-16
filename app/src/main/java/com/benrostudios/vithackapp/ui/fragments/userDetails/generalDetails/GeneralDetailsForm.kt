package com.benrostudios.vithackapp.ui.fragments.userDetails.generalDetails

import androidx.lifecycle.ViewModelProviders
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

class GeneralDetailsForm : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: GeneralDetailsFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.general_details_form_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        submit_general_details_button.setOnClickListener {
            navigate()
        }
        viewModel = ViewModelProviders.of(this).get(GeneralDetailsFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun navigate() {
        if (user_name_edit_text.text != null && user_name_edit_text.text.isNotEmpty() && phone_number_edit_text.text != null
            && phone_number_edit_text.text.isNotEmpty()) {
            if (vitian_check_box.isChecked) {
                navController.navigate(R.id.action_generalDetailsForm_to_vitDetailsForm)
            }
            else {
                Toast.makeText(context, "Check the text box", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(context, "Enter all the text fields", Toast.LENGTH_SHORT).show()
        }
    }
}
