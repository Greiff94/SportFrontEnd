package no.ntnu.sportsapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.fragments.SignedUpFragment;

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView txtViewSport, txtViewDesc, txtViewDate, txtViewTime, txtViewLocation, txtViewMaxPlayers;
    private Button attendBtn, notAttendingBtn, generateTeamBtn;

    private SupportMapFragment supportMapFragment;
    private GoogleMap map;

    private double latitude;
    private double longitude;

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

        Bundle bundleExtra = getIntent().getExtras();

        // Changing
        String latLng = bundleExtra.getString("latLng");
        latLng = latLng.replace("lat/lng:", "");
        latLng = latLng.replace("(", "");
        latLng = latLng.replace(")", "");
        String[] latLongSplit = latLng.split(",");
        latitude = Double.parseDouble(latLongSplit[0]);
        longitude = Double.parseDouble(latLongSplit[1]);

        //Sends eventid to  SignedupUserFragment
        //So that we can fetch signed up users
        String eventid = this.getIntent().getStringExtra("id");
        Bundle bundle = new Bundle();
        bundle.putString(eventid, "From Activity");
// set Fragmentclass Arguments
        SignedUpFragment fragobj = new SignedUpFragment();
        fragobj.setArguments(bundle);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float zoomLevel = 15.0f;
        map = googleMap;
        LatLng chosenLocation = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(chosenLocation));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(chosenLocation, zoomLevel));
    }
}
