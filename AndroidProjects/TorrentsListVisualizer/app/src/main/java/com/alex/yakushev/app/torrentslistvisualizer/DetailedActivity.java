package com.alex.yakushev.app.torrentslistvisualizer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo;

public class DetailedActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        FragmentManager fm = getSupportFragmentManager();
        Fragment listFragment = fm.findFragmentById(R.id.detailed_fragment_container);

        MovieInfo movieInfo = getIntent().getParcelableExtra(MovieInfo.class.getName());

        if (listFragment == null) {
            listFragment = DetailedFragment.create(movieInfo);

            fm.beginTransaction()
                    .add(R.id.detailed_fragment_container, listFragment)
                    .commit();
        }
    }
}
