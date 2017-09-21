package schedule.sumdu.edu.ua.schedulesumdu.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import schedule.sumdu.edu.ua.schedulesumdu.ChangeDateActivity;

public class EditViewDateM extends android.support.v7.widget.AppCompatEditText {
    public static final long MILLISECONDS_IN_WEEK = 604800000L;

    private Date mValueDate;
    private int mIntentDataID;
    private Activity mParentActivity;

    public EditViewDateM(Context context) {
        super(context);
        initComponent();
    }

    public EditViewDateM(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponent();
    }

    public EditViewDateM(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initComponent();
    }

    private void initComponent() {
        mIntentDataID = 0;

        Paint paintLines = new Paint();
        paintLines.setColor(Color.BLUE);

        mValueDate = Calendar.getInstance().getTime();
        setTextDateM();

        this.setFocusable(false);
    }

    private void setTextDateM() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        this.setText(sdf.format(mValueDate));
    }

    public void setDateView(Date d) {
        mValueDate = d;
        setTextDateM();
    }

    public void setDateViewNowPlusWeek() {
        Date dateAddWeek = new Date();
        long timeAddWeek = dateAddWeek.getTime() + MILLISECONDS_IN_WEEK;
        dateAddWeek.setTime(timeAddWeek);
        setDateView(dateAddWeek);
    }

    public Date getDateView() {
        return mValueDate;
    }

    public void setIntentDataID(int id) {
        mIntentDataID = id;
    }

    public void setParentActivity(Activity a) {
        mParentActivity = a;
    }

    @Override
    public boolean onTouchEvent(MotionEvent te) {
        super.onTouchEvent(te);

        if (te.getAction() == MotionEvent.ACTION_DOWN) {
            Intent intent = new Intent(mParentActivity, ChangeDateActivity.class);
            intent.putExtra(ChangeDateActivity.ID_CHOOSE_DATE, mValueDate.getTime());
            mParentActivity.startActivityForResult(intent, mIntentDataID);
        }

        return true;
    }
}
