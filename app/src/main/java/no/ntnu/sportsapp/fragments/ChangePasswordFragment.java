package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.zip.Inflater;

import no.ntnu.sportsapp.R;

public class ChangePasswordFragment extends Fragment {

    private Button changepwdBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepwd, container, false);

        changepwdBtn = view.findViewById(R.id.changepwdButton);
        changepwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "CHANGE PASSWORD CLICKED", Toast.LENGTH_SHORT).show();
                System.out.println("Change password button pressed");
            }
        });

        return view;
    }
}
