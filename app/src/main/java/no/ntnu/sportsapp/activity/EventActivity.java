package no.ntnu.sportsapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import no.ntnu.sportsapp.R;

public class EventActivity extends AppCompatActivity {

    private TextView txtViewSport, txtViewDesc, txtViewDate, txtViewTime, txtViewLocation, txtViewMaxPlayers;
    private Button attendBtn, notattendingBtn, generateTeamBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_event);

        txtViewSport = findViewById(R.id.eventsport);
        txtViewDesc = findViewById(R.id.eventdesc);
        txtViewDate = findViewById(R.id.eventdate);
        txtViewTime = findViewById(R.id.eventtime);
        txtViewMaxPlayers = findViewById(R.id.eventMaxPlayers);
        txtViewLocation = findViewById(R.id.eventlocation);

        //txtViewSport.setText(getIntent().getStringExtra("sport"));
        //txtViewDesc.setText(getIntent().getStringExtra("description"));
        //txtViewDate.setText(getIntent().getStringExtra("date"));
        //txtViewTime.setText(getIntent().getStringExtra("time"));
        //txtViewMaxPlayers.setText(getIntent().getStringExtra("maxPlayers"));
        //txtViewLocation.setText(getIntent().getStringExtra("location"));

        System.out.println("EVENT ACTIVITY OPENED");


    }
}
