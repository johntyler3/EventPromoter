package john.eventpromoter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EventsList extends AppCompatActivity {

    private static final String TAG = "EventsList";

    private HashSet mEventSet;
    private HashMap mEventMap;

    private ListView mView;
    private ArrayList eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_list);
        mEventSet = (HashSet) getIntent().getSerializableExtra("EventSet");
        mEventMap = (HashMap) getIntent().getSerializableExtra("EventMap");
        eventList = new ArrayList<>(mEventSet);
        mView = (ListView) findViewById(android.R.id.list);
        Log.d(TAG, "number of events = " + eventList.size());
        mView.setAdapter(new EventAdapter(eventList));
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
        // Right now is made to handle ONLY sorting options in the menu
        if (item.isChecked()) item.setChecked(false);
        else item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.sort_by_name:
                // do stuff
            case R.id.sort_by_location:
                // do other stuff
                ((ArrayAdapter<Event>) mView.getAdapter()).notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createOnItemClickListener() {
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

                Log.d(TAG, "Selected view: " + v);

                Event event = (Event) eventList.get(position);

                // example if creating and showing a Toast. Cheers!
//                String toastString = "position: " + position +
//                        ", id: " + id + "\ndata: "
//                        + event;
//                Toast.makeText(EventsList.this,
//                        toastString,
//                        Toast.LENGTH_SHORT).show();

                // call method to respond to click
                InfoBoxDialogFragment fragment = InfoBoxDialogFragment.newInstance(event.getEventID());
                fragment.show(getFragmentManager(), "info box");

            }
        });
    }

    private Event getModel(int position) {
        return ((EventAdapter) mView.getAdapter()).getItem(position);
    }

    private class EventAdapter extends ArrayAdapter<Event> {
        EventAdapter(List<Event> list) {
            super(EventsList.this, R.layout.list_item, list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
//            View row = super.getView(position, convertView, parent);
//            TextView timeView = (TextView) row.getTag();
//            if (timeView == null) {
//                timeView = (TextView) row.findViewById(R.id.list_item_time);
//                row.setTag(timeView);
//            }
//
//            Event model = getModel(position);
//            timeView.setTag(position);
//            timeView.setText(model.getEventTime());
//            return row;

            LayoutInflater inflater = EventsList.this.getLayoutInflater();
            View row = inflater.inflate(R.layout.list_item, parent, false);

            TextView name = (TextView) row.findViewById(R.id.list_item_name);
            TextView time = (TextView) row.findViewById(R.id.list_item_time);

            Event model = getModel(position);
            name.setText(model.getEventName());
            time.setText(model.getEventTime());

            return row;
        }
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
