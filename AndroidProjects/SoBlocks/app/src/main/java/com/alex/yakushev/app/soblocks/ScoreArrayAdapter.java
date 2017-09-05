package com.alex.yakushev.app.soblocks;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alex.yakushev.app.soblocks.model.ScoreData;

import java.util.List;

/**
 * Created by Oleksandr on 16-Aug-17.
 */

public class ScoreArrayAdapter  extends ArrayAdapter<ScoreData> {
    private static final long LATEST_SCORES_TIME = 5000; // 5 seconds

    private Context wordContext;
    private List<ScoreData> scoreList;
    private int listViewResourceId;

    public ScoreArrayAdapter(Context context, int listViewResourceId, List<ScoreData> listOfScores) {
        super(context, listViewResourceId, listOfScores);
        this.wordContext = context;
        this.scoreList = listOfScores;
        this.listViewResourceId = listViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) wordContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView itemView = (TextView) inflater.inflate(listViewResourceId, parent, false);
        String html = scoreList.get(position).toHtml();

        if (System.currentTimeMillis()-scoreList.get(position).getTimeStamp().getTime()<LATEST_SCORES_TIME) {
            html = "<u>"+html+"</u>";
        }
        itemView.setText(Html.fromHtml("<h3>"+(position+1)+". "+html+"</h3>"));

        return itemView;
    }
}
