package schedule.sumdu.edu.ua.schedulesumdu;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import schedule.sumdu.edu.ua.schedulesumdu.components.EditViewDateM;
import schedule.sumdu.edu.ua.schedulesumdu.components.EditViewListM;
import schedule.sumdu.edu.ua.schedulesumdu.model.ScheduleItem;
import schedule.sumdu.edu.ua.schedulesumdu.service.ServiceSumduApi;
import schedule.sumdu.edu.ua.schedulesumdu.service.SumduApi;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "MainActivity";

    private final int ID_EDIT_DATE_BEFORE = 210;
    private final int ID_EDIT_DATE_AFTER = 211;
    private final int ID_EDIT_TEACHER = 212;
    private final int ID_EDIT_GROUP = 213;
    private final int ID_EDIT_ROOM = 214;

    private EditViewDateM mEditViewDateBefore, mEditViewDateAfter;
    private EditViewListM mEditTeacher, mEditGroup, mEditRoom;
    private Button mButtonSend;
    private ServiceSumduApi mInternalApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditViewDateBefore = (EditViewDateM) this.findViewById(R.id.dateinputbefore);
        mEditViewDateAfter = (EditViewDateM) this.findViewById(R.id.dateinputafter);
        mEditTeacher = (EditViewListM) this.findViewById(R.id.editTeacher);
        mEditGroup = (EditViewListM) this.findViewById(R.id.editGroup);
        mEditRoom = (EditViewListM) this.findViewById(R.id.editRoom);
        mButtonSend = (Button) this.findViewById(R.id.btnOKList);

        initComponents();

        mInternalApi = ((ScheduleSumduApp) getApplication()).getServiceSumduApi();

        if (checkConnection()) {
            updateViewLists();
        } else {
            showToastShort("No connection to Internet!");
        }
    }

    private void initComponents() {
        mButtonSend.setOnClickListener(this);
        mButtonSend.setEnabled(false);

        mEditViewDateAfter.setDateViewNowPlusWeek();

        mEditViewDateBefore.setIntentDataID(ID_EDIT_DATE_BEFORE);
        mEditViewDateAfter.setIntentDataID(ID_EDIT_DATE_AFTER);
        mEditTeacher.setIntentDataID(ID_EDIT_TEACHER);
        mEditGroup.setIntentDataID(ID_EDIT_GROUP);
        mEditRoom.setIntentDataID(ID_EDIT_ROOM);

        mEditViewDateBefore.setParentActivity(this);
        mEditViewDateAfter.setParentActivity(this);
        mEditTeacher.setParentActivity(this);
        mEditGroup.setParentActivity(this);
        mEditRoom.setParentActivity(this);
    }

    private void updateViewLists() {
        SumduApi generalApi = mInternalApi.getSumduApi();

        generalApi.getTeachers().enqueue(new ListCustomCallback(mEditTeacher));
        generalApi.getAuditoriums().enqueue(new ListCustomCallback(mEditRoom));
        generalApi.getGroups().enqueue(new ListCustomCallback(mEditGroup));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ID_EDIT_DATE_BEFORE:
                    mEditViewDateBefore.setDateView(
                            new Date(intent.getExtras().getLong(ChangeDateActivity.ID_CHOOSE_DATE)));
                    checkDates();
                    break;
                case ID_EDIT_DATE_AFTER:
                    mEditViewDateAfter.setDateView(
                            new Date(intent.getExtras().getLong(ChangeDateActivity.ID_CHOOSE_DATE)));
                    checkDates();
                    break;
                case ID_EDIT_TEACHER:
                    mEditTeacher.setText(
                            intent.getExtras().getString(ChangeListActivity.ID_CHOOSE_ITEM));
                    break;
                case ID_EDIT_GROUP:
                    mEditGroup.setText(
                            intent.getExtras().getString(ChangeListActivity.ID_CHOOSE_ITEM));
                    break;
                case ID_EDIT_ROOM:
                    mEditRoom.setText(
                            intent.getExtras().getString(ChangeListActivity.ID_CHOOSE_ITEM));
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonSend) {
            mButtonSend.setEnabled(false);
            String teacher = mEditTeacher.getText().toString();
            String group = mEditGroup.getText().toString();
            String room = mEditRoom.getText().toString();

            if (teacher.equals("") && group.equals("") && room.equals("")) {
                mButtonSend.setEnabled(true);
                showToastShort("Choose teacher or group or room!");
                return;
            }

            String dateFrom = mEditViewDateBefore.getText().toString();
            String dateTo = mEditViewDateAfter.getText().toString();

            requestToScheduleItems(group, teacher, room, dateFrom, dateTo);
        }
    }

    private void requestToScheduleItems(String group, String teacher, String room,
                                        String dateFrom, String dateTo) {
        Call<List<ScheduleItem>> call = mInternalApi.getSumduApi(GsonConverterFactory.create())
                .getScheduleItems(
                        mEditGroup.findIdByValue(group),
                        mEditTeacher.findIdByValue(teacher),
                        mEditRoom.findIdByValue(room),
                        dateFrom,
                        dateTo);

        call.enqueue(new Callback<List<ScheduleItem>>() {
            @Override
            public void onResponse(Call<List<ScheduleItem>> call, Response<List<ScheduleItem>> response) {
                showScheduleList(response.body());
                mButtonSend.setEnabled(true);
            }

            @Override
            public void onFailure(Call<List<ScheduleItem>> call, Throwable t) {
                Log.e(TAG, "Error in Callback<List<ScheduleItem>> ", t);
                showToastLong(t.getMessage());
                mButtonSend.setEnabled(true);
            }
        });
    }

    private void checkDates() {
        if (mEditViewDateAfter.getDateView().before(mEditViewDateBefore.getDateView())) {
            mEditViewDateAfter.setDateView(mEditViewDateBefore.getDateView());
        }

        Date minDate = Calendar.getInstance().getTime();
        if (mEditViewDateBefore.getDateView().before(minDate)) {
            mEditViewDateBefore.setDateView(minDate);
        }
        if (mEditViewDateAfter.getDateView().before(minDate)) {
            mEditViewDateAfter.setDateView(minDate);
        }
    }

    private boolean checkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void showToastShort(String txt) {
        Toast toast = Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    private void showToastLong(String txt) {
        Toast toast = Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    private void showScheduleList(List<ScheduleItem> list) {
        Intent intent = new Intent(this, LookScheduleActivity.class);
        intent.putParcelableArrayListExtra(LookScheduleActivity.ID_SCHEDULE_ITEMS,
                (ArrayList<? extends Parcelable>) list);
        startActivity(intent);
    }

    private class ListCustomCallback implements Callback<ResponseBody> {
        private static final String INNER_TAG = "ListCustomCallback";

        private EditViewListM mEditText;

        ListCustomCallback(EditViewListM editText) {
            mEditText = editText;
        }

        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    JSONObject json = new JSONObject(responseBody.string());
                    Iterator<String> keys = json.keys();

                    mEditText.clearIdAndValue();

                    while (keys.hasNext()) {
                        String keyItem = keys.next();
                        String valueItem = json.getString(keyItem);
                        mEditText.addIdAndValue(keyItem, valueItem);
                    }

                    if (isListValuesLoaded()) {
                        mButtonSend.setEnabled(true);
                    }
                }
            } catch (Exception e) {
                Log.e(INNER_TAG, "Callback.onResponse ", e);
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.e(INNER_TAG, "Callback.onFailure ", t);
        }
    }

    private boolean isListValuesLoaded() {
        return !mEditTeacher.getIdAndValues().isEmpty() &&
                !mEditGroup.getIdAndValues().isEmpty() &&
                !mEditRoom.getIdAndValues().isEmpty();
    }
}
