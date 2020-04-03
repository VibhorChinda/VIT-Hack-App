package com.benrostudios.vithackapp.ui.authenticatedLogin

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.userLogin.UserLogin
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.authenticated_user_fragment.*

class AuthenticatedUser : Fragment() {
    private var mCallback: SwitchLoginFrag? = null

    interface SwitchLoginFrag {
        fun switchToLogin()
    }


    companion object {
        fun newInstance() = AuthenticatedUser()
    }

    private lateinit var viewModel: AuthenticatedUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.authenticated_user_fragment, container, false)
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            mCallback = activity as SwitchLoginFrag
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement TextClicked"
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthenticatedUserViewModel::class.java)
        logout_button.setOnClickListener {
            mCallback?.switchToLogin()
        }
    }

}
