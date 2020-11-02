package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import no.ntnu.sportsapp.R;

public class MyProfileFragment extends Fragment {

    TextView profileNameView, usernameInfoView, emailView, phoneNumberView, changePassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);

        profileNameView = view.findViewById(R.id.username);
        usernameInfoView = view.findViewById(R.id.usernameinfo);
        emailView = view.findViewById(R.id.emailinfo);
        phoneNumberView = view.findViewById(R.id.phoneinfo);
        changePassword = view.findViewById(R.id.changepwd);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment changePwdFragment =  new ChangePasswordFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, changePwdFragment).commit();
            }
        });

        return view;
    }

    public void userInfo() {
        String profileName = profileNameView.getText().toString().trim();
        String usernameInfo = usernameInfoView.getText().toString().trim();
        String email = emailView.getText().toString().trim();
        String phoneNumber = phoneNumberView.getText().toString().trim();



    }
}
