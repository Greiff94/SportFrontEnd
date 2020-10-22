package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import no.ntnu.sportsapp.R;

public class RegisterFragment extends Fragment {
    private EditText rfirstname;
    private EditText rlastname;
    private EditText remail;
    private EditText pwd;
    private EditText pwd2;
    private Button rbutton;
    private Button rlogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

    return view;
    }
}
