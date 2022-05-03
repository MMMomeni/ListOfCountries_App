package com.example.midtronics_first_technical;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<MyViewHolder>  {

    private static final String TAG = "NoteAdapter";
    private final List<Country> countryList;
    private final MainActivity mainAct;

    CountryAdapter(List<Country> ntList, MainActivity ma) {
        this.countryList = ntList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_box, parent, false);

        itemView.setOnClickListener(mainAct); // means that main activity owns the onClickListener
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Country n = countryList.get(position);

        holder.country.setText(n.getCountry());

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }
}
