package schedule.sumdu.edu.ua.schedulesumdu.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import schedule.sumdu.edu.ua.schedulesumdu.ChangeListActivity;

public class EditViewListM extends android.support.v7.widget.AppCompatEditText {
    private int mIntentDataID;
    private Activity mParentActivity;
    private Map<String, String> mIdAndValues;

    public EditViewListM(Context context) {
        super(context);
        initComponent();
    }

    public EditViewListM(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    public EditViewListM(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initComponent();
    }

    private void initComponent() {
        this.setFocusable(false);
        mIdAndValues = new HashMap<>();
    }

    public void setIntentDataID(int id) {
        mIntentDataID = id;
    }

    public void setParentActivity(Activity a) {
        mParentActivity = a;
    }

    public void clearIdAndValue() {
        mIdAndValues.clear();
    }

    public void addIdAndValue(String key, String value) {
        mIdAndValues.put(key, value);
    }

    public Map<String, String> getIdAndValues() {
        return mIdAndValues;
    }

    public String findIdByValue(String value) {
        for (String key : mIdAndValues.keySet()) {
            if (mIdAndValues.get(key).equals(value))
                return key;
        }

        return "";
    }

    private ArrayList<String> getValuesForIntent() {
        ArrayList<String> resultList = new ArrayList<>(mIdAndValues.values());
        Collections.sort(resultList, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });

        return resultList;
    }

    @Override
    public boolean onTouchEvent(MotionEvent te) {
        super.onTouchEvent(te);

        if (te.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(mParentActivity, ChangeListActivity.class);
            intent.putExtra(ChangeListActivity.ID_CHOOSE_ITEM, this.getText().toString());
            intent.putStringArrayListExtra(ChangeListActivity.ID_LIST_ITEM, getValuesForIntent());
            mParentActivity.startActivityForResult(intent, mIntentDataID);
        }

        return true;
    }
}
