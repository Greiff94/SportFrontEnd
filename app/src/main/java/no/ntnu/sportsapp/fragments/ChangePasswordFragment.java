package no.ntnu.sportsapp.fragments;

import android.app.ActionBar;
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

import java.util.zip.Inflater;

import no.ntnu.sportsapp.R;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private Button changepwdBtn;
    private Button returnToProfile;
    private EditText currentPwd, newPwd, newPwdAgain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepwd, container, false);

        // Stores inputs from changepasswordfragment
        changepwdBtn = view.findViewById(R.id.changepwdButton);
        returnToProfile = view.findViewById(R.id.backtomyprofile);
        currentPwd = view.findViewById(R.id.currPwd);
        newPwd = view.findViewById(R.id.newPwd);
        newPwdAgain = view.findViewById(R.id.newPwdAgain);

        //Listeners for the buttons, declaring them for this fragment
        changepwdBtn.setOnClickListener(this);
        returnToProfile.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changepwdButton:
                changePassword();
                System.out.println("Change password button pressed");

                break;
            case R.id.backtomyprofile:
                Toast.makeText(view.getContext(), "BACK TO PROFILE WE GO", Toast.LENGTH_SHORT).show();
                Fragment newFragment = new MyProfileFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, newFragment).commit();
                break;

        }
    }


    public void changePassword() {
        // Makes password inputs as strings
        String currentPassword = currentPwd.getText().toString().trim();
        String newPassword = newPwd.getText().toString().trim();
        String newPasswordAgain = newPwdAgain.getText().toString().trim();


        // If different criterias isnt fulfilled, user wont be able to complete the task
        if (currentPassword.isEmpty()) {
            currentPwd.setError("Please enter your current password");
            currentPwd.requestFocus();
        }

        if (newPassword.isEmpty()) {
            newPwd.setError("Please enter a new password!");
            newPwd.requestFocus();
        }

        if (newPasswordAgain.isEmpty()) {
            newPwdAgain.setError("Please re-enter your new password");
        }

        if (!newPassword.equals(newPasswordAgain)) {
            newPwdAgain.setError("Password doesnt match! Try again!");
            newPwdAgain.requestFocus();
        }

    }
}
