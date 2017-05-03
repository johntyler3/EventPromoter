package john.eventpromoter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Nathan Seegmiller on 5/2/2017.
 */

public class FilterEventDialog extends DialogFragment {

    private static final String TAG = "FilterDialog";

    final CharSequence[] items = {" Date", " Time", " Building", " Org Name", " Food"};
    private FragmentManager fragmentManager = getFragmentManager();
    private ArrayList<String> itemsChecked = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "Filter";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("Filter", Context.MODE_PRIVATE);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity()).setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    itemsChecked.add(String.valueOf(which));
                    switch (which){

                        case 0: DialogFragment dateFragment = new DatePickerDialogFragmentFilter();
                            dateFragment.show(getFragmentManager(), "datePicker"); break;
                        case 1: DialogFragment timeFragment = new TimePickerDialogFragmentFilter();
                            timeFragment.show(getFragmentManager(), "timePicker"); break;
                        case 2: DialogFragment buildingFragment = new BuildingPickerDialogFragment();
                            buildingFragment.show(getFragmentManager(), "buildingPicker"); break;
                        // TODO: 5/3/2017 Add cases for orgs, pass in a list of available orgs, will take in a bundle to do that
                    }
                    // If the user checked the item, add it to the selected items
                } else if (itemsChecked.contains(String.valueOf(which))){
                    itemsChecked.remove(itemsChecked.indexOf(String.valueOf(which)));
                    // Else, if the item is already in the array, remove it
                }
            }
        }).setPositiveButton(R.string.close,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(getActivity(), "Something was pressed " + itemsChecked.toString(), Toast.LENGTH_LONG).show();
//                        sharedPreferences = getActivity().getSharedPreferences("Filter", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        // TODO: 5/3/2017 Make sure that the filters are cleared so as not to contaminate results. Having trouble storing, adding to filter, if only more time
                        if (itemsChecked.contains("0")){
                            editor.putBoolean("dateCheck", true);
                        }
                        if (itemsChecked.contains("1")){
                            editor.putBoolean("timeCheck", true);
                        }
                        if (itemsChecked.contains("2")){
                            editor.putBoolean("buildingCheck", true);
                        }
                        if (itemsChecked.contains("3")){
                            editor.putBoolean("orgCheck", true);
                        }
                        if (itemsChecked.contains("4")){
                            editor.putBoolean("foodCheck", true);
                        }
                        Log.d(TAG, "Setting FilterCheck as false");
                        editor.putBoolean("FilterCheck", false);

                        editor.commit();
                        int year, month, day;
                        year = month = day = -1;
                        if (itemsChecked.contains("0")){
                            year = sharedPreferences.getInt("year", -1);
                            month = sharedPreferences.getInt("month", -1);
                            day = sharedPreferences.getInt("day", -1);
                        }
                        Set<String> buildingSet = sharedPreferences.getStringSet("buildings", null);
                        Log.d(TAG, "Year: " + year + " Month: " + month + " Day: " + day);
                        // // TODO: 5/3/2017 call the method that will filter here?

                        dismiss();

                    }
                });


        return dialog.create();


    }
}
