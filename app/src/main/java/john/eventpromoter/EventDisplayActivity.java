package john.eventpromoter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

/**
 * Created by ndseeg on 4/3/17.
 */

public class EventDisplayActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);
        Event event = (Event) getIntent().getSerializableExtra("Event");

        EditText editText = (EditText) findViewById(R.id.display_eventNameEdit);
        editText.setText(event.getEventName());
        editText.setKeyListener(null);

        editText = (EditText) findViewById(R.id.display_nameOrgEdit);
        editText.setText(event.getOrgName());
        editText.setKeyListener(null);

        editText = (EditText) findViewById(R.id.display_locationEdit);
        BuildingCodeLocationEnum buildingCode = BuildingCodeLocationEnum.valueOf(event.getBuildingCode());
        editText.setText(event.getBuildingCode() + " - " + buildingCode.getBuildingName());
        editText.setKeyListener(null);

        editText = (EditText) findViewById(R.id.display_roomEdit);
        editText.setText(event.getRoomNumber());
        editText.setKeyListener(null);

        editText = (EditText) findViewById(R.id.display_timeEdit);
        int hour = event.getHour();
        int minute = event.getMinute();
        String minuteString = String.valueOf(minute);
        String AM_PM = "AM";
        if(minute < 10){
            minuteString = "0" + minute;
        }
        if (hour >= 12){
            if (hour > 12){
                hour = hour - 12;
            }
            AM_PM = "PM";
        }
        if (hour == 0){
            hour = 12;
        }
        editText.setText(hour + ": " + minuteString + " " + AM_PM);
        editText.setKeyListener(null);

        editText = (EditText) findViewById(R.id.display_dateEdit);
        int year = event.getYear();
        int month = event.getMonth();
        int day = event.getDay();
        editText.setText(month + "/" + day + "/" + year);
        editText.setKeyListener(null);

        editText = (EditText) findViewById(R.id.display_foodProvidedEdit);
        editText.setText(event.getFoodProvided());
        editText.setKeyListener(null);

        editText = (EditText) findViewById(R.id.display_extraDetailsEdit);
        editText.setText(event.getExtraDetails());
        editText.setKeyListener(null);

        // TODO: 4/4/2017 Maybe have a button that shows the single event on the map; maybe for beta release 
    }
}
