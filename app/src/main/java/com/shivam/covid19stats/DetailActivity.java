package com.shivam.covid19stats;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ImageView countryFlag;
    private TextView countryName;
    private TextView countryLongitude;
    private TextView countryLatitude;
    private TextView countryTotalCases;
    private TextView countryCasesToday;
    private TextView countryTotalDeaths;
    private TextView countryDeathsToday;
    private TextView countryRecovered;
    private TextView countryActiveCases;
    private TextView countryCriticalCases;
    private TextView countryCasesPerMil;
    private TextView countryDeathsPerMil;
    private ProgressBar detailProgressBar;
    private RequestQueue requestQueue;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        position = Objects.requireNonNull(getIntent().getExtras()).getInt("position");

        countryFlag = findViewById(R.id.detailCountryFlag);
        countryName = findViewById(R.id.detailCountryName);
        countryLongitude = findViewById(R.id.detailCountryLong);
        countryLatitude = findViewById(R.id.detailCountryLat);
        countryTotalCases = findViewById(R.id.detailCountryCasesCount);
        countryCasesToday = findViewById(R.id.detailCountryCasesTodayCount);
        countryTotalDeaths = findViewById(R.id.detailCountryDeathsCount);
        countryDeathsToday = findViewById(R.id.detailCountryDeathsTodayCount);
        countryRecovered = findViewById(R.id.detailCountryRecoveredCount);
        countryActiveCases = findViewById(R.id.detailCountryDeathsActiveCount);
        countryCriticalCases = findViewById(R.id.detailCountryCriticalCount);
        countryCasesPerMil = findViewById(R.id.detailCountryCasesPerMilCount);
        countryDeathsPerMil = findViewById(R.id.detailCountryDeathsPerMilCount);
        detailProgressBar = findViewById(R.id.detailProgressBar);

        requestQueue = Volley.newRequestQueue(this);
        getCountryData();


    }

    private void getCountryData() {

        String countriesFullData = "https://corona.lmao.ninja/countries";
        JsonArrayRequest request = new JsonArrayRequest(countriesFullData, new Response.Listener<JSONArray>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONArray response) {
                detailProgressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject jsonObject1 = response.getJSONObject(position);
                    JSONObject jsonObject2 = response.getJSONObject(position).getJSONObject("countryInfo");
                    String country = jsonObject1.getString("country");
                    String flag = jsonObject2.getString("flag");
                    int latitude = jsonObject2.getInt("lat");
                    int longitude = jsonObject2.getInt("long");
                    int totalCases = jsonObject1.getInt("cases");
                    int totalDeaths = jsonObject1.getInt("deaths");
                    int totalRecovered = jsonObject1.getInt("recovered");
                    int casesToday = jsonObject1.getInt("todayCases");
                    int deathsToday = jsonObject1.getInt("todayDeaths");
                    int active = jsonObject1.getInt("active");
                    int critical = jsonObject1.getInt("critical");
                    double casesPerOneMillion = jsonObject1.getDouble("casesPerOneMillion");
                    double deathsPerOneMillion = jsonObject1.getDouble("deathsPerOneMillion");

                    setTitle("" + country);
                    countryName.setText("" + country);
                    countryLatitude.setText("" + latitude + "° N");
                    countryLongitude.setText("" + longitude + "° E");
                    countryTotalCases.setText("" + totalCases);
                    countryCasesToday.setText("" + casesToday);
                    countryTotalDeaths.setText("" + totalDeaths);
                    countryDeathsToday.setText("" + deathsToday);
                    countryRecovered.setText("" + totalRecovered);
                    countryActiveCases.setText("" + active);
                    countryCriticalCases.setText("" + critical);
                    countryCasesPerMil.setText("" + casesPerOneMillion);
                    countryDeathsPerMil.setText("" + deathsPerOneMillion);
                    Glide.with(DetailActivity.this).load(flag).into(countryFlag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                detailProgressBar.setVisibility(View.INVISIBLE);
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}
