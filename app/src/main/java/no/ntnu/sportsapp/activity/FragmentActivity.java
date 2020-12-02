package no.ntnu.sportsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.fragments.SignedUpFragment;
import no.ntnu.sportsapp.fragments.TeamGeneratorFragment;

public class FragmentActivity extends AppCompatActivity {
long eventid;
private Button gButton;
private EditText numberOfTeams;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);
        Intent intent = getIntent();
        eventid = intent.getLongExtra("eventid", 0);
        System.out.println(eventid);

        listUser();

        gButton = findViewById(R.id.generateButton);
        numberOfTeams = findViewById(R.id.numberOfTeams);

        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateTeams();
            }
        });
    }

    public void generateTeams() {
        String string = numberOfTeams.getText().toString().trim();
        int teamAmount = Integer.parseInt(string);
        Bundle bundle = new Bundle();
        bundle.putLong("eventid", eventid);
        bundle.putInt("number", teamAmount);
        TeamGeneratorFragment fragObj = new TeamGeneratorFragment();
        fragObj.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_activityholder, fragObj).commit();
    }

    public void listUser() {
        Bundle bundle = new Bundle();
        bundle.putLong("eventid", eventid);
        SignedUpFragment fragobj = new SignedUpFragment();
        fragobj.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_activityholder, fragobj).commit();
    }
}
