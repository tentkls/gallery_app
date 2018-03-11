package com.gallery.gallerylib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gallery.gallerylib.model.Ad;
import com.gallery.gallerylib.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import rx.schedulers.Schedulers;

public class MainActivity extends AdActivity {
    protected RecyclerView listView;
    protected View emptyView;
    private ListAdapter adapter;
    @Nullable
    private List<Item> itemList;
    @Nullable
    private List<Ad> adList;
    private List<Object> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        listView = findViewById(R.id.list);
        emptyView = findViewById(R.id.empty);
        adapter = new ListAdapter();
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        listView.setAdapter(adapter);
        loadAd();
        loadItem();
        refresh();
    }

    public void refresh() {
        list = new ArrayList<>();
        if (itemList != null) {
            for (int i = 0; i < itemList.size(); i++) {
                list.add(itemList.get(i));
                if (i % 2 == 0) {
                    if (adList != null && adList.size() > 0) {
                        Ad a = adList.remove(new Random().nextInt(adList.size()));
                        list.add(a);
                    }
                    if (adList != null && adList.size() > 0) {
                        Ad a = adList.remove(new Random().nextInt(adList.size()));
                        list.add(a);
                    }
                }
            }
        }

        if (adapter.getItemCount() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    public void onClick(Ad ad) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ad.getUrl()));
        try {
            startActivity(i);
        } catch (Exception ignored) {
        }
    }

    public void onClick(Item item) {
        GalleryActivity.start(this, item.getUrls());
    }

    private class ListAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(MainActivity.this, parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (list.get(position) instanceof Ad) {
                holder.fill((Ad) list.get(position));
            } else if (list.get(position) instanceof Item) {
                holder.fill((Item) list.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView title;
        protected TextView description;
        protected ImageView image;
        protected View adView;

        private Item item;
        private Ad ad;


        public ViewHolder(Context context, ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.view_result, parent, false));
            ButterKnife.bind(this, itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);
            adView = itemView.findViewById(R.id.ad);
            itemView.setOnClickListener(this);
        }

        public void fill(Item item) {
            title.setText(item.getTitle());
            Picasso.with(MainActivity.this).load(item.getUrl()).into(image);
            adView.setVisibility(View.GONE);
            this.item = item;
            this.ad = null;
        }

        public void fill(Ad ad) {
            title.setText(ad.getTitle());
            description.setText(ad.getDescription());
            Picasso.with(MainActivity.this).load(ad.getImg()).into(image);
            adView.setVisibility(View.VISIBLE);
            this.ad = ad;
            this.item = null;
        }

        @Override
        public void onClick(View v) {
            if (item != null) {
                MainActivity.this.onClick(item);
            }
            if (ad != null) {
                MainActivity.this.onClick(ad);
            }
        }
    }

    private void loadAd() {
        App.getApi().getAd().subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new rx.Observer<AdObj>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(AdObj ads) {
                adList = ads.getList();
                MainActivity.this.listView.post(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
            }
        });
    }

    private void loadItem() {
        App.getApi().getItem().subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(new rx.Observer<ItemObj>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ItemObj items) {
                itemList = items.getList();
                MainActivity.this.listView.post(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                });
            }
        });
    }
}
