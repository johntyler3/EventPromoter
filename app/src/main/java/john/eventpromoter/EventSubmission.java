package john.eventpromoter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Nathan Seegmiller on 4/1/2017.
 */
public class EventSubmission extends Activity {

    //    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "EventPrefs";

    private String mEventName, mOrgName;

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
        EditText editText = (EditText)findViewById(R.id.eventNameEdit);
        mEventName = editText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        mhour = sharedPreferences.getInt("HourOfDay",-1);
        mminute = sharedPreferences.getInt("Minute",-1);
        String setValues = String.format("{mhour: %d, mminute: %d, mEventName: %s, mOrgName: %s}", mhour, mminute, mEventName, mOrgName);
        Log.d(TAG, "Event has been posted, with the values " + setValues);
//        Toast toast = new Toast();
        //Exit event post once it has been posted
        finish();
    }

}
