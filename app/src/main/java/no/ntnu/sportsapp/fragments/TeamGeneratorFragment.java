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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.model.User;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamGeneratorFragment extends Fragment {
private long eventid;
private ArrayList<User> users = new ArrayList<>();
private Button generateButton;
private EditText editNumberOfTeams;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate, container, false);

        eventid = getArguments().getLong("eventid");
        setUserList();

        editNumberOfTeams = view.findViewById(R.id.editNumberOfTeams);

        generateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                generateTeams();
            }
        });

        return view;
    }

    private void generateTeams(){
        Random randomGenerator = new Random();
        String input = editNumberOfTeams.getText().toString().trim();
        int numberOfTeams = Integer.parseInt(input);
        int numberOfPlayers = users.size();
        int teamSize;
        int leftOvers;
        if(numberOfPlayers % numberOfTeams == 0){
            teamSize = numberOfPlayers/numberOfTeams;
            int index = randomGenerator.nextInt(numberOfPlayers);
        } else  {
            teamSize = Math.floorDiv(numberOfPlayers, numberOfTeams);
            leftOvers = numberOfPlayers % numberOfTeams;
        }



    }

    private void setUserList() {
        final UserPrefs userPrefs = new UserPrefs(this.getContext());
        String token = "Bearer " + userPrefs.getToken();
        Call<List<User>> call = ApiClient
                .getSingleton()
                .getApi()
                .getAttenders(token, eventid);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    users = (ArrayList<User>) response.body();
                    System.out.println("TESTING CALL IN TEAMGENERATOR");
                }else{
                    Toast.makeText(getContext(), "failed to fetch users, try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

}
