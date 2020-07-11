package com.benrostudios.vithackapp.ui.home.dynamicfaq

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.vithackapp.R
import com.benrostudios.vithackapp.ui.home.faq.FaqViewModel
import com.benrostudios.vithackapp.ui.home.faq.FaqViewModelFactory
import com.benrostudios.vithackapp.utils.isValidAlphaNumeric
import com.benrostudios.vithackapp.utils.shortToaster
import kotlinx.android.synthetic.main.dynamic_faq_fragment.*
import kotlinx.android.synthetic.main.faq_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class DynamicFaq : DialogFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FaqViewModelFactory by instance()

    companion object {
        fun newInstance() = DynamicFaq()
    }

    private lateinit var viewModel: FaqViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (dialog != null && dialog?.window != null) {
            dialog!!.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return inflater.inflate(R.layout.dynamic_faq_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(FaqViewModel::class.java)
        dynamic_faq_cancel_btn.setOnClickListener {
            dismiss()
        }
        dynamic_faq_ask_question_btn.setOnClickListener {
            if(dynamic_faq_question_input.isValidAlphaNumeric("Question")){
                postQuestion(dynamic_faq_question_input.text.toString())
            }
        }
    }
    fun postQuestion(question: String){
        viewModel.postFaq(question,System.currentTimeMillis().toString())
        viewModel.postFaqStatus.observe(viewLifecycleOwner, Observer {
            if(it){
                requireActivity().shortToaster("Question Posted Successfully!")
                dismiss()
            }
        })
    }
}