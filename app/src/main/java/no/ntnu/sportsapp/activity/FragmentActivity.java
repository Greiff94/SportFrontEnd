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



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);
        Intent intent = getIntent();
        long eventid = intent.getLongExtra("eventid", 0);
        System.out.println(eventid);

        System.out.println("FRAGMENTACRTIVITY OPENED");

// set Fragmentclass Arguments
        Bundle bundle = new Bundle();
        bundle.putLong("eventid", eventid);
        SignedUpFragment fragobj = new SignedUpFragment();
        fragobj.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_activityholder, fragobj).commit();

    }

}
