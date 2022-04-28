package com.example.midtronics_first_technical;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView country;

    MyViewHolder(View view){
        super(view);
        country = view.findViewById(R.id.countryName);

    }
}
