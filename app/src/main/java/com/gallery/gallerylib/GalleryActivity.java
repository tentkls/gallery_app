package com.gallery.gallerylib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gallery.gallery.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class GalleryActivity extends AdActivity {
    static final String URLS_TAG = "urlsTag";
    RecyclerView listView;
    Adapter adapter = new Adapter();
    List<String> list;

    public static void start(Context context, List<String> list) {
        Intent i = new Intent(context, GalleryActivity.class);
        i.putStringArrayListExtra(GalleryActivity.URLS_TAG, (ArrayList<String>) list);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        list = getIntent().getStringArrayListExtra(URLS_TAG);
        listView = findViewById(R.id.list);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        listView.setAdapter(adapter);
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.fill(list.get(position), position);
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        int position;


        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(GalleryActivity.this).inflate(R.layout.view_image, parent, false));
            ButterKnife.bind(this, itemView);
            image = ButterKnife.findById(itemView, R.id.image);
            image.setOnClickListener(this);
        }

        void fill(String string, int position) {
            Picasso.with(GalleryActivity.this).load(string).into(image);
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            PagerActivity.start(GalleryActivity.this, list, position);
        }
    }
}
