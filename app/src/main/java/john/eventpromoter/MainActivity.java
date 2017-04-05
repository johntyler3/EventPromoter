package john.eventpromoter;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference eventRef = database.getReference();

    final static String TAG = "MainEvent";
    protected HashMap<String, Event> mEventMap;

    protected HashSet<Event> mEventSet;

    // TODO: 4/4/2017 Have event Listener only listen for new events on resume
    private void eventListener(){
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Event event = child.getValue(Event.class);
                    Log.d(TAG, event.toString());
                    if (!mEventMap.containsKey(event.getEventID())){
                        mEventMap.put(event.getEventID(), event);
                        mEventSet.add(event);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEventMap = new HashMap<>();
        mEventSet = new HashSet<>();
        // Grab all the data that is saved on Firebase and add it to a hashmap, each time that the main activity starts up
        eventListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (item.getItemId()){
            case R.id.information:
                AppDescriptionDialogFragment dialogFragment = new AppDescriptionDialogFragment();
                dialogFragment.show(fragmentManager, "info");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        eventListener();
    }
    // TODO: 4/2/17 High Priority: Add method to kick off searching events
    // TODO: 4/2/17 Low Priority: Add in a settings method and activity

    //button to submit an event
    public void eventSubmission(View view){
        Intent intent = new Intent(this, EventSubmissionActivity.class);
        startActivity(intent);
    }

    //button to browse events
    public void browseEvents(View view){
        // Not final of course, just seeing how the events list looks right now
        Intent intent = new Intent(this, EventsList.class).putExtra("EventSet", mEventSet).putExtra("EventMap", mEventMap);
        startActivity(intent);
    }

    //button to select map
    public void viewMap(View view){
        Intent intent = new Intent(this, MapsActivity.class).putExtra("EventSet", mEventSet).putExtra("EventMap", mEventMap);
        startActivity(intent);
    }

}
