package com.hadimusthafa.tvshowtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentTwo extends Fragment {

    public View view;
    Context mContext;
    TextView drama, sf, thriller, action, crime, horror, romance,
            adventure, mystery, supernatural, fantasy, family, anime, history, comedy;
    public FragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmenttwo, container, false);

        drama = view.findViewById(R.id.drama);
        sf = view.findViewById(R.id.sf);
        thriller = view.findViewById(R.id.thriller);
        action = view.findViewById(R.id.action);
        mystery = view.findViewById(R.id.mystery);
        supernatural = view.findViewById(R.id.supernatural);
        fantasy = view.findViewById(R.id.fantasy);
        family = view.findViewById(R.id.family);
        anime = view.findViewById(R.id.anime);
        history = view.findViewById(R.id.history);
        comedy = view.findViewById(R.id.comedy);
        crime = view.findViewById(R.id.crime);
        horror = view.findViewById(R.id.horror);
        romance = view.findViewById(R.id.romance);
        adventure = view.findViewById(R.id.adventure);

        drama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Drama");
                startActivity(intent);
            }
        });
        sf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Science-Fiction");
                startActivity(intent);
            }
        });
        thriller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Thriller");
                startActivity(intent);
            }
        });
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Action");
                startActivity(intent);
            }
        });
        crime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Crime");
                startActivity(intent);
            }
        });
        horror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Horror");
                startActivity(intent);
            }
        });
        romance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Romance");
                startActivity(intent);
            }
        });
        adventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Adventure");
                startActivity(intent);
            }
        });
        mystery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Mystery");
                startActivity(intent);
            }
        });
        supernatural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Supernatural");
                startActivity(intent);
            }
        });
        fantasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Fantasy");
                startActivity(intent);
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Family");
                startActivity(intent);
            }
        });
        anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Anime");
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "History");
                startActivity(intent);
            }
        });
        comedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), Category.class);
                intent.putExtra("cat", "Comedy");
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}