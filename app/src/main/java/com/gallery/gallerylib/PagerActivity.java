package com.gallery.gallerylib;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.gallery.gallery.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class PagerActivity extends AdActivity implements ViewPager.OnPageChangeListener {
    private static final String DATA = "data";
    private static final String POS = "pos";
    private List<String> urls;
    ViewPager pager;

    public static void start(Context context, List<String> urls, int position) {
        Intent i = new Intent(context, PagerActivity.class);
        i.putStringArrayListExtra(DATA, (ArrayList<String>) urls);
        i.putExtra(POS, position);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);
        pager = ButterKnife.findById(this, R.id.pager);
        urls = getIntent().getStringArrayListExtra(DATA);
        pager.setAdapter(new Adapter());
        pager.setCurrentItem(getIntent().getIntExtra(POS, 0));
        pager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class Adapter extends FragmentStatePagerAdapter {
        Adapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.instance(urls.get(position));
        }

        @Override
        public int getCount() {
            return urls == null ? 0 : urls.size();
        }
    }
}
