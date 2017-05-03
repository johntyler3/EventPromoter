package john.eventpromoter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Nathan Seegmiller on 5/2/2017.
 */

public class BuildingPickerDialogFragment extends DialogFragment {

    private static final String TAG = "BuildingPick";
    private static final String PREF_NAME = "Filter";

    private List<BuildingCodeLocationEnum> buildingCodes = Arrays.asList(BuildingCodeLocationEnum.values());
    private CharSequence[] items;
    private ArrayList<String> buildingsSelected = new ArrayList<>();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final List<String> buildingNames = new ArrayList<>();
        for (BuildingCodeLocationEnum buildingEnum : buildingCodes) {
            buildingNames.add(buildingEnum.toString());
        }
        items = buildingNames.toArray(new CharSequence[buildingNames.size()]);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity()).setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    buildingsSelected.add(items[which].toString());
                }
                else if (buildingsSelected.contains(items[which].toString())){
                    buildingsSelected.remove(buildingsSelected.indexOf(items[which].toString()));
                }
            }
        }).setPositiveButton(R.string.close,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, buildingsSelected.toString());
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Set<String> set = new HashSet<String>(buildingsSelected);
                        editor.putStringSet("buildings", set);
                        editor.commit();
                        dismiss();
                    }
                });
        return dialog.create();
    }
}
