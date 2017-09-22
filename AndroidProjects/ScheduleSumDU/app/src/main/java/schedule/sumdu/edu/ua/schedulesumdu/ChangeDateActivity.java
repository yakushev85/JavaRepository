package schedule.sumdu.edu.ua.schedulesumdu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import schedule.sumdu.edu.ua.schedulesumdu.components.MonthView;

public class ChangeDateActivity extends AppCompatActivity {
    public static final String ID_CHOOSE_DATE = "CHOOSE_DATE";
    public static long MILLISECONDS_IN_MONTH = 2592000000L;

    private Date mCurrentDate;

    @BindView(R.id.textmonthyear)
    TextView mTextMonthAndYear;
    @BindView(R.id.monthview)
    MonthView mMonthView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_date);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        mCurrentDate = new Date();
        mCurrentDate.setTime(bundle.getLong(ID_CHOOSE_DATE));

        setChangesDate();
    }

    @OnClick(R.id.button_ok)
    public void onClickOk() {
        finish();
    }

    @OnClick(R.id.buttonleft)
    public void onClickLeft() {
        //turn month - 1
        turnMonthDate(mCurrentDate, true);
        setChangesDate();
    }

    @OnClick(R.id.buttonright)
    public void onClickRight() {
        //turn month + 1
        turnMonthDate(mCurrentDate, false);
        setChangesDate();
    }

    @Override
    public void finish() {
        mCurrentDate = mMonthView.getDateM();

        Intent intent = new Intent();
        intent.putExtra(ID_CHOOSE_DATE, mCurrentDate.getTime());

        this.setResult(RESULT_OK, intent);

        super.finish();
    }

    private void setChangesDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.US);
        mTextMonthAndYear.setText(sdf.format(mCurrentDate));
        mMonthView.setDateM(mCurrentDate);
    }

    private void turnMonthDate(Date d, boolean isIncMonth) {
        long time = d.getTime();

        if (isIncMonth) time -= MILLISECONDS_IN_MONTH;
        else time += MILLISECONDS_IN_MONTH;

        d.setTime(time);
    }
}
