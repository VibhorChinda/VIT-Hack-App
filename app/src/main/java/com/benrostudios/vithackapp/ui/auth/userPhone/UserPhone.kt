package com.benrostudios.vithackapp.ui.auth.userPhone


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.data.models.User
import com.benrostudios.vithackapp.ui.auth.userSetUp.UserSetup.Companion.INSTITUTION_NAME
import com.benrostudios.vithackapp.ui.auth.userSetUp.UserSetup.Companion.NAME
import com.benrostudios.vithackapp.ui.auth.userSetUp.UserSetup.Companion.REGISTRATION_NUMBER
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.benrostudios.vithackapp.utils.isValidPhone
import com.benrostudios.vithackapp.utils.shortToaster
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.user_phone_fragment.*
import kotlinx.android.synthetic.main.user_setup_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class UserPhone : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: UserPhoneViewModelFactory by instance()
    private lateinit var name: String
    private lateinit var institution: String
    private lateinit var registrationNumber: String
    private val sharedPrefUtils: SharedPrefUtils by instance()

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
        viewModel = ViewModelProvider(this,viewModelFactory).get(UserPhoneViewModel::class.java)
        name = arguments?.getString(NAME).toString()
        institution = arguments?.getString(INSTITUTION_NAME).toString()
        registrationNumber = arguments?.getString(REGISTRATION_NUMBER).toString()
        phone_number_continue_btn.setOnClickListener {
            if (phone_input.isValidPhone()) {
                createUser()
                userListener()
            }
        }
    }

    private fun createUser() = launch {
        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        Log.d("PhoneFragment","User UID is :$uid")
        val user = User(institution,sharedPrefUtils.getFCMToken() ?: "",sharedPrefUtils.getEmailId() ?: "",name,phone_input.text.toString(),registrationNumber,"",uid)
        Log.d("PhoneFragment","User being upserted is : $user")
        viewModel.userUpsert(user)
    }

    private fun userListener() = launch {
        viewModel.userUpsertStatus.observe(viewLifecycleOwner, Observer {
            if(it){
                requireActivity().shortToaster("User Created Successfully")
                var intent = Intent(requireActivity(),HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }else{
                requireActivity().shortToaster("Error Creating User")
            }
        })
    }
}