package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import no.ntnu.sportsapp.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button lbutton;
    private Button cregister;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //Fetches and stores the text written in the inputs, will be used to send to server when button is pressed
        emailEditText = view.findViewById(R.id.lemail);
        passwordEditText = view.findViewById(R.id.lpwd);
        lbutton = view.findViewById(R.id.lbutton);
        cregister = view.findViewById(R.id.register);

        //Listeners for the buttons, declaring them for this fragment
        lbutton.setOnClickListener(this);
        cregister.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lbutton:
                System.out.println("LOGIN PRESSED");

                break;
            case R.id.register:
                Fragment fragment = new RegisterFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();
                break;
        }
    }
}