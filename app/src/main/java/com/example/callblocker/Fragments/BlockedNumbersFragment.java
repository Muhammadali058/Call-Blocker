package com.example.callblocker.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.callblocker.Adapters.BlockedNumbersAdapter;
import com.example.callblocker.Dao.AppDatabase;
import com.example.callblocker.Models.BlockedNumbers;
import com.example.callblocker.R;
import com.example.callblocker.databinding.FragmentBlockedNumbersBinding;

import java.util.ArrayList;
import java.util.List;

public class BlockedNumbersFragment extends Fragment {

    FragmentBlockedNumbersBinding binding;
    BlockedNumbersAdapter blockedNumbersAdapter;
    List<BlockedNumbers> list;
    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBlockedNumbersBinding.bind(inflater.inflate(R.layout.fragment_blocked_numbers, container, false));

        init();

        return binding.getRoot();
    }

    private void init(){
        db = AppDatabase.getInstance(getContext());

        list = new ArrayList<>();
        blockedNumbersAdapter = new BlockedNumbersAdapter(getActivity(), list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(blockedNumbersAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        list.addAll(db.blockNumbersDao().getAll());
        blockedNumbersAdapter.notifyDataSetChanged();
    }

}