package com.example.gehen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.gehen.arraydb.ApplicationDatabase;
import com.example.gehen.arraydb.HotelJSON;
import com.example.gehen.arraydb.HotelJSONAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class mainhome extends AppCompatActivity {
    private static final String JSON_URL = "https://raw.githubusercontent.com/dnzrx/SLC-REPO/master/ISYS6203/E202-ISYS6203-LA01-00.json";
    ListView lvProduct;
    SearchView searchView;
    public static List<HotelJSON> hotelJSONList;
    Intent japan;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);
        searchView = findViewById(R.id.searchview);
        lvProduct = findViewById(R.id.lv_product);

        hotelJSONList = new ArrayList<>();
        loadPlayer();

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {


                for (int j = 0; j < hotelJSONList.size(); j++) {
                    if(i == j){
                        if(j==0){
                            japan = new Intent(mainhome.this, NEO.class);
                        }else if(j==1){
                            japan = new Intent(mainhome.this, Harrison.class);
                        }else if(j==2){
                            japan = new Intent(mainhome.this, GrandSetiabudi.class);
                        }else if(j==3){
                        japan = new Intent(mainhome.this, NEO.class);
                    }else if(j==4){
                        japan = new Intent(mainhome.this, Harrison.class);
                    }
//                            japan.putExtra("NEO", lvProduct.getItemAtPosition(i).toString());
                        ApplicationDatabase.cekhotel = j;
                        startActivity(japan);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater flame = getMenuInflater();
        flame.inflate(R.menu.mainhomemenu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mybookings:

                japan = new Intent(mainhome.this, Indonesia.class);
                startActivity(japan);
                return  true;

            case R.id.logout:

                japan = new Intent(mainhome.this, Login.class);
                startActivity(japan);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void loadPlayer() {
        JsonArrayRequest stringRequest = new JsonArrayRequest(JSON_URL,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject playerObject = null;
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                playerObject = response.getJSONObject(i);

                                HotelJSON hotelJSON = new HotelJSON(playerObject.getInt("id"),
                                        playerObject.getString("name"),
                                        playerObject.getInt("price_per_night"),
                                        playerObject.getString("phone_number"),
                                        playerObject.getString("image"),
                                        playerObject.getString("address"),
                                        playerObject.getDouble("LAT"),
                                        playerObject.getDouble("LNG"));

                                hotelJSONList.add(hotelJSON);
                            }

                            final HotelJSONAdapter adapter = new HotelJSONAdapter( getApplicationContext(), hotelJSONList);
                            lvProduct.setAdapter(adapter);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String s) {

                                    return true;
                                }

                                @Override
                                public boolean onQueryTextChange(String s) {
                                    adapter.getFilter().filter(s);
                                    return false;
                                }
                            });



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
