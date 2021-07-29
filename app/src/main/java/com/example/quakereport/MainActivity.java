package com.example.quakereport;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {
    ProgressBar progressBar;
    int progress=0;
    private static final String SAMPLE_JSON_RESPONSE = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2021-01-01&endtime=2050-12-01&minmagnitude=4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.Progress);
        progressBar.setVisibility(View.VISIBLE);
        if(isNetworkConnected()) {
            extractEarthquakes();
        }else{
            progressBar.setVisibility(View.GONE);
            TextView textView=(TextView)findViewById(R.id.forinternet);
            textView.setText("please check your internet connection");
        }
       

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public void extractEarthquakes() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        ArrayList<EarthQuake> al= new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, SAMPLE_JSON_RESPONSE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root=new JSONObject(response);
                    JSONArray jsonArray=root.getJSONArray("features");
                    for(int i=0;i<20;i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        JSONObject jsonObject1=jsonObject.getJSONObject("properties");
                        double d=jsonObject1.getDouble("mag");
                        String s=jsonObject1.getString("place");
                        long date=jsonObject1.getLong("time");
                         String datestr= datechange(date);
                        String Url=jsonObject1.getString("url");
                        al.add(new EarthQuake(d,s,datestr,Url));
                    }
                    ListView listView=(ListView)findViewById(R.id.listview);
                    customarrayadapter customarrayadapter=new customarrayadapter(getApplicationContext(),al);
                    listView.setAdapter(customarrayadapter);
                    progressBar.setVisibility(View.GONE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            EarthQuake e=al.get(position);
                            String url=e.getUrl();
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        }
                    });

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
        requestQueue.add(stringRequest);


    }
    public String datechange(long l) {
        Date dateObject = new Date(l);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-M-yyyy\n HH:mm:ss");
        String dateToDisplay = dateFormatter.format(dateObject);
        return dateToDisplay;
    }

}