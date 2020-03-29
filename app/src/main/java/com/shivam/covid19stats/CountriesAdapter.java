package com.shivam.covid19stats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<Country> countryArrayList;
    private onItemClickListener itemClickListener;

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    void setOnItemClickListener(onItemClickListener listener) {
        itemClickListener = listener;
    }

    CountriesAdapter(Context context, ArrayList<Country> countryArrayList) {
        this.context = context;
        this.countryArrayList = countryArrayList;
    }

    @NonNull
    @Override
    public CountriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countries, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountriesAdapter.MyViewHolder holder, int position) {

        Country country = countryArrayList.get(position);

        String countryFlag = country.getCountryFlag();
        String countryName = country.getCountryName();
        int countryTotalCases = country.getCountryTotalCases();
        int countryTotalDeaths = country.getCountryTotalDeaths();
        int countryTotalRecovered = country.getCountryTotalRecovered();

        holder.countriesName.setText(countryName);
        holder.countriesTotalCasesCount.setText("" + countryTotalCases);
        holder.countriesTotalDeathsCount.setText("" + countryTotalDeaths);
        holder.countriesTotalRecoveredCount.setText("" + countryTotalRecovered);
        Glide.with(context).load(countryFlag).into(holder.countriesImage);
    }

    @Override
    public int getItemCount() {
        return countryArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView countriesImage;
        private TextView countriesName, countriesTotalCasesCount, countriesTotalDeathsCount, countriesTotalRecoveredCount;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            countriesImage = itemView.findViewById(R.id.countriesImage);
            countriesName = itemView.findViewById(R.id.countriesName);
            countriesTotalCasesCount = itemView.findViewById(R.id.countriesTotalCasesCount);
            countriesTotalDeathsCount = itemView.findViewById(R.id.countriesTotalDeathsCount);
            countriesTotalRecoveredCount = itemView.findViewById(R.id.countriesTotalRecoveredCount);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
