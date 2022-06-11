package com.example.callblocker.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callblocker.Dao.AppDatabase;
import com.example.callblocker.Models.BlockedNumbers;
import com.example.callblocker.Models.CallLogs;
import com.example.callblocker.R;
import com.example.callblocker.databinding.BlockedNumbersHolderBinding;
import com.example.callblocker.databinding.CallLogsHolderBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BlockedNumbersAdapter extends RecyclerView.Adapter<BlockedNumbersAdapter.ViewHolder> {

    Context context;
    List<BlockedNumbers> list;
    AppDatabase db;

    public BlockedNumbersAdapter(Context context, List<BlockedNumbers> list) {
        this.context = context;
        this.list = list;

        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blocked_numbers_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BlockedNumbers blockedNumber = list.get(position);

        if(TextUtils.isEmpty(blockedNumber.getName())){
            holder.binding.name.setText("Unknown");
        }else {
            holder.binding.name.setText(blockedNumber.getName());
        }

        holder.binding.number.setText(blockedNumber.getNumber());
        holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                        .setTitle("Remove")
                        .setMessage("Are you sure to remove?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.blockNumbersDao().delete(blockedNumber);
                                    list.remove(position);
                                    notifyDataSetChanged();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                dialog.show();
            }
        });
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
        BlockedNumbersHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = BlockedNumbersHolderBinding.bind(itemView);
        }
    }

}
