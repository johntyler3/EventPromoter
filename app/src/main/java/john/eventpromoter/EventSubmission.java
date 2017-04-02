package john.eventpromoter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Nathan Seegmiller on 4/1/2017.
 */
public class EventSubmission extends Activity {

    //    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "EventPrefs";

    private String mEventName, mOrgName, mBuildingName, mRoomNumber, mExtraDetails, mFoodProvided;

    private int mhour, mminute;

    private static final String TAG = "EventSubmission";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_submission);
    }

    public void setTime(View view){
        DialogFragment newFragment = new TimePickerDialogFragment();
        newFragment.show(getFragmentManager(), "timePicker");
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

    }

    public void setDate(View view){
        DialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.show(getFragmentManager(), "datePicker");

    }

    public void postEvent(View view){

        FirebaseDatabase database = FirebaseDatabase.getInstance();

//        DatabaseReference mRef = database.getReference("eventpromoter-20168");
        EditText editText = (EditText)findViewById(R.id.eventNameEdit);
        mEventName = editText.getText().toString();
//        event.setEventName(editText.getText().toString());
        editText = (EditText)findViewById(R.id.nameOrgEdit);
        mOrgName = editText.getText().toString();
//        event.setOrgName(editText.getText().toString());
        // TODO: 4/2/17  Add check to make sure that users are not missing any required fields, and defaults are set for non required fields
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        mhour = sharedPreferences.getInt("HourOfDay",-1);
        mminute = sharedPreferences.getInt("Minute",-1);
        int year = sharedPreferences.getInt("year",-1);
        int month = sharedPreferences.getInt("month",-1);
        int day = sharedPreferences.getInt("day",-1);
//        String setValues = String.format("{mhour: %d, mminute: %d, mEventName: %s, mOrgName: %s}", mhour, mminute, mEventName, mOrgName);
        mBuildingName = "";
        mExtraDetails = "";
        mRoomNumber = "";
        mFoodProvided = "";
        // TODO: 4/2/17 Switch location to a spinner and have the GPS coordinates associated with each
        Event event = new Event(mhour, mminute,
                year, month, day,
                mEventName,
                mOrgName,
                mExtraDetails,
                mFoodProvided,
                mBuildingName,
                mRoomNumber);
        DatabaseReference mRef = database.getReference(event.getEventID());
        mRef.setValue(event);
        // TODO: 4/2/17 find a way to maintain the database and clean out old values
//        Log.d(TAG, "Event has been posted, with the values " + setValues);
//        Toast toast = new Toast();
        //Exit event post once it has been posted
        finish();
    }

}
