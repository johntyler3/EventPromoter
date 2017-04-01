package john.eventpromoter;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
//import android.icu.util.Calendar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Nathan Seegmiller on 4/1/2017.
 */

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private static final String PREF_NAME = "EventPrefs";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HourOfDay", hourOfDay);
        editor.putInt("Minute", minute);
        editor.apply();
        EditText editText = (EditText) this.getActivity().findViewById(R.id.timeEdit);
        editText.setText(hourOfDay + ": " + minute);
        // Do something with the time chosen by the user
    }

}
