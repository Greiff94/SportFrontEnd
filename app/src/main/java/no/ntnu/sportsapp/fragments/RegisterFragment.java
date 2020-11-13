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
import androidx.fragment.app.FragmentTransaction;

import no.ntnu.sportsapp.R;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText editfirstname;
    private EditText editlastname;
    private EditText editemail;
    private EditText editpwd;
    private EditText editpwd2;
    private Button rbutton;
    private Button clogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
    //Fetches and stores inputs from registerfragment
        editfirstname = view.findViewById(R.id.rfirstname);
        editlastname = view.findViewById(R.id.rlastname);
        editemail = view.findViewById(R.id.remail);
        editpwd = view.findViewById(R.id.rpwd);
        editpwd2= view.findViewById(R.id.rpwd2);
        rbutton = view.findViewById(R.id.rbutton);
        clogin = view.findViewById(R.id.rlogin);

        //Listeners for the buttons, declaring them for this fragment
        rbutton.setOnClickListener(this);
        clogin.setOnClickListener(this);

    return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.rbutton:
                System.out.println("TRYING TO REGISTER USER");
                break;
            case R.id.rlogin:
                Fragment fragment = new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();
                break;

        }
    }
}
