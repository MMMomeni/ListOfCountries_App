package com.example.midtronics_first_technical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class CountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Country countryObject = (Country) intent.getSerializableExtra("countryObject");

        TextView countryView = findViewById(R.id.countryView);
        TextView capitalView = findViewById(R.id.capitalView);
        TextView populationView = findViewById(R.id.populationView);
        TextView areaView = findViewById(R.id.areaView);
        TextView regionView = findViewById(R.id.regionView);
        TextView subregionView = findViewById(R.id.subregionView);

        setTitle(countryObject.getCountry());

        countryView.setText(countryObject.getCountry());
        capitalView.setText(countryObject.getCapital());
        populationView.setText(countryObject.getPopulation());
        areaView.setText(countryObject.getArea());
        regionView.setText(countryObject.getRegion());
        subregionView.setText(countryObject.getSubregion());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}