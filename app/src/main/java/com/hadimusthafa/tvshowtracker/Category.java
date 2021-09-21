package com.hadimusthafa.tvshowtracker;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

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
import java.util.HashMap;

public class Category extends AppCompatActivity {
    private FileAdapter FileAdapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    TextView catT;
    ProgressDialog progressDialog;
    String RLT, cat, url___ = "https://api.tvmaze.com/shows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(true);

        cat = (getIntent().getExtras().getString("cat"));
        recyclerView = findViewById(R.id.recycler);
        swipeLayout = findViewById(R.id.srl);
        catT = findViewById(R.id.catT);
        catT.setText(cat);
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
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                URL mUrl = new URL(url___);
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
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    public void fetchFiles() {
        HashMap<String, ArrayList<FileModel>> maps = new HashMap<String, ArrayList<FileModel>>();
        double k1 = 0;
        while (k1 < 100) {
            k1 = k1 + 1;
            maps.put("Group" + k1, new ArrayList<FileModel>());
        }
        ArrayList<FileModel> fileModelArrayListF = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(RLT);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FileModel FileModel = new FileModel();
                String genres = jsonObject.getString("genres");
                if (genres.contains(cat)) {
                    String rat = jsonArray.getJSONObject(i).getJSONObject("rating").getString("average");
                    double k2 = 0;
                    String fds, sds, ts;
                    int fd = 9, sd = 10;
                    double t = 9;
                    while (k2 < 100 && t > 0.0) {
                        if (sd == 0) {
                            fd--;
                            sd = 9;
                        } else {
                            sd--;
                        }
                        fds = String.valueOf(fd);
                        sds = String.valueOf(sd);
                        ts = fds + "." + sds;
                        k2++;
                        if (rat.contains(ts)) {
                            FileModel.setShow_id(jsonObject.getString("id"));
                            FileModel.setShow_Name(jsonObject.getString("name"));
                            FileModel.setLanguage(jsonObject.getString("language"));
                            FileModel.setStatus(jsonObject.getString("status"));
                            FileModel.setRuntime(jsonObject.getString("runtime"));
                            FileModel.setRelease(jsonObject.getString("premiered"));
                            String summary = (jsonObject.getString("summary"));
                            String a = "<p>";
                            summary = summary.replaceAll(a, "");
                            String b = "</p>";
                            summary = summary.replaceAll(b, "");
                            String c = "<b>";
                            summary = summary.replaceAll(c, "");
                            String d = "</b>";
                            summary = summary.replaceAll(d, "");
                            String e = "<i>";
                            summary = summary.replaceAll(e, "");
                            String f = "</i>";
                            summary = summary.replaceAll(f, "");
                            FileModel.setSummary(summary);
                            FileModel.setEpisode(jsonObject.getString("weight"));
                            String genres1 = (jsonObject.getString("genres"));
                            genres1 = genres1.replaceAll("[^a-z A-Z , 0-9]", " ");
                            FileModel.setGenres(genres1);
                            FileModel.setRating(jsonObject.getJSONObject("rating").getString("average"));
                            FileModel.setImage(jsonObject.getJSONObject("image").getString("original"));
                            maps.get("Group" + k2).add(FileModel);
                        }
                    }
                }
            }
            double k = 0;
            while (k < 100) {
                k++;
                fileModelArrayListF.addAll(maps.get("Group" + k));
            }
            FileAdapter = new FileAdapter(getApplicationContext(), fileModelArrayListF);
            recyclerView.setAdapter(FileAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }
}
