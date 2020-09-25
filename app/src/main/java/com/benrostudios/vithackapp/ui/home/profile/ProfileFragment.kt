package com.benrostudios.vithackapp.ui.home.profile

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.base.ScopedFragment
import com.benrostudios.vithackapp.ui.home.HomeActivity
import com.benrostudios.vithackapp.ui.splash.SplashActivity
import com.benrostudios.vithackapp.ui.splash.SplashActivityViewModel
import com.benrostudios.vithackapp.ui.splash.SplashActivityViewModelFactory
import com.benrostudios.vithackapp.utils.SharedPrefUtils
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var dialog: Dialog

    companion object {
        fun newInstance() = ProfileFragment()
        const val FACEBOOK_URL = "https://www.facebook.com/vithack19/"
        const val INSTA_URL = "https://www.instagram.com/vithack2020/"
        const val LINKEDIN_URL = "https://www.linkedin.com/company/hackvit/"
        const val TWITTER_URL = "https://twitter.com/VITHack2020/"
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
        dialog = Dialog(requireActivity(), R.style.ProgressDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.show()
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
        iv_facebook.setOnClickListener {
            launchUri(FACEBOOK_URL)
        }
        iv_instagram.setOnClickListener {
            launchUri(INSTA_URL)
        }
        iv_linkedin.setOnClickListener {
            launchUri(LINKEDIN_URL)
        }
        iv_twitter.setOnClickListener {
            launchUri(TWITTER_URL)
        }
    }

    private fun fetchUser() = launch {
        val uid = FirebaseAuth.getInstance().uid
        viewModel.fetchUser(uid!!)
    }

    private fun userListener() {
        viewModel.fetchedUser.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                dialog.dismiss()
                profile_initials_text.text = intialExtractor(it.name)
                user_full_name_profile_screen.text = it.name
                user_email_address_profile_screen.text = it.mail
                user_institute_name_profile_screen.text = it.collegeName
                user_registration_number_profile_screen.text = it.regno
                user_name_profile_screen.text = it.name
            }
        })
    }

    private fun intialExtractor(name: String): String{
        val names = name.split(" ")
        var initials = ""
        names.forEach {
            initials += it[0].toUpperCase()
        }
        return initials
    }

    private fun launchUri(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}