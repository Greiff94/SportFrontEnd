package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.model.User;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfileFragment extends Fragment {

    TextView fNameView, lNameview, emailView, changePassword;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myprofile, container, false);

        fNameView = view.findViewById(R.id.fname);
        lNameview = view.findViewById(R.id.lname);
        emailView = view.findViewById(R.id.emailinfo);
        changePassword = view.findViewById(R.id.changepwd);

        userInfo();

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment changePwdFragment = new ChangePasswordFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, changePwdFragment).commit();
            }
        });

        return view;
    }

    public void userInfo() {
        final UserPrefs userPrefs = new UserPrefs(getContext());
        String token = "Bearer " + userPrefs.getToken();

        Call<User> call = ApiClient
                .getSingleton()
                .getApi()
                .currentUser(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    user = response.body();
                    String fName = user.getFirstname();
                    String lName = user.getLastname();
                    String email = user.getUid();

                    System.out.println(fName+ " " + lName + " " + email);

                    fNameView.setText(fName);
                    lNameview.setText(lName);
                    emailView.setText(email);
                } else {
                    Toast.makeText(getContext(), "Could not fetch data. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Could not connect", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
