package com.hadimusthafa.tvshowtracker;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Cast extends AppCompatActivity {
    private AdapterCast AdapterCast;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    String RLT, show_id, url, image, country, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast);
        show_id = (getIntent().getExtras().getString("id"));
        url = "https://api.tvmaze.com/shows/" + show_id + "/cast";
        recyclerView = findViewById(R.id.recycler);
        swipeLayout = findViewById(R.id.srl);
        new AsyncMyClass().execute();
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFiles();
            }
        });
    }

    class AsyncMyClass extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL mUrl = new URL(url);
                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.setRequestProperty("Content-length", "0");
                httpConnection.setUseCaches(false);
                httpConnection.setAllowUserInteraction(false);
                httpConnection.setConnectTimeout(100000);
                httpConnection.setReadTimeout(100000);
                httpConnection.connect();
                int responseCode = httpConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        result.append(line + "\n");
                    }
                    br.close();
                    return result.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            RLT = result;
            fetchFiles();
        }
    }

    public void fetchFiles() {
        ArrayList<FileModel> fileModelArrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(RLT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FileModel FileModel = new FileModel();
                FileModel.setNameP(jsonObject.getJSONObject("person").getString("name"));
                FileModel.setImageP(jsonObject.getJSONObject("person").getJSONObject("image").getString("original"));
                FileModel.setCountry(jsonObject.getJSONObject("person").getJSONObject("country").getString("name"));
                fileModelArrayList.add(FileModel);
            }
            AdapterCast = new AdapterCast(getApplicationContext(), fileModelArrayList);
            recyclerView.setAdapter(AdapterCast);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }
}
