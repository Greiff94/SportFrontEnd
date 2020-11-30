package no.ntnu.sportsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private TextView txtViewSport, txtViewDesc, txtViewDate, txtViewTime, txtViewLocation, txtViewNumPlayers, txtViewMaxPlayers;
    private Button attendBtn, notAttendingBtn, generateTeamBtn, testParticBtn;

    private SupportMapFragment supportMapFragment;
    private GoogleMap map;

    private double latitude;
    private double longitude;

    private Bundle bundleExtras;


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
        txtViewNumPlayers = findViewById(R.id.eventJoinedPlayers);
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

        // Changing latlng string to get coordinates only.
        // Splitting the coordinates with the ,
        String latLng = bundleExtras.getString("latLng");
        latLng = latLng.replace("lat/lng:", "");
        latLng = latLng.replace("(", "");
        latLng = latLng.replace(")", "");
        String[] latLongSplit = latLng.split(",");
        latitude = Double.parseDouble(latLongSplit[0]);
        longitude = Double.parseDouble(latLongSplit[1]);

        attendBtn.setOnClickListener(this);
        notAttendingBtn.setOnClickListener(this);
        testParticBtn.setOnClickListener(this);

        updateNumPlayers();
        getButtonStatus();
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

    public void updateNumPlayers() {
        if (bundleExtras != null) {
            long eventid = bundleExtras.getLong("eventid");
            Call<Integer> call = ApiClient
                    .getSingleton()
                    .getApi()
                    .getSize(eventid);

            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.isSuccessful()) {
                        int numPlayers = response.body();
                        String numPlayersString = Integer.toString(numPlayers);
                        txtViewNumPlayers.setText(numPlayersString);
                    } else {
                        Toast.makeText(EventActivity.this, "Unable to fetch users...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(EventActivity.this, "Could not connect...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void viewUsers() {
        if (bundleExtras != null) {
            long eventid = bundleExtras.getLong("eventid");
            Bundle bundle = new Bundle();
            bundle.putLong("eventid", eventid);

            Intent intent = new Intent(EventActivity.this, FragmentActivity.class);
            intent.putExtra("eventid", eventid);
            EventActivity.this.startActivity(intent);
        }
    }

    // Gets a google map that has a marker for the events location
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
        String txtNumPlayers = txtViewNumPlayers.getText().toString().trim();
        String txtMaxPlayers = txtViewMaxPlayers.getText().toString().trim();
        final int numPlayers = Integer.parseInt(txtNumPlayers);
        final int maxPlayers = Integer.parseInt(txtMaxPlayers);
        String token = "Bearer " + userPrefs.getToken();

        if (bundleExtras != null) {
            long eventid = bundleExtras.getLong("eventid");

            System.out.println(eventid);
            Call<ResponseBody> call = ApiClient
                    .getSingleton()
                    .getApi()
                    .joinEvent(token, eventid);

            if (numPlayers < maxPlayers) {
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()) {
                            System.out.println("Number of players and max players: " + numPlayers + "of" + maxPlayers);
                            updateNumPlayers();
                            Toast.makeText(EventActivity.this, "You have joined an event", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 500) {
                            Toast.makeText(EventActivity.this, "You are already attending", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EventActivity.this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show();
                            System.out.println("Response when something is wrong: " + response);
                        }
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(EventActivity.this, "Could not connect...", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "This event is full, unable to join.", Toast.LENGTH_SHORT).show();
            }
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
                        updateNumPlayers();
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

    public void getButtonStatus() {
        final UserPrefs userPrefs = new UserPrefs(this);
        RadioGroup radioGroup = findViewById(R.id.radioButtonView);
        radioGroup.check(userPrefs.getButtonState());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                userPrefs.setButtonState(i);
            }
        });
    }
}
