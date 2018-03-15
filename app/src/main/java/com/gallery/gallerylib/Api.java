package com.gallery.gallerylib;


import retrofit2.http.GET;
import rx.Observable;

public interface Api {
    @GET("ad2.json")
    Observable<AdObj> getAd();
}
