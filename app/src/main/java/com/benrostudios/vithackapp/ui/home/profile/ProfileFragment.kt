package com.benrostudios.vithackapp.ui.home.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avatarfirst.avatargenlib.AvatarConstants
import com.avatarfirst.avatargenlib.AvatarGenerator
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.ui.splash.SplashActivity
import com.benrostudios.vithackapp.ui.splash.SplashActivityViewModel
import com.benrostudios.vithackapp.ui.splash.SplashActivityViewModelFactory
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ProfileFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: SplashActivityViewModelFactory by instance()
    private val sharedPrefUtils: SharedPrefUtils by instance()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: SplashActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SplashActivityViewModel::class.java)
        // TODO: Use the ViewModel
        fetchUser()
        userListener()
        profiile_logout_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            sharedPrefUtils.nuke()
            val intent = Intent(requireActivity(), SplashActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        dark_mode_switch.isChecked = !sharedPrefUtils.getUiMode()
        dark_mode_switch.setOnClickListener {
            (activity as HomeActivity).switchUiMode()
        }
    }

    private fun fetchUser() = launch {
        val uid = FirebaseAuth.getInstance().uid
        viewModel.fetchUser(uid!!)
    }

    private fun userListener() {
        viewModel.fetchedUser.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Glide.with(this)
                    .load(
                        AvatarGenerator.avatarImage(
                            requireActivity(),
                            200,
                            AvatarConstants.CIRCLE,
                            it.name,
                            AvatarConstants.COLOR400
                        )
                    )
                    .into(user_profile_photo)
                user_full_name_profile_screen.text = it.name
                user_email_address_profile_screen.text = it.mail
                user_institute_name_profile_screen.text = it.collegeName
                user_registration_number_profile_screen.text = it.regno
                user_name_profile_screen.text = it.name
            }
        })
    }
}