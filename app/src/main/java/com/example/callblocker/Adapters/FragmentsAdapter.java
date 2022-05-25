package com.example.callblocker.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.callblocker.Fragments.BlockedNumbersFragment;
import com.example.callblocker.Fragments.CallLogsFragment;
import com.example.callblocker.Fragments.SilentNumbersFragment;

public class FragmentsAdapter extends FragmentStateAdapter {

    public FragmentsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CallLogsFragment();
            case 1:
                return new BlockedNumbersFragment();
            case 2:
                return new SilentNumbersFragment();
            default:
                return new CallLogsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
