package com.gallery.gallerylib.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String url;
    private String title;
    private List<String> urls;

    public Item() {
    }

    public Item(JSONObject o) {
        url = o.optString("url");
        title = o.optString("title");
        urls = new ArrayList<>();
        JSONArray a = o.optJSONArray("urls");
        for (int i = 0; i < a.length(); i++) {
            urls.add(a.optString(i));
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
