package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.util.Patterns;
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
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        editpwd2 = view.findViewById(R.id.rpwd2);
        rbutton = view.findViewById(R.id.rbutton);
        clogin = view.findViewById(R.id.rlogin);

        //Listeners for the buttons, declaring them for this fragment
        rbutton.setOnClickListener(this);
        clogin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbutton:
                System.out.println("TRYING TO REGISTER USER");
                userRegister();
                break;
            case R.id.rlogin:
                Fragment fragment = new LoginFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment).commit();
                break;


        }
    }

    private void userRegister() {
        final String email = editemail.getText().toString().trim();
        String fname = editfirstname.getText().toString().trim();
        String lname = editlastname.getText().toString().trim();
        String pwd = editpwd.getText().toString().trim();
        String pwd2 = editpwd2.getText().toString().trim();

        //------LIST OF REQUIREMENTS FOR USERCREATION---------//

        if (fname.isEmpty()) {
            editfirstname.setError("Please enter your first name");
            editfirstname.requestFocus();
            return;
        }
        if (lname.isEmpty()) {
            editlastname.setError("Please enter your first name");
            editlastname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editemail.setError("Email is required");
            editemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Enter a valid email");
            editemail.requestFocus();
            return;

        }
        if (pwd.length() < 3) {
            editpwd.setError("Password must be 3 characters or longer");
            editpwd.requestFocus();
            return;
        }
        if (pwd2.equals(pwd)) {
            editpwd.setError("Passwords must match");
            editpwd.requestFocus();
            editpwd2.requestFocus();
            return;
        }
        //------IF ALL REQUIREMENTS ARE FULFILLED => SIGN UP NEW USER ---------//
        //Send call to our RESTAPI and request creation of new user
        Call<ResponseBody> call = ApiClient
                .getSingleton()
                .getApi()
                .registerUser(fname, lname, email, pwd);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Fragment newFrag = new LoginFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            response.body().string();
                            Toast.makeText(getActivity(), "Account created! Login to join and create events", Toast.LENGTH_SHORT).show();
                            fragmentTransaction.replace(R.id.fragment_container, newFrag).commit();
                        }
                    } else if (response.code() == 400) {
                        String errorMessage = "Account with this email allready exists";
                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
