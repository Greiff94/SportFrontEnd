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
import androidx.fragment.app.FragmentTransaction;

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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {


    private GoogleMap map;
    private SupportMapFragment supportMapFragment;
    private AutocompleteSupportFragment autocompleteFragment;
    private PlacesClient placesClient;

    private TextView timeView, dateView;
    private EditText editTextSpotsAvailable, editTextDesc;
    private Spinner dropDownSport;

    private String apiKey;

    private Button dateButton;
    private Button timeButton;
    private Button createButton;

    private String locationName;
    private LatLng latLng;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addevent, container, false);

        initViews(view);

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

        autocompleteFragment.setCountry("NO");
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
                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        map.clear();
                        latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.addMarker(new MarkerOptions().position(latLng));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        locationName = address.getLocality();
                        System.out.println("Location name is: " + locationName);
                    } else {
                        latLng = new LatLng(0,0);
                        locationName = "No location currently available";
                        Toast.makeText(getContext(), "Unable to add marker to the map!", Toast.LENGTH_SHORT).show();
                    }
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
        String sport = dropDownSport.getSelectedItem().toString().trim();
        String description = editTextDesc.getText().toString().trim();
        String date = dateView.getText().toString().trim();
        String location = "";
        String time = timeView.getText().toString().trim();
        String latlngLocation = latLng.toString();
        int maxPlayers = 0;

        try {
            maxPlayers  = Integer.parseInt(editTextSpotsAvailable.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            editTextSpotsAvailable.requestFocus();
            editTextSpotsAvailable.setError("Need at least 1 participant");
        }

        try {
            location = locationName.trim();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }

        if (description.isEmpty()) {
            editTextDesc.setError("Description is required");
            editTextDesc.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            dateView.setError("Please select a date!");
            dateView.requestFocus();
            return;
        }

        if (time.isEmpty()) {
            timeView.setError("Please select a time for the event!");
            timeView.requestFocus();
            return;
        }

        final UserPrefs userPrefs = new UserPrefs(getContext());
        final String token = "Bearer " + userPrefs.getToken();

        Call<ResponseBody> call = ApiClient
                .getSingleton()
                .getApi()
                .addEvent(token, sport, description, date, location, time, maxPlayers, latlngLocation);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Event added!", Toast.LENGTH_SHORT).show();
                    Fragment eventsFragment = new EventsFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, eventsFragment).commit();
                } else {
                    Toast.makeText(getContext(), "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Could not connect...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initViews(View view) {
        // Initialize textview
        timeView = view.findViewById(R.id.addeventtime);
        dateView = view.findViewById(R.id.addeventdate);
        // Initialize edittext
        editTextSpotsAvailable = view.findViewById(R.id.eventnumofpeople);
        editTextDesc = view.findViewById(R.id.addeventdesc);
        // Initialize buttons
        dateButton = view.findViewById(R.id.eventDatebtn);
        timeButton = view.findViewById(R.id.eventTimebtn);
        createButton = view.findViewById(R.id.createEventbtn);
    }




}
