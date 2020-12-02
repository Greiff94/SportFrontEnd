package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.net.CacheResponse;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {

    EditText editTextEmail;
    Button sendPwdBtn, returntoLoginBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgotpwd, container, false);

        initViews(view);

        sendPwdBtn.setOnClickListener(this);
        returntoLoginBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgotpwdbtn:
                sendNewPassword();
                break;

            case R.id.forgotpwdreturntologin:
                changeView();
                break;
        }
    }

    public void sendNewPassword() {

        String userid = editTextEmail.getText().toString().trim();

        Call<ResponseBody> call = ApiClient
                .getSingleton()
                .getApi()
                .resetPassword(userid);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Mail sent!\nPlease check you email for a new password!", Toast.LENGTH_SHORT).show();
                    changeView();
                } else if (response.code() == 304) {
                    Toast.makeText(getContext(), "Could not find email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Something went wrong, please try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Could not connect...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changeView() {
        Fragment loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, loginFragment).commit();
    }

    public void initViews(View view) {
        editTextEmail = view.findViewById(R.id.forgotpwdemail);
        sendPwdBtn = view.findViewById(R.id.forgotpwdbtn);
        returntoLoginBtn = view.findViewById(R.id.forgotpwdreturntologin);
    }
}
