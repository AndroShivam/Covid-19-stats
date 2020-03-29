package com.shivam.covid19stats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements CountriesAdapter.onItemClickListener {

    private TextView mainTotalCasesCount, mainTotalDeathsCount, mainTotalRecoveredCount;
    private RequestQueue requestQueueCard, requestQueueRV;
    private RecyclerView mainRV;
    private CountriesAdapter countriesAdapter;
    private ArrayList<Country> countryArrayList;
    private ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);

        mainTotalCasesCount = findViewById(R.id.mainTotalCasesCount);
        mainTotalDeathsCount = findViewById(R.id.mainTotalDeathsCount);
        mainTotalRecoveredCount = findViewById(R.id.mainTotalRecoveredCount);
        mainProgressBar = findViewById(R.id.mainProgressBar);
        mainRV = findViewById(R.id.mainRV);
        mainRV.setHasFixedSize(true);
        mainRV.setLayoutManager(new LinearLayoutManager(this));

        countryArrayList = new ArrayList<>();
        requestQueueCard = Volley.newRequestQueue(this);
        requestQueueRV = Volley.newRequestQueue(this);
        getDataForCard();
        getDataforRV();
    }

    private void getDataforRV() {
        String countriesData = "https://corona.lmao.ninja/countries";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(countriesData, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mainProgressBar.setVisibility(View.INVISIBLE);
                JSONObject jsonObject1;
                JSONObject jsonObject2;

                try {
                    for (int i = 0; i < response.length(); i++) {
                        //for other info
                        jsonObject1 = response.getJSONObject(i);
                        //for country flag
                        jsonObject2 = response.getJSONObject(i).getJSONObject("countryInfo");

                        String countryName = jsonObject1.getString("country");
                        String countryFlag = jsonObject2.getString("flag");
                        int totalCases = jsonObject1.getInt("cases");
                        int totalDeaths = jsonObject1.getInt("deaths");
                        int totalRecovered = jsonObject1.getInt("recovered");

                        countryArrayList.add(new Country(countryFlag, countryName, totalCases, totalDeaths, totalRecovered));
                    }
                    countriesAdapter = new CountriesAdapter(MainActivity.this, countryArrayList);
                    mainRV.setAdapter(countriesAdapter);
                    countriesAdapter.notifyDataSetChanged();
                    countriesAdapter.setOnItemClickListener(MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainProgressBar.setVisibility(View.INVISIBLE);
                error.printStackTrace();
            }
        });
        requestQueueRV.add(jsonArrayRequest);
    }

    private void getDataForCard() {
        String totalData = "https://corona.lmao.ninja/all";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, totalData, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {

                try {
                    int totalCases = response.getInt("cases");
                    int totalDeaths = response.getInt("deaths");
                    int totalRecovered = response.getInt("recovered");

                    mainTotalCasesCount.setText("" + totalCases);
                    mainTotalDeathsCount.setText("" + totalDeaths);
                    mainTotalRecoveredCount.setText("" + totalRecovered);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueueCard.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemRefresh) {
            getDataForCard();
            getDataforRV();
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("position", position);
        startActivity(detailIntent);
    }
}
