package com.example.cafe.cafe.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafe.Data;

public abstract class GyHolder extends RecyclerView.ViewHolder {
    public GyHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void onBind(RecyclerView.ViewHolder holder, Data data);
}
