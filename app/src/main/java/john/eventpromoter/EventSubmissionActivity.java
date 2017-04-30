package john.eventpromoter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nathan Seegmiller on 4/1/2017.
 */
public class EventSubmissionActivity extends Activity {

    //    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "EventPrefs";

    private String mEventName, mOrgName, mBuildingName, mRoomNumber, mExtraDetails, mFoodProvided;

    private int mhour, mminute;

    private ArrayAdapter<String> buildingNames;

    private static final String TAG = "EventSubmissionActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_submission);
        List<String> buildings = Arrays.asList(getResources().getStringArray(R.array.buildings));
        buildingNames = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, buildings);
        Spinner textView = (Spinner) findViewById(R.id.locationEdit);
        textView.setAdapter(buildingNames);
        mFoodProvided = "";
        mExtraDetails = "";
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

        Log.d(TAG, "Is the first line being logged, or is it just shitting the bed");
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        EditText editText = (EditText)findViewById(R.id.eventNameEdit);
        mEventName = editText.getText().toString();
        editText = (EditText)findViewById(R.id.nameOrgEdit);
        mOrgName = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        mhour = sharedPreferences.getInt("HourOfDay",-1);
        mminute = sharedPreferences.getInt("Minute",-1);
        int year = sharedPreferences.getInt("year",-1);
        int month = sharedPreferences.getInt("month",-1);
        int day = sharedPreferences.getInt("day",-1);
        Spinner spinner = (Spinner) findViewById(R.id.locationEdit);
//        editText = (EditText)findViewById(R.id.locationEdit);
        mBuildingName = spinner.getSelectedItem().toString();
        //Show only the three digit code associated with each building even though they choose from the full name, useful for enums
        if (!mBuildingName.isEmpty()){
            mBuildingName = (String) mBuildingName.subSequence(0,mBuildingName.indexOf("-")-1);
        }
        editText = (EditText)findViewById(R.id.roomEdit);
        mRoomNumber = editText.getText().toString();
        if (mEventName.isEmpty() || mOrgName.isEmpty() || mhour == -1 || year == -1 || mBuildingName.isEmpty() || mRoomNumber.isEmpty()){
            Toast.makeText(this, "Please Make Sure Event Details are Filled In Before Posting", Toast.LENGTH_SHORT).show();
            return;
        }
        editText = (EditText)findViewById(R.id.extraDetailsEdit);
        mExtraDetails = editText.getText().toString();
        editText = (EditText)findViewById(R.id.foodProvidedEdit);
        mFoodProvided = editText.getText().toString();
        Log.d(TAG, "What is the text if nothing is added? " + mFoodProvided);


        Event event = new Event(mhour, mminute,
                year, month, day,
                mEventName,
                mOrgName,
                mExtraDetails,
                mFoodProvided,
                mBuildingName,
                mRoomNumber);

        Log.d(TAG, event.toString());

        //Uncomment to post to database
        DatabaseReference mRef = database.getReference(month + "-" + day + "-" + year + ":" + event.getEventID());
        mRef.setValue(event);
        // TODO: 4/2/17 find a way to maintain the database and clean out old values
//        Log.d(TAG, "Event has been posted, with the values " + setValues);
        Toast.makeText(this, "Event Has Been Posted!", Toast.LENGTH_LONG).show();
        //Exit event post once it has been posted
        finish();
    }

}
