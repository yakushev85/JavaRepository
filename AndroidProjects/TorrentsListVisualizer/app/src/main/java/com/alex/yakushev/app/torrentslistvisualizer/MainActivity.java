package com.alex.yakushev.app.torrentslistvisualizer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNetworkConnected()) {
            Toast.makeText(this, "No connection to internet!", Toast.LENGTH_LONG).show();
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment listFragment = fm.findFragmentById(R.id.main_fragment_container);

        if (listFragment == null) {
            listFragment = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.main_fragment_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentInteraction(MovieInfo movieInfo) {
        Intent intent = new Intent(this, DetailedActivity.class);
        intent.putExtra(MovieInfo.class.getName(), movieInfo);
        startActivity(intent);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
