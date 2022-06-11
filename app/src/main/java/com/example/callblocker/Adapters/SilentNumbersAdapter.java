package com.example.callblocker.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callblocker.Dao.AppDatabase;
import com.example.callblocker.Models.BlockedNumbers;
import com.example.callblocker.Models.SilentNumbers;
import com.example.callblocker.R;
import com.example.callblocker.databinding.BlockedNumbersHolderBinding;
import com.example.callblocker.databinding.SilentNumbersHolderBinding;

import java.util.List;

public class SilentNumbersAdapter extends RecyclerView.Adapter<SilentNumbersAdapter.ViewHolder> {

    Context context;
    List<SilentNumbers> list;
    AppDatabase db;

    public SilentNumbersAdapter(Context context, List<SilentNumbers> list) {
        this.context = context;
        this.list = list;

        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.silent_numbers_holder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SilentNumbers silentNumbers = list.get(position);

        if(TextUtils.isEmpty(silentNumbers.getName())){
            holder.binding.name.setText("Unknown");
        }else {
            holder.binding.name.setText(silentNumbers.getName());
        }

        holder.binding.number.setText(silentNumbers.getNumber());
        holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                        .setTitle("Remove")
                        .setMessage("Are you sure to remove?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    db.silentNumbersDao().delete(silentNumbers);
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
        SilentNumbersHolderBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SilentNumbersHolderBinding.bind(itemView);
        }
    }

}
