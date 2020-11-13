package no.ntnu.sportsapp.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import no.ntnu.sportsapp.R;

public class AddEventFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {




    GoogleMap map;
    SupportMapFragment supportMapFragment;
    AutocompleteSupportFragment autocompleteFragment;
    PlacesClient placesClient;

    private TextView timeView, dateView;
    private EditText editTextSpotsAvailable,editTextDesc;
    private Spinner dropDownSport, dropDownInOut;

    private String apiKey;

    private Button dateButton;
    private Button timeButton;
    private Button createButton;

    private LatLng latLng;
    private Double latitude;
    private Double longitude;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addevent, container, false);
        // Initialize textview
        timeView = view.findViewById(R.id.eventtime);
        dateView = view.findViewById(R.id.eventdate);
        // Initialize edittext
        editTextSpotsAvailable = view.findViewById(R.id.eventnumofpeople);
        editTextDesc = view.findViewById(R.id.addeventdesc);
        // Initialize buttons
        dateButton = view.findViewById(R.id.eventDatebtn);
        timeButton = view.findViewById(R.id.eventTimebtn);
        createButton = view.findViewById(R.id.createEventbtn);

        // Get spinner(dropdown) from the xml file
        dropDownSport = (Spinner) view.findViewById(R.id.sportdropdown);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sports_array));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDownSport.setAdapter(adapter);

        apiKey = getString(R.string.map_key);

        // Initialize places
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), apiKey);
        }

        // Create new Places client instance
        placesClient = Places.createClient(view.getContext());


        // Map fragment init
        supportMapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.googleMap);
        supportMapFragment.getMapAsync(this);

        autocompleteFragment = (AutocompleteSupportFragment)
                this.getChildFragmentManager().findFragmentById(R.id.autocompletefragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        //Listeners for the buttons, declaring them for this fragment
        timeButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        createButton.setOnClickListener(this);

        // Listener for autcomplete fragment.
        // Sets location and adds marker on the google map.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                String chosenLocation = place.toString();
                List<Address> addressList = null;

                if (chosenLocation != null || !chosenLocation.equals("")) {
                    Geocoder geocoder = new Geocoder(getContext());
                    try {
                        addressList = geocoder.getFromLocationName(chosenLocation, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);

                    map.clear();
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    map.addMarker(new MarkerOptions().position(latLng));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    latitude = latLng.latitude;
                    longitude = latLng.longitude;

                }
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });



        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.createEventbtn:
                addEvent();
                break;
            case R.id.eventTimebtn:
                setTimeButton();
                break;
            case R.id.eventDatebtn:
                setDateButton();
                break;
        }
    }

    private void setTimeButton() {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        boolean is24Format = DateFormat.is24HourFormat(getContext());

        TimePickerDialog timePickerDialog = new TimePickerDialog(Objects.requireNonNull(getContext()), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                // Creates format for time view.
                String timeFormat = String.format("%02d:%02d", hour, minute);
                timeView.setText(timeFormat);
                Toast.makeText(getContext(), "Time set!", Toast.LENGTH_SHORT).show();
            }
        }, hour, minute, is24Format);
        timePickerDialog.show();

    }

    private void setDateButton() {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.YEAR, year);
                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

                dateView.setText(simpleDateFormat.format(myCalendar.getTime()));
                Toast.makeText(getContext(), "Date set!", Toast.LENGTH_SHORT).show();
            }
        }, day, month, year);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float zoomLevel = 10.0f;
        map = googleMap;
        LatLng aalesund = new LatLng(62.4681226, 6.1714086);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(aalesund, zoomLevel));
    }

    public void addEvent() {
        String spotsAvailable = editTextSpotsAvailable.getText().toString().trim();
        String description = editTextDesc.getText().toString().trim();

        String date = dateView.getText().toString().trim();
        String time = timeView.getText().toString().trim();

        String sportSelected = dropDownSport.getSelectedItem().toString().trim();
        String chosenLocation = latLng.toString().trim();







    }
}
