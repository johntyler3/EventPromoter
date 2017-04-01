package john.eventpromoter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Nathan Seegmiller on 4/1/2017.
 */
public class EventSubmission extends Activity {

    private String mEventName, mOrgName;

    private static final String TAG = "EventSubmission";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_submission);
    }

    public void postEvent(View view){
        EditText editText = (EditText)findViewById(R.id.eventNameEdit);
        mEventName = editText.getText().toString();
        Log.d(TAG, "Event has been posted, with the name " + mEventName);
//        Toast toast = new Toast();
        //Exit event post once it has been posted
        finish();
    }

}
