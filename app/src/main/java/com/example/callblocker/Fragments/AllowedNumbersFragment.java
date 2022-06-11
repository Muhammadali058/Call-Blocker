package com.example.callblocker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.callblocker.Adapters.AllowedNumbersAdapter;
import com.example.callblocker.Dao.AppDatabase;
import com.example.callblocker.Models.AllowedNumbers;
import com.example.callblocker.R;
import com.example.callblocker.databinding.FragmentAllowedNumbersBinding;

import java.util.ArrayList;
import java.util.List;

public class AllowedNumbersFragment extends Fragment {

    FragmentAllowedNumbersBinding binding;
    AllowedNumbersAdapter allowedNumbersAdapter;
    List<AllowedNumbers> list;
    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllowedNumbersBinding.bind(inflater.inflate(R.layout.fragment_allowed_numbers, container, false));

        init();

        return binding.getRoot();
    }

    private void init(){
        db = AppDatabase.getInstance(getContext());

        list = new ArrayList<>();
        allowedNumbersAdapter = new AllowedNumbersAdapter(getActivity(), list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(allowedNumbersAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        list.addAll(db.allowedNumbersDao().getAll());
        allowedNumbersAdapter.notifyDataSetChanged();
    }

}