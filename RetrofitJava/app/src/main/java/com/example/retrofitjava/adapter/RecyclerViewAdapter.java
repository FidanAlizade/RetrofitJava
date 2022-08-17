package com.example.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitjava.databinding.RowLayoutBinding;
import com.example.retrofitjava.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    String[] colors = {"#21423c", "#008080","#33aa99", "#58c142", "#339933", "#ffd700", "#ffa500", "#ffa742", "#ff4f00", "#ff005a", "#eb0c83", "#cc33aa"};
    public ArrayList<CryptoModel> cryptoModelArrayList;

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoModelArrayList) {
        this.cryptoModelArrayList = cryptoModelArrayList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutBinding rowLayoutBinding = RowLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RowHolder(rowLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.rowLayoutBinding.textName.setText(cryptoModelArrayList.get(position).currency);
        holder.rowLayoutBinding.textPrice.setText(cryptoModelArrayList.get(position).price);
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 12]));
    }

    @Override
    public int getItemCount() {
        return cryptoModelArrayList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder{

        RowLayoutBinding rowLayoutBinding;
        public RowHolder(RowLayoutBinding rowLayoutBinding) {
            super(rowLayoutBinding.getRoot());
            this.rowLayoutBinding = rowLayoutBinding;
        }
    }
}
