package no.ntnu.sportsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.fragments.SignedUpFragment;
import retrofit2.Call;

public class FragmentActivity extends AppCompatActivity {
long eventid;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);
        Intent intent = getIntent();
        eventid = intent.getLongExtra("eventid", 0);
        System.out.println(eventid);

        System.out.println("FRAGMENTACRTIVITY OPENED");
        if(intent.getStringExtra("users").equals("users")){
            listUser();
        } else {
            generateTeams();
        }

    }

    private void generateTeams() {
        Bundle bundle = new Bundle();
        bundle.putLong("eventid", eventid);
        TeamGeneratorFragment fragobj = new TeamGeneratorFragment();
        fragobj.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_activityholder, fragobj).commit();
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
