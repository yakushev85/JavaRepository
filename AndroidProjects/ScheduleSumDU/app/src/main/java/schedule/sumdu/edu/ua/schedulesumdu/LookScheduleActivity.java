package schedule.sumdu.edu.ua.schedulesumdu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import schedule.sumdu.edu.ua.schedulesumdu.model.ScheduleItem;

public class LookScheduleActivity extends AppCompatActivity {
    public static final String ID_SCHEDULE_ITEMS = "SCHEDULE_ITEMS";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_shedule);

        findViewById(R.id.btnOKList).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }

        });

        Bundle bundle = this.getIntent().getExtras();
        List<ScheduleItem> scheduleItems = bundle.getParcelableArrayList(ID_SCHEDULE_ITEMS);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scheduleRecyclerView);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(new ScheduleRecyclerAdapter(scheduleItems));
    }

    private class ScheduleRecyclerAdapter extends RecyclerView.Adapter {
        private List<ScheduleItem> mScheduleItems;

        public ScheduleRecyclerAdapter(List<ScheduleItem> scheduleItems) {
            mScheduleItems = scheduleItems;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTitleListView;

            public ViewHolder(View itemView) {
                super(itemView);

                mTitleListView = (TextView) itemView.findViewById(R.id.titleListView);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

            return new ScheduleRecyclerAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ScheduleRecyclerAdapter.ViewHolder) holder).mTitleListView
                    .setText(mScheduleItems.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mScheduleItems.size();
        }
    }
}
