package no.ntnu.sportsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.adapter.UserListAdapter;
import no.ntnu.sportsapp.model.User;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import no.ntnu.sportsapp.fragments.SignedUpFragment;

public class EventActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private TextView txtViewSport, txtViewDesc, txtViewDate, txtViewTime, txtViewLocation, txtViewMaxPlayers;
    private Button attendBtn, notAttendingBtn, generateTeamBtn, testParticBtn;

    private SupportMapFragment supportMapFragment;
    private GoogleMap map;

    private double latitude;
    private double longitude;

    private Bundle bundleExtras;
    private ArrayList<User> signups = new ArrayList<>();
    private UserListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // TextView Init
        txtViewSport = findViewById(R.id.eventsport);
        txtViewDesc = findViewById(R.id.eventdesc);
        txtViewDate = findViewById(R.id.eventdate);
        txtViewTime = findViewById(R.id.eventtime);
        txtViewMaxPlayers = findViewById(R.id.eventMaxPlayers);
        txtViewLocation = findViewById(R.id.eventlocation);

        // Init buttons
        attendBtn = findViewById(R.id.eventAttendbtn);
        notAttendingBtn = findViewById(R.id.eventNotAttendbtn);
        generateTeamBtn = findViewById(R.id.generateTeambtn);
        testParticBtn = findViewById(R.id.testingParticipants);

        supportMapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.googleMapEvent);
        supportMapFragment.getMapAsync(this);


        txtViewSport.setText(getIntent().getStringExtra("sport"));
        txtViewDesc.setText(getIntent().getStringExtra("description"));
        txtViewDate.setText(getIntent().getStringExtra("date"));
        txtViewTime.setText(getIntent().getStringExtra("time"));
        txtViewMaxPlayers.setText(getIntent().getStringExtra("maxPlayers"));
        txtViewLocation.setText(getIntent().getStringExtra("location"));

        System.out.println("==========================================");
        System.out.println(" ");
        System.out.println("EVENT ACTIVITY OPENED");
        System.out.println(" ");
        System.out.println("==========================================");

        bundleExtras = getIntent().getExtras();

        // Changing
        String latLng = bundleExtras.getString("latLng");
        latLng = latLng.replace("lat/lng:", "");
        latLng = latLng.replace("(", "");
        latLng = latLng.replace(")", "");
        String[] latLongSplit = latLng.split(",");
        latitude = Double.parseDouble(latLongSplit[0]);
        longitude = Double.parseDouble(latLongSplit[1]);

        attendBtn.setOnClickListener(this);
        notAttendingBtn.setOnClickListener(this);
        txtViewMaxPlayers.setOnClickListener(this);
        testParticBtn.setOnClickListener(this);

        //Sends eventid to  SignedupUserFragment
        //So that we can fetch signed up users


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.eventAttendbtn:
                joinEvent();
                break;

            case R.id.eventNotAttendbtn:
                leaveEvent();
                break;

            case R.id.generateTeambtn:
                break;

            case R.id.testingParticipants:
                viewUsers();
                break;
        }
    }

    private void viewUsers() {
        if (bundleExtras != null) {
            long eventid = bundleExtras.getLong("eventid");
            Bundle bundle = new Bundle();
            bundle.putLong( "eventid", eventid);

            Intent intent = new Intent(EventActivity.this, FragmentActivity.class);
            intent.putExtra("eventid", eventid);
            intent.putExtra("users", "users");
            EventActivity.this.startActivity(intent);
            System.out.println("USERS");
        }
    }

    private void generateTeams() {
        if (bundleExtras != null) {
            long eventid = bundleExtras.getLong("eventid");
            Bundle bundle = new Bundle();
            bundle.putLong( "eventid", eventid);

            Intent intent = new Intent(EventActivity.this, FragmentActivity.class);
            intent.putExtra("eventid", eventid);
            intent.putExtra("generate", "generate");
            EventActivity.this.startActivity(intent);
            System.out.println("GENERATE");
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        float zoomLevel = 15.0f;
        map = googleMap;
        LatLng chosenLocation = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(chosenLocation));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation, zoomLevel));
    }

    public void joinEvent() {
        final UserPrefs userPrefs = new UserPrefs(this);
        String token = "Bearer " + userPrefs.getToken();

        if (bundleExtras != null) {
            long eventid = bundleExtras.getLong("eventid");

            System.out.println(eventid);
            Call<ResponseBody> call = ApiClient
                    .getSingleton()
                    .getApi()
                    .joinEvent(token, eventid);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EventActivity.this, "ATTENDING", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EventActivity.this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(EventActivity.this, "Could not connect...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void leaveEvent() {

        final UserPrefs userPrefs = new UserPrefs(this);

        String token = "Bearer " + userPrefs.getToken();

        if (bundleExtras != null) {
            long eventid = bundleExtras.getLong("eventid");

            Call<ResponseBody> call = ApiClient
                    .getSingleton()
                    .getApi()
                    .leaveEvent(token, eventid);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EventActivity.this, "NOT ATTENDING", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EventActivity.this, "TRY AGAIN", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }


}
