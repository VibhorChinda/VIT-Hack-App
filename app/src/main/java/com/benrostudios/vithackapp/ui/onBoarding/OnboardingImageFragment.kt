package com.benrostudios.vithackapp.ui.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.utils.GlideApp
import kotlinx.android.synthetic.main.fragment_onboarding_image.*

class OnboardingImageFragment(
    private val imageRes: Int = R.drawable.image_1,
    private val description: String = "The hackathon will be focusing on the feasibility, application, resourcefulness and fundability of each project idea presented by the participant"
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = iv_onboarding_image
        GlideApp.with(view)
            .load(imageRes)
            .into(imageView)
        tv_description.text = description
    }

}