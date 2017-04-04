package john.eventpromoter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EventsList extends AppCompatActivity {

    private static final String TAG = "EventsList";

    private HashSet mEventSet;
    private HashMap mEventMap;

    private ListView mView;
    private List<String> events;
    private ArrayList asdf;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_list);
        mEventSet = (HashSet) getIntent().getSerializableExtra("EventSet");
        mEventMap = (HashMap) getIntent().getSerializableExtra("EventMap");
        createModel();
//        mView = getListView(); // use this if EventsList extends ListActivity
        mView = (ListView) findViewById(android.R.id.list);
        setAdapter();
        createOnItemClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.events_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_name:
                // do stuff
            case R.id.sort_by_location:
                // do other stuff
//                adapter.notifyDataSetChanged();
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createModel() {
//        String[] rawData
//                = getResources().getStringArray(R.array.events);
        // In the future, want to draw data from Firebase/AWS
        String[] rawData = new String[] {"Example 1", "Example 2", "Example 3", "Example 4", "Example 5", "Example 6", "Example 7", "Example 8"};

        events = new ArrayList<>(Arrays.asList(rawData));
        asdf = new ArrayList<>(mEventSet);
    }

    private void setAdapter() {
        // for layout that is simply a TextViews
        adapter
                = new ArrayAdapter<>(
                this,
                R.layout.list_item, // android.R.layout.simple_list_item_1,
                events);

//        setListAdapter(adapter); // use this if EventsList extends ListActivity
        mView.setAdapter(adapter);
    }

    private void createOnItemClickListener() {
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

                Log.d(TAG, "Selected view: " + v);

                Event event = (Event) asdf.get(position);
//                Event event = events.get(position);

                // example if creating and showing a Toast. Cheers!
//                String toastString = "position: " + position +
//                        ", id: " + id + "\ndata: "
//                        + event;
//                Toast.makeText(EventsList.this,
//                        toastString,
//                        Toast.LENGTH_SHORT).show();

//                // call method to respond to click
                InfoBoxDialogFragment fragment = InfoBoxDialogFragment.newInstance(event.getEventID());
                fragment.show(getFragmentManager(), "info box");

            }
        });
    }

    public void goToEventPage(String eventID) {
        Intent intent = new Intent(this, EventDisplayActivity.class).putExtra("Event", (Event) mEventMap.get(eventID));
        startActivity(intent);
    }

    public void viewMap(View view){
        Intent intent = new Intent(this, MapsActivity.class).putExtra("EventSet", mEventSet).putExtra("EventMap", mEventMap);
        startActivity(intent);
    }

    public void homeButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
