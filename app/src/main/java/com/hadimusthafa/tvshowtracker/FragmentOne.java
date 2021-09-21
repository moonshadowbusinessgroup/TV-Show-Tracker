package com.hadimusthafa.tvshowtracker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentOne extends Fragment {

    private FileAdapter FileAdapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    public View view;
    Context mContext;
    JSONArray jsonArray;

    public FragmentOne() {
    }

    public FragmentOne(JSONArray JSONaa) {
        jsonArray = JSONaa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmentone, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        swipeLayout = view.findViewById(R.id.srl);
        fetchFiles();
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchFiles();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FileModel fileModel = new FileModel();
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
                        fileModel.setShow_id(jsonObject.getString("id"));
                        fileModel.setShow_Name(jsonObject.getString("name"));
                        fileModel.setLanguage(jsonObject.getString("language"));
                        fileModel.setStatus(jsonObject.getString("status"));
                        fileModel.setRuntime(jsonObject.getString("runtime"));
                        fileModel.setRelease(jsonObject.getString("premiered"));
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
                        fileModel.setSummary(summary);
                        fileModel.setEpisode(jsonObject.getString("weight"));
                        String genres = (jsonObject.getString("genres"));
                        genres = genres.replaceAll("[^a-z A-Z , 0-9]", " ");
                        fileModel.setGenres(genres);
                        fileModel.setRating(jsonObject.getJSONObject("rating").getString("average"));
                        fileModel.setImage(jsonObject.getJSONObject("image").getString("original"));
                        maps.get("Group" + k2).add(fileModel);
                    }
                }
            }
            double k = 0;
            while (k < 100) {
                k++;
                fileModelArrayListF.addAll(maps.get("Group" + k));
            }
            FileAdapter = new FileAdapter(mContext.getApplicationContext(), fileModelArrayListF);
            recyclerView.setAdapter(FileAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext.getApplicationContext(), 3));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }
    }
}