package john.eventpromoter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by E on 4/4/2017.
 * Dialog to confirm whether or not the user wants to go to an event page from the events list
 * May in the future contain a small summary of info about the event
 */

public class InfoBoxDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // copied code from tictactoe project
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.event_page_question)
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((EventsList) getActivity()).goToEventPage(getArguments().getString("eventID"));
                                dismiss();
                            }
                        })
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dismiss();
                            }
                        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static InfoBoxDialogFragment newInstance(String eventID) {
        InfoBoxDialogFragment f = new InfoBoxDialogFragment();

        Bundle args = new Bundle();
        args.putString("eventID", eventID);
        f.setArguments(args);

        return f;
    }
}
