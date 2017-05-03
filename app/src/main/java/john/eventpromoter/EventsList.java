package john.eventpromoter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class EventsList extends AppCompatActivity {

    private static final String TAG = "EventsList";

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "Filter";

    private HashSet mEventSet;
    private HashMap mEventMap;
    private HashMap mFilteredMap;

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
        sortList(TimeDateComparator, "time and date");
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
        FragmentManager fragmentManager = getFragmentManager();

        if (item.isChecked()) item.setChecked(false);
        else item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.sort_by_name:
                sortList(NameComparator, "name");
                return true;
            case R.id.sort_by_location:
                sortList(LocationComparator, "location");
                return true;
            case R.id.sort_by_date_time:
                sortList(TimeDateComparator, "time and date");
                return true;
//            case R.id.filter_events:
//                FilterEventDialog filterEventDialog = new FilterEventDialog();
//                Bundle bundle = new Bundle();
////                bundle.put
////                filterEventDialog.setArguments();
//                filterEventDialog.show(fragmentManager, "filter");
//                sharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void sortList(Comparator<Event> comparator, String criteria) {
        Collections.sort(eventList, comparator);
        Toast.makeText(EventsList.this, "Sorting by event " + criteria, Toast.LENGTH_LONG).show();
        ((ArrayAdapter<Event>) mView.getAdapter()).notifyDataSetChanged();
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
            time.setText(model.getEventTime() + " " + model.getMonth() + "/" + model.getDay() + "/" + model.getYear());

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
        finish();
    }

    public void homeButton(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /***
     * Custom Comparators for sorting the list
     */

    public static Comparator<Event> NameComparator = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            String name1 = e1.getEventName();
            String name2 = e2.getEventName();

            return name1.compareToIgnoreCase(name2);
        }
    };

    public static Comparator<Event> LocationComparator = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            String loc1 = e1.getBuildingCode();
            String loc2 = e2.getBuildingCode();

            if (loc1.equals(loc2)) {
                String room1 = e1.getRoomNumber();
                String room2 = e2.getRoomNumber();

                return room1.compareToIgnoreCase(room2);
            }

            return loc1.compareToIgnoreCase(loc2);
        }
    };

    // TODO: Make this implementation less massively sucky, maybe by using Date class or something
    public static Comparator<Event> TimeDateComparator = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            int year1 = e1.getYear();
            int year2 = e2.getYear();

            if (year1 == year2) {
                int month1 = e1.getMonth();
                int month2 = e2.getMonth();

                if (month1 == month2) {
                    int day1 = e1.getDay();
                    int day2 = e2.getDay();

                    if (day1 == day2) {
                        int hour1 = e1.getHour();
                        int hour2 = e2.getHour();

                        if (hour1 == hour2) {
                            int min1 = e1.getMinute();
                            int min2 = e2.getMinute();

                            return min1 - min2;
                        }

                        return hour1 - hour2;
                    }

                    return day1 - day2;
                }

                return month1 - month2;
            }

            return year1 - year2;
        }
    };

    public Map<String, Event> filter(Map<String, Event> eventMap,
                                            int year, int month, int day,
                                            int hour, int minute,
                                            ArrayList<String> building,
                                            ArrayList<String> orgName,
                                            boolean food){
        Map<String, Event> filteredEvents = new HashMap<>();
        Log.d(TAG, "Beginning Event Query");
        boolean filterDate = false;
        boolean filterTime = false;
        boolean filterBuilding = false;
        boolean filterOrg = false;
//        boolean filterFood = food;
        if (year != -1){
            filterDate = true;
        }
        if (hour != -1){
            filterTime = true;
        }
        if (building != null){
            filterBuilding = true;
        }
        if (orgName != null){
            filterOrg = true;
        }
//        if (food != null){
//            filterFood = true;
//        }

        for (Event event: eventMap.values()) {
            boolean passFilterDate = false;
            boolean passFilterTime = false;
            boolean passFilterBuilding = false;
            boolean passFilterOrg = false;
            boolean passFilterFood = false;
            // ================================================DATE================================
            if(filterDate){
                if (event.getYear() == year && event.getMonth() == month && event.getDay() == day){
                    passFilterDate = true;
                }
            }
            else{ // If date is not one of the parameters events are being filtered on it automatically passes
                passFilterDate = true;
            }
            // ================================================TIME=================================
            if(filterTime){
                if (event.getHour() == hour && event.getMinute() == minute){
                    passFilterTime = true;
                }
            }
            else{
                passFilterTime = true;
            }
            // ============================================BUILDING=================================
            if(filterBuilding){
                if (building.contains(event.getBuildingCode())){
                    passFilterBuilding = true;
                }
            }
            else{
                passFilterBuilding = true;
            }
            // ==============================================ORG====================================
            if(filterOrg){
                if (orgName.contains(event.getOrgName())){
                    passFilterOrg = true;
                }
            }
            else{
                passFilterOrg = true;
            }
            // ==============================================FOOD===================================
            if(food){
                if (event.getFoodProvided() != null)
                    passFilterFood = true;
            }
            else{
                passFilterFood = true;
            }


            if (passFilterBuilding && passFilterDate && passFilterFood && passFilterOrg && passFilterTime){
                filteredEvents.put(event.getEventID(), event);
            }
        }
        return filteredEvents;
    }
}
