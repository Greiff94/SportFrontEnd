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
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private Button changepwdBtn;
    private Button returnToProfile;
    private EditText currentPwd, newPwd, newPwdAgain;
    private String uid;
    private String pwd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepwd, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.changepwdButton:
                changePassword();
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
        final String currentPassword = currentPwd.getText().toString().trim();
        final String newPassword = newPwd.getText().toString().trim();
        String newPasswordAgain = newPwdAgain.getText().toString().trim();

        final UserPrefs userPrefs = new UserPrefs(getContext());
        String token = "Bearer " + userPrefs.getToken();
        uid = userPrefs.getUid();
        pwd = userPrefs.getPwd();

        // If different criterias isnt fulfilled, user wont be able to complete the task
        if (currentPassword.isEmpty()) {
            currentPwd.setError("Please enter your current password");
            currentPwd.requestFocus();
        }

        if (!currentPassword.equals(pwd)) {
            currentPwd.setError("Current password is wrong!");
            currentPwd.requestFocus();
        }

        if (newPassword.isEmpty()) {
            newPwd.setError("Please enter a new password!");
            newPwd.requestFocus();
        }

        if (newPasswordAgain.isEmpty()) {
            newPwdAgain.setError("Please re-enter your new password");
        }

        if (currentPassword.equals(newPassword)) {
            newPwd.setError("New password matches current password!");
            newPwd.requestFocus();
        }

        if (!newPassword.equals(newPasswordAgain)) {
            newPwd.setError("Password doesn't match! Try again!");
            newPwdAgain.setError("Password doesn't match! Try again!");
            newPwd.requestFocus();
            newPwdAgain.requestFocus();
        }
        if (currentPassword.equals(newPassword)) {
            newPwd.setError("New password can't be you current one!");
            newPwd.requestFocus();
        }
        if (currentPassword.equals(pwd) && !currentPassword.equals(newPassword) && newPassword.equals(newPasswordAgain)) {
            Call<ResponseBody> call = ApiClient
                    .getSingleton()
                    .getApi()
                    .changePassword(token, uid, newPassword);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        userPrefs.setPwd(newPassword);
                        Fragment myProfile = new MyProfileFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, myProfile).commit();
                        Toast.makeText(getContext(), "Password changed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Could not change password. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Could not connect...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void initViews(View view) {
        // Stores inputs from changepasswordfragment
        changepwdBtn = view.findViewById(R.id.changepwdButton);
        returnToProfile = view.findViewById(R.id.backtomyprofile);
        currentPwd = view.findViewById(R.id.currPwd);
        newPwd = view.findViewById(R.id.newPwd);
        newPwdAgain = view.findViewById(R.id.newPwdAgain);

        //Listeners for the buttons, declaring them for this fragment
        changepwdBtn.setOnClickListener(this);
        returnToProfile.setOnClickListener(this);
    }
}
