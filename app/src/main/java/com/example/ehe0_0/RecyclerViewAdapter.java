package com.example.ehe0_0;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Myclass> {
    MainActivity mainActivity;
    ArrayList<String> imgArraylist;
    public RecyclerViewAdapter(MainActivity mainActivity, ArrayList<String> imgArraylist) {
        this.imgArraylist=imgArraylist;
        this.mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mainActivity).inflate(R.layout.recycler_imgae_item,parent,false);

        return new Myclass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myclass holder, int position) {
        Glide.with(mainActivity).load(imgArraylist.get(position)).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pagerIntent=new Intent(mainActivity,WallpaerPagerActivity.class);

                pagerIntent.putExtra("list",imgArraylist);
                pagerIntent.putExtra("pos",position);

                mainActivity.startActivity(pagerIntent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return imgArraylist.size();
    }
    class Myclass extends RecyclerView.ViewHolder{
        ImageView imageView;

        public Myclass(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
