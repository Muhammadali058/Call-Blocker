package com.example.callblocker.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.CallLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.callblocker.Adapters.CallLogsAdapter;
import com.example.callblocker.Models.CallLogs;
import com.example.callblocker.Models.Sections;
import com.example.callblocker.R;
import com.example.callblocker.databinding.FragmentCallLogsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallLogsFragment extends Fragment {

    FragmentCallLogsBinding binding;
    CallLogsAdapter callLogsAdapter;
    List<CallLogs> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCallLogsBinding.bind(inflater.inflate(R.layout.fragment_call_logs, container, false));

        init();
        getCallLogsByGroup();

        return binding.getRoot();
    }

    private void init(){
        list = new ArrayList<>();
        list.addAll(getCallLogs());
        callLogsAdapter = new CallLogsAdapter(getActivity(), list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setAdapter(callLogsAdapter);
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
    private void getCallLogsByGroup(){
        List<CallLogs> callLogsList = new ArrayList<>();

        String strOrder = CallLog.Calls.DATE + " DESC";

        Cursor cur = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);

        List<String> datesList = new ArrayList<>();
        List<CallLogs> datesItemsList = new ArrayList<>();
        List<Sections> sectionsList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String oldDate = "";

        int i = 0;
        while (cur.moveToNext()) {
            CallLogs callLog = new CallLogs();

            callLog.setName(cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME)));
            callLog.setNumber(cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER)));
            callLog.setType(cur.getInt(cur.getColumnIndex(CallLog.Calls.TYPE)));
            callLog.setDate(cur.getLong(cur.getColumnIndex(CallLog.Calls.DATE)));
            callLog.setDuration(cur.getLong(cur.getColumnIndex(CallLog.Calls.DURATION)));

            String newDate = sdf.format(new Date(callLog.getDate()));
            if(i == 0){
                oldDate = sdf.format(new Date(callLog.getDate()));
                datesList.add(oldDate);
                i++;
            }

            if(!oldDate.equalsIgnoreCase(newDate)){
                oldDate = sdf.format(new Date(callLog.getDate()));
                datesList.add(oldDate);
            }

            callLogsList.add(callLog);

        }

        for (String s : datesList){
            Log.i("Date = ", s);
        }
    }

}