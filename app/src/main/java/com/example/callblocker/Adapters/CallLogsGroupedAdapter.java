package com.example.callblocker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callblocker.Models.CallLogs;
import com.example.callblocker.Models.CallLogsGrouped;
import com.example.callblocker.R;
import com.example.callblocker.databinding.CallLogsGroupsHolderBinding;
import com.example.callblocker.databinding.CallLogsHolderBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CallLogsGroupedAdapter extends RecyclerView.Adapter<CallLogsGroupedAdapter.ViewHolder> {

    Context context;
    List<CallLogsGrouped> list;

    public CallLogsGroupedAdapter(Context context, List<CallLogsGrouped> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_logs_groups_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallLogsGrouped logs = list.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        holder.binding.date.setText(sdf.format(new Date(logs.getDate())));

        CallLogsAdapter callLogsAdapter = new CallLogsAdapter(context, logs.getCallLogsList());
        holder.binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.binding.recyclerView.setAdapter(callLogsAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CallLogsGroupsHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CallLogsGroupsHolderBinding.bind(itemView);
        }
    }

}
