package com.alex.yakushev.app.torrentslistvisualizer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo;
import com.squareup.picasso.Picasso;

public class DetailedFragment extends Fragment {
    public DetailedFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_detailed, container, false);

        Bundle bundle = getArguments();
        MovieInfo movieInfo = (bundle==null)?null:bundle.getParcelable(MovieInfo.class.getName());

        if (movieInfo != null) {
            ImageView imageView = (ImageView) layoutView.findViewById(R.id.imageDetailedView);
            TextView titleView = (TextView) layoutView.findViewById(R.id.titleDetailedView);
            TextView descriptionView = (TextView) layoutView.findViewById(R.id.descriptionDetailedView);

            Picasso.with(getActivity())
                    .load(movieInfo.getLargeCoverImage())
                    .into(imageView);

            titleView.setText(movieInfo.getTitle());
            descriptionView.setText(movieInfo.getSummary());
        }

        return layoutView;
    }
}
