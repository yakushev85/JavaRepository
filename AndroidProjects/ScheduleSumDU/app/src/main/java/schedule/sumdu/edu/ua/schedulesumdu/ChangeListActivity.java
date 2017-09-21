package schedule.sumdu.edu.ua.schedulesumdu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChangeListActivity extends AppCompatActivity {
    public static final String ID_CHOOSE_ITEM = "CHOOSE_ITEM";
    public static final String ID_LIST_ITEM = "LIST_ITEM";
    private List<String> mListItems;
    private String mSelectedItem;
    private ListView mListView;

    public ChangeListActivity() {
        mSelectedItem = "";
    }

    private void setItemsToList(String filterText) {
        ArrayList<String> arrItems = new ArrayList<>();
        String filterTextLow = filterText.toLowerCase();

        for (String item : mListItems) {
            String itemLow = item.toLowerCase();
            if (itemLow.startsWith(filterTextLow)) {
                arrItems.add(item);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, R.layout.item_list, arrItems.toArray(new String[0]));
        mListView.setAdapter(adapter);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_list);

        Button btnOk = (Button) this.findViewById(R.id.btnOKList);
        mListView = (ListView) this.findViewById(R.id.listView1);
        EditText findText = (EditText) this.findViewById(R.id.findText);

        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        findText.setText("");

        mSelectedItem = getIntent().getExtras().getString(ID_CHOOSE_ITEM);
        mListItems = getIntent().getStringArrayListExtra(ID_LIST_ITEM);

        setItemsToList("");

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }

        });

        findText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                String filterText = s.toString();
                setItemsToList(filterText);
                mListView.invalidate();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int argInt,
                                    long argLong) {
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    mSelectedItem = (String) textView.getText();
                }

                finish();
            }

        });
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(ID_CHOOSE_ITEM, mSelectedItem);

        this.setResult(RESULT_OK, intent);

        super.finish();
    }
}
