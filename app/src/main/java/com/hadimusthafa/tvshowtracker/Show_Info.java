package com.hadimusthafa.tvshowtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Show_Info extends AppCompatActivity {

    private FileAdapter1 FileAdapter1;
    private RecyclerView recyclerView;
    ImageView imageI, imageI1;
    Button castB;
    TextView nameT, ratingT, languageT, genresT, statusT, runtimeT, releaseT, summaryT, episodeT, related;
    String show_id, name, image, rating, language, genres, status, runtime, release, summary, episode;
    String RLT, url___ = "https://api.tvmaze.com/shows";
    String s1, s2, s3, s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__info);
        imageI = findViewById(R.id.imageI);
        imageI1 = findViewById(R.id.imageI1);
        castB = findViewById(R.id.castB);
        nameT = findViewById(R.id.nameT);
        ratingT = findViewById(R.id.ratingT);
        languageT = findViewById(R.id.languageT);
        genresT = findViewById(R.id.genresT);
        statusT = findViewById(R.id.statusT);
        runtimeT = findViewById(R.id.runtimeT);
        releaseT = findViewById(R.id.releaseT);
        summaryT = findViewById(R.id.summaryT);
        episodeT = findViewById(R.id.episodeT);

        related = findViewById(R.id.related);
        recyclerView = findViewById(R.id.recycler);
        new AsyncMyClass().execute();

        image = (getIntent().getExtras().getString("image"));
        show_id = (getIntent().getExtras().getString("id"));
        name = (getIntent().getExtras().getString("name"));
        rating = (getIntent().getExtras().getString("rating"));
        language = (getIntent().getExtras().getString("language"));
        genres = (getIntent().getExtras().getString("genres"));
        status = (getIntent().getExtras().getString("status"));
        runtime = (getIntent().getExtras().getString("runtime"));
        release = (getIntent().getExtras().getString("release"));
        summary = (getIntent().getExtras().getString("summary"));
        episode = (getIntent().getExtras().getString("episode"));
        Glide.with(this).load(image).into(imageI);
        Glide.with(this).load(image).into(imageI1);
        nameT.setText(name);
        ratingT.setText(rating);
        summaryT.setText(summary);
        genresT.setText("Genres : " + genres);
        releaseT.setText("Release : " + release);
        languageT.setText("Language : " + language);
        statusT.setText("Status : " + status);
        runtimeT.setText("Runtime : " + runtime);
        episodeT.setText("Episode : " + episode);
        castB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Cast.class);
                intent.putExtra("id", show_id);
                startActivity(intent);
            }
        });
    }

    class AsyncMyClass extends AsyncTask<String, String, String> {
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
            related.setVisibility(View.VISIBLE);
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
                String cat = (jsonObject.getString("genres"));
                cat = cat.replaceAll("[^a-z A-Z , 0-9]", " ");
                String[] arr = genres.split(",");
                for ( String ss : arr) {
                    int a = 1;
                    if (a==1){
                       s1 = ss;
                       a++;
                    }
                    if (a==2){
                        s2 = ss;
                        a++;
                    }
                    if (a==3){
                        s3 = ss;
                        a++;
                    }
                    if (a==4){
                        s4 = ss;
                        a++;
                    }
                }
                if (arr.length == 1){
                    if (cat.contains(s1)) {
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
                                String sho = (jsonObject.getString("id"));
                                if (!sho.contains(show_id)){
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
                }
                if (arr.length == 2){
                    if (cat.contains(s1) || cat.contains(s2)) {
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
                                String sho = (jsonObject.getString("id"));
                                if (!sho.contains(show_id)){
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
                }
                if (arr.length == 3){
                    if (cat.contains(s1) || cat.contains(s2) || cat.contains(s3)) {
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
                                String sho = (jsonObject.getString("id"));
                                if (!sho.contains(show_id)){
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
                }
                if (arr.length == 4){
                    if (cat.contains(s1) || cat.contains(s2) || cat.contains(s3) || cat.contains(s3)) {
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
                                String sho = (jsonObject.getString("id"));
                                if (!sho.contains(show_id)){
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
                }
            }
            double k = 0;
            while (k < 100) {
                k++;
                fileModelArrayListF.addAll(maps.get("Group" + k));
            }
            FileAdapter1 = new FileAdapter1(getApplicationContext(), fileModelArrayListF);
            recyclerView.setAdapter(FileAdapter1);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
