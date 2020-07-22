package com.benrostudios.vithackapp.ui.home.problemStatement;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.benrostudios.vithackapp.R;

public class ProblemStatement extends Fragment {

    private ProblemStatementViewModel mViewModel;

    public static ProblemStatement newInstance() {
        return new ProblemStatement();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.problem_statement_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProblemStatementViewModel.class);
        // TODO: Use the ViewModel
    }

}