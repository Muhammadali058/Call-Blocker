package com.example.callblocker.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        holder.binding.name.setText(logs.getName());
        holder.binding.number.setText(logs.getNumber());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        holder.binding.date.setText(sdf.format(new Date(logs.getDate())));

//        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//        holder.binding.date.setText(sdf.format(new Date(logs.getDate())));

        switch (logs.getType()){
            case 1:
                holder.binding.icon.setImageResource(R.drawable.ic_call_received);
                break;
            case 2:
                holder.binding.icon.setImageResource(R.drawable.ic_call_made);
                break;
            case 5:
                holder.binding.icon.setImageResource(R.drawable.ic_call_missed);
                break;
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

    private String getDuration(long seconds){
        return "";
    }

}
