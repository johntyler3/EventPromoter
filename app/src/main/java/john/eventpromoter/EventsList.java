package john.eventpromoter;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventsList extends ListActivity {

    private static final String TAG = "EventsList";

    private ListView view;
    private List<String> events;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createModel();
        view = getListView();
        setAdapter();
        createOnItemClickListener();
    }

    private void createModel() {
//        String[] rawData
//                = getResources().getStringArray(R.array.events);
        // In the future, want to draw data from Firebase/AWS
        String[] rawData = new String[] {"Example 1", "Example 2", "Example 3"};

        events = new ArrayList<>(Arrays.asList(rawData));
    }

    private void setAdapter() {
        // for layout that is simply a TextViews
        adapter
                = new ArrayAdapter<>(
                this,
                R.layout.list_item, // android.R.layout.simple_list_item_1,
                events);

        // don't comment out!
        setListAdapter(adapter);
    }

    private void createOnItemClickListener() {
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

                Log.d(TAG, "Selected view: " + v);

                String event = events.get(position);

                // example if creating and showing a Toast. Cheers!
//                String toastString = "position: " + position +
//                        ", id: " + id + "\ndata: "
//                        + event;
//                Toast.makeText(EventsList.this,
//                        toastString,
//                        Toast.LENGTH_SHORT).show();

//                // call method to respond to click
//                adapter.notifyDataSetChanged();

            }
        });
    }

}
