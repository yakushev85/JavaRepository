package com.alex.yakushev.app.torrentslistvisualizer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Oleksandr on 10-Sep-17.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private RecyclerClickListener mClickListener;
    private GestureDetector mGestureDetector;

    public RecyclerTouchListener(Context context, final RecyclerView recycleView, final RecyclerClickListener clickListener){

        mClickListener = clickListener;
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if(child != null && mClickListener != null && mGestureDetector.onTouchEvent(e)){
            mClickListener.onClick(child, rv.getChildAdapterPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
