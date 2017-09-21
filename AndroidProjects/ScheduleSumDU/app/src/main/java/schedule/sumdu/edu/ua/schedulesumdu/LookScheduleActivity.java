package schedule.sumdu.edu.ua.schedulesumdu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import schedule.sumdu.edu.ua.schedulesumdu.model.ScheduleItem;

public class LookScheduleActivity extends AppCompatActivity {
	public static final String ID_SCHEDULE_ITEMS = "SCHEDULE_ITEMS";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_shedule);
		
		Button btnOk = (Button) this.findViewById(R.id.btnOKList);
		ListView listView = (ListView) this.findViewById(R.id.listView1);
		
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	
		Bundle bundle = this.getIntent().getExtras();
		List<ScheduleItem> listItems = bundle.getParcelableArrayList(ID_SCHEDULE_ITEMS);

		ArrayAdapter<ScheduleItem> adapter = new ArrayAdapter <> (this, R.layout.item_list, listItems);
		listView.setAdapter(adapter);
		
		btnOk.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
			}
			
		});		
	}
}
