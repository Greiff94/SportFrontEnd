package no.ntnu.sportsapp.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import no.ntnu.sportsapp.R;

public class AddEventFragment extends Fragment implements View.OnClickListener {

    TextView timeView, dateView;
    private Button dateButton;
    private Button timeButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addevent, container, false);

        timeView = view.findViewById(R.id.eventtime);
        timeButton = view.findViewById(R.id.eventTimebtn);
        dateView = view.findViewById(R.id.eventdate);
        dateButton = view.findViewById(R.id.eventDatebtn);

        //Listeners for the buttons, declaring them for this fragment
        timeButton.setOnClickListener(this);
        dateButton.setOnClickListener(this);


        // Get spinner(dropdown) from the xml file
        Spinner dropDown = (Spinner) view.findViewById(R.id.sportdropdown);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sports_array));
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropDown.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.eventTimebtn:
                setTimeButton();
                Toast.makeText(view.getContext(), "TIME BUTTON PRESSEED", Toast.LENGTH_SHORT).show();
                break;
            case R.id.eventDatebtn:
                setDateButton();
                Toast.makeText(view.getContext(), "Date set", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void setTimeButton() {

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

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar1.set(Calendar.MONTH, monthOfYear);
                calendar1.set(Calendar.YEAR, year);
                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

                dateView.setText(simpleDateFormat.format(calendar1.getTime()));
                Toast.makeText(getContext(), dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
            }
        }, day, month, year);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
}
