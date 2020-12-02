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

import java.io.IOException;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button lbutton;
    private Button cregister;
    private Button forgotpwdBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lbutton:
                userLogin();
                System.out.println("LOGIN PRESSED");

                break;
            case R.id.lregister:
                Fragment fragment = new RegisterFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();
                break;

            case R.id.lforgotpwd:
                Fragment fpwdfragment = new ForgotPasswordFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fpwdfragment).commit();
                break;
        }
    }

    public void userLogin() {
        final String email = emailEditText.getText().toString().trim();
        final String pwd = passwordEditText.getText().toString().trim();

        final UserPrefs userPrefs = new UserPrefs(getContext());
        if (email.isEmpty()) {
            emailEditText.setError("Please enter a valid username");
            emailEditText.requestFocus();
        } else if (pwd.isEmpty()) {
            passwordEditText.setError("Please enter your password");
            passwordEditText.requestFocus();
        }

        Call<ResponseBody> call = ApiClient
                .getSingleton()
                .getApi()
                .login(email, pwd);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Fragment eventsFragment = new EventsFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    try {
                        userPrefs.setToken(response.body().string());
                        userPrefs.setUid(email);
                        userPrefs.setPwd(pwd);
                        getActivity().recreate();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    fragmentTransaction.replace(R.id.fragment_container, eventsFragment).commit();
                } else {
                    Toast.makeText(getContext(), "Email or password is wrong, please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Could not connect...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initViews(View view) {
        //Fetches and stores the text written in the inputs, will be used to send to server when button is pressed
        emailEditText = view.findViewById(R.id.lemail);
        passwordEditText = view.findViewById(R.id.lpwd);
        lbutton = view.findViewById(R.id.lbutton);
        cregister = view.findViewById(R.id.lregister);
        forgotpwdBtn = view.findViewById(R.id.lforgotpwd);

        //Listeners for the buttons, declaring them for this fragment
        lbutton.setOnClickListener(this);
        cregister.setOnClickListener(this);
        forgotpwdBtn.setOnClickListener(this);
    }
}