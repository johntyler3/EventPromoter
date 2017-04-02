package john.eventpromoter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // TODO: 4/2/17 High Priority: Add method to kick off searching events
    // TODO: 4/2/17 Low Priority: Add in a settings method and activity

    public void eventSubmission(View view){
        Intent intent = new Intent(this, EventSubmission.class);
        startActivity(intent);
    }
}
