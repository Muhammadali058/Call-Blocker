package com.example.callblocker.Fragments;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.CallLog;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.callblocker.Adapters.CallLogsAdapter;
import com.example.callblocker.Adapters.CallLogsGroupedAdapter;
import com.example.callblocker.Models.CallLogs;
import com.example.callblocker.Models.CallLogsGrouped;
import com.example.callblocker.R;
import com.example.callblocker.databinding.FragmentCallLogsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallLogsFragment extends Fragment {

    FragmentCallLogsBinding binding;
    CallLogsGroupedAdapter callLogsGroupedAdapter;
    List<CallLogsGrouped> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCallLogsBinding.bind(inflater.inflate(R.layout.fragment_call_logs, container, false));

        init();

        return binding.getRoot();
    }

    private void init(){
        list = new ArrayList<>();
        list.addAll(getCallLogsByGroup());
        callLogsGroupedAdapter = new CallLogsGroupedAdapter(getActivity(), list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(callLogsGroupedAdapter);
    }

    @SuppressLint("Range")
    private List<CallLogs> getCallLogs(){
        List<CallLogs> callLogsList = new ArrayList<>();

        String strOrder = CallLog.Calls.DATE + " DESC";

        Cursor cur = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);

        while (cur.moveToNext()) {
            CallLogs callLog = new CallLogs();
            callLog.setName(cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME)));
            callLog.setNumber(cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER)));
            callLog.setType(cur.getInt(cur.getColumnIndex(CallLog.Calls.TYPE)));
            callLog.setDate(cur.getLong(cur.getColumnIndex(CallLog.Calls.DATE)));
            callLog.setDuration(cur.getLong(cur.getColumnIndex(CallLog.Calls.DURATION)));

            callLogsList.add(callLog);
        }

        return callLogsList;
    }

    @SuppressLint("Range")
    private List<CallLogsGrouped> getCallLogsByGroup(){
        List<CallLogsGrouped> callLogsGroupedList = new ArrayList<>();
        List<CallLogs> callLogsList = new ArrayList<>();

        Cursor cur = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String oldDate = "";

        CallLogsGrouped callLogsGrouped = null;
        int i = 0;
        while (cur.moveToNext()) {
            CallLogs callLog = new CallLogs();

            callLog.setName(cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME)));
            callLog.setNumber(cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER)));
            callLog.setType(cur.getInt(cur.getColumnIndex(CallLog.Calls.TYPE)));
            callLog.setDate(cur.getLong(cur.getColumnIndex(CallLog.Calls.DATE)));
            callLog.setDuration(cur.getLong(cur.getColumnIndex(CallLog.Calls.DURATION)));

            callLogsList.add(callLog);

            String newDate = sdf.format(new Date(callLog.getDate()));
            if(i == 0){
                oldDate = sdf.format(new Date(callLog.getDate()));
                callLogsGrouped =new CallLogsGrouped();
                callLogsGrouped.setDate(callLog.getDate());
            }

            // When found new date
            if(!oldDate.equalsIgnoreCase(newDate)){
                // Adding logsList to current list
                List<CallLogs> temp = new ArrayList<>();
                temp.addAll(callLogsList);
                callLogsGrouped.setCallLogsList(temp);
                callLogsGroupedList.add(callLogsGrouped);
                callLogsList.clear();

                oldDate = sdf.format(new Date(callLog.getDate()));
                callLogsGrouped = new CallLogsGrouped();
                callLogsGrouped.setDate(callLog.getDate());
            }

            if(i == cur.getCount() - 1){
                callLogsGrouped.setCallLogsList(callLogsList);
                callLogsGroupedList.add(callLogsGrouped);
            }

            i++;
        }

        return callLogsGroupedList;
    }

}