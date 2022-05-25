package com.example.callblocker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.callblocker.R;
import com.example.callblocker.databinding.FragmentBlockedNumbersBinding;
import com.example.callblocker.databinding.FragmentSilentNumbersBinding;

public class SilentNumbersFragment extends Fragment {

    FragmentSilentNumbersBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSilentNumbersBinding.bind(inflater.inflate(R.layout.fragment_silent_numbers, container, false));

        init();

        return binding.getRoot();
    }

    private void init(){

    }

}