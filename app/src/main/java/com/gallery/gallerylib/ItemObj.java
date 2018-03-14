package com.gallery.gallerylib;


import com.gallery.gallerylib.model.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemObj {
    private String uniqueTag;
    private String title;
    private String shortDescription;
    private String description;
    private String icon;
    private List<Item> list;

    public ItemObj() {
    }

    public ItemObj(JSONObject o) {
        uniqueTag = o.optString("uniqueTag");
        title = o.optString("title");
        shortDescription = o.optString("shortDescription");
        description = o.optString("description");
        icon = o.optString("icon");
        list = new ArrayList<>();
        JSONArray a = o.optJSONArray("list");
        for (int i = 0; i < a.length(); i++) {
            list.add(new Item(a.optJSONObject(i)));
        }
    }

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }
}
