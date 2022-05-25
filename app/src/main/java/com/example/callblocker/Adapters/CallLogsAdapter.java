package com.example.callblocker.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callblocker.Models.CallLogs;
import com.example.callblocker.R;
import com.example.callblocker.databinding.CallLogsHolderBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CallLogsAdapter extends RecyclerView.Adapter<CallLogsAdapter.ViewHolder> {

    Context context;
    List<CallLogs> list;
    int selectedPos = -1;

    public CallLogsAdapter(Context context, List<CallLogs> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_logs_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallLogs logs = list.get(position);

        if(TextUtils.isEmpty(logs.getName())){
            holder.binding.name.setText(logs.getNumber());
        }else {
            holder.binding.name.setText(logs.getName());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        holder.binding.date.setText(sdf.format(new Date(logs.getDate())));

        switch (logs.getType()){
            case 1:
                holder.binding.icon.setImageResource(R.drawable.ic_call_received);
                holder.binding.type.setText("Received");
                break;
            case 2:
                holder.binding.icon.setImageResource(R.drawable.ic_call_made);
                holder.binding.type.setText("Dialed");
                break;
            case 5:
                holder.binding.icon.setImageResource(R.drawable.ic_call_missed);
                holder.binding.type.setText("Missed");
                break;
        }

        final int pos = position;
        holder.binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPos == pos)
                    selectedPos = -1;
                else
                    selectedPos = pos;

                notifyDataSetChanged();
            }
        });
        if(position == selectedPos){
            holder.binding.expandableLayout.setVisibility(View.VISIBLE);
        }else {
            holder.binding.expandableLayout.setVisibility(View.GONE);
        }
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
        CallLogsHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CallLogsHolderBinding.bind(itemView);
        }
    }

}
