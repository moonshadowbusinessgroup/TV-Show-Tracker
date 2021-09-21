package com.hadimusthafa.tvshowtracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<FileModel> dataFileModelArrayList;
    private Context mContext;

    public FileAdapter(Context ctx, ArrayList<FileModel> dataFileModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.dataFileModelArrayList = dataFileModelArrayList;
        mContext = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(dataFileModelArrayList.get(position).getImage()).into(holder.iv);
        holder.showName.setText(dataFileModelArrayList.get(position).getShow_Name());
        holder.rating.setText(dataFileModelArrayList.get(position).getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Show_Info.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", dataFileModelArrayList.get(position).getShow_id());
                intent.putExtra("name", dataFileModelArrayList.get(position).getShow_Name());
                intent.putExtra("image", dataFileModelArrayList.get(position).getImage());
                intent.putExtra("rating", dataFileModelArrayList.get(position).getRating());
                intent.putExtra("language", dataFileModelArrayList.get(position).getLanguage());
                intent.putExtra("genres", dataFileModelArrayList.get(position).getGenres());
                intent.putExtra("status", dataFileModelArrayList.get(position).getStatus());
                intent.putExtra("runtime", dataFileModelArrayList.get(position).getRuntime());
                intent.putExtra("release", dataFileModelArrayList.get(position).getRelease());
                intent.putExtra("summary", dataFileModelArrayList.get(position).getSummary());
                intent.putExtra("episode", dataFileModelArrayList.get(position).getEpisode());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataFileModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView showName, rating;

        public MyViewHolder(final View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            showName = itemView.findViewById(R.id.showName);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
