package com.example.wappdrop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wappdrop.Models.Photo;
import com.example.wappdrop.R;
import com.example.wappdrop.listeners.OnRecyclerClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CuratedAdapter extends RecyclerView.Adapter<CuratedViewHolder> {
    Context context;
    List<Photo> list;
    OnRecyclerClickListener listener;

    public CuratedAdapter(Context context, List<Photo> list, OnRecyclerClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CuratedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CuratedViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.home_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CuratedViewHolder holder, int position) {
        Picasso.get().load(list.get(position).src.getMedium()).into(holder.imageView);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class CuratedViewHolder extends RecyclerView.ViewHolder{
    CardView container;
    ImageView imageView;

    public CuratedViewHolder(@NonNull View itemView) {
        super(itemView);
        container = itemView.findViewById(R.id.home_list_container);
        imageView = itemView.findViewById(R.id.image_list);

    }
}
