package com.gallery.gallerylib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gallery.gallery.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class AdActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestNewInterstitial();
        isFirst = true;
    }

    private void requestNewInterstitial() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_page));
        AdRequest adRequest;
        adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private boolean isFirst = true;
    private static Random random = new Random();

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst && random.nextFloat() < 0.8 && mInterstitialAd.isLoaded()) {
            showAd();
        } else {
            isFirst = false;
        }
    }

    protected void showAd() {
        mInterstitialAd.show();
        requestNewInterstitial();
        isFirst = true;
    }
}
