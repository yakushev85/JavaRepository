package com.alex.yakushev.app.torrentslistvisualizer;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alex.yakushev.app.torrentslistvisualizer.model.GeneralMoviesData;
import com.alex.yakushev.app.torrentslistvisualizer.model.MovieInfo;
import com.alex.yakushev.app.torrentslistvisualizer.service.ServiceApi;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;

    public ListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFragment = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = (RecyclerView) viewFragment.findViewById(R.id.recyclerView);

        int currentOrientation = getActivity().getResources().getConfiguration().orientation;

        RecyclerView.LayoutManager layoutManager;
        DividerItemDecoration dividerItemDecoration;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                    DividerItemDecoration.HORIZONTAL);
        } else {
            layoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);
        }

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new YtsRecycleListAdapter(new ArrayList<>(), getActivity()));

        mRecyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(getActivity(), mRecyclerView, (view, position) -> {
                    YtsRecycleListAdapter adapterInner =
                            (YtsRecycleListAdapter) mRecyclerView.getAdapter();

                    mListener.onFragmentInteraction(adapterInner.getMovieByPosition(position));
                }));

        YtsServiceApplication serviceApplication =
                (YtsServiceApplication) getActivity().getApplicationContext();
        Observable<GeneralMoviesData> moviesObservable = serviceApplication
                .getServiceApi()
                .getYtsApi()
                .getListOfMovies();

        if (moviesObservable != null) {
            moviesObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(exception -> {
                        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    })
                    .subscribe(inData -> {
                        Collection<MovieInfo> movieInfoList = inData.getData().getMovies();
                        mRecyclerView.setAdapter(new YtsRecycleListAdapter(movieInfoList, getActivity()));
                    });
        }

        return viewFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(MovieInfo movieInfo);
    }
}
