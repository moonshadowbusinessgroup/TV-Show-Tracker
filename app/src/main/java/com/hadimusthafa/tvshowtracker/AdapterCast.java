package com.hadimusthafa.tvshowtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterCast extends RecyclerView.Adapter<AdapterCast.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<FileModel> dataFileModelArrayList;
    private Context mContext;

    public AdapterCast(Context ctx, ArrayList<FileModel> dataFileModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.dataFileModelArrayList = dataFileModelArrayList;
        mContext = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_cast, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(dataFileModelArrayList.get(position).getImageP()).into(holder.imageP);
        holder.nameP.setText(dataFileModelArrayList.get(position).getNameP());
        holder.country.setText(dataFileModelArrayList.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return dataFileModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageP;
        TextView nameP, country;

        public MyViewHolder(final View itemView) {
            super(itemView);
            imageP = itemView.findViewById(R.id.imageP);
            nameP = itemView.findViewById(R.id.nameP);
            country = itemView.findViewById(R.id.country);
        }
    }
}
