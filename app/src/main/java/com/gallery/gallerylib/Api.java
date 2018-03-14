package com.gallery.gallerylib;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {
    @GET("ad2.json")
    Observable<AdObj> getAd();
}
