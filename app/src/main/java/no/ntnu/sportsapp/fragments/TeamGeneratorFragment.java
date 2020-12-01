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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.adapter.TeamListAdapter;
//import no.ntnu.sportsapp.model.TeamMember;
import no.ntnu.sportsapp.model.User;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamGeneratorFragment extends Fragment {
private long eventid;
private SwipeRefreshLayout swipeRefreshLayout;
private RecyclerView teamsRecyclerView;
private TeamListAdapter adapter;
private ArrayList<User> users = new ArrayList<>();
private ArrayList<User> teams = new ArrayList<>();
private TextView teamsText;
private int numberOfTeams;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_teams, container, false);

        initViews(view);
        eventid = getArguments().getLong("eventid");
        numberOfTeams = getArguments().getInt("number");

        setUserList();

        adapter = new TeamListAdapter(getContext());
        adapter.setUsers(users);

        teamsRecyclerView.setAdapter(adapter);
        teamsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUserList();
            }
        });
        System.out.println("TEAMGENERATOR");
        return view;
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
                    generateTeams();

                }else{
                    Toast.makeText(getContext(), "failed to fetch users, try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private void generateTeams() {
        int numberOfPlayers = users.size();
        int teamSize = Math.floorDiv(numberOfPlayers, numberOfTeams);
        int rest = numberOfPlayers % numberOfTeams;
        ArrayList<User> team = new ArrayList<>();
        for (int t = 1; t <= numberOfTeams; t++) {
            System.out.println("NUMBER OF TEAMS " + numberOfTeams);
            System.out.println("NUMBER OF PLAYERS " + numberOfPlayers);
            System.out.println("TEAMSIZE " + teamSize);
            System.out.println("REST " + rest);
            User teamNumber = new User("teamleader@team.com","Team ", String.valueOf(t));
            team.add(teamNumber);
            System.out.println("TEAM " + t + ":");
            for (int s = 0; s < teamSize; s++) {
                if (users!= null && users.size() != 0) {
                    int random = (int) ((Math.random() * users.size()));
                    User user = users.get(random);
                    team.add(user);
                    users.remove(random);
                    System.out.println("Player: " + user.getFirstname());
                }
            }
            if (rest != 0) {
                if (users!= null && users.size() != 0) {
                    int random = (int) ((Math.random() * users.size()));
                    User user = users.get(random);
                    team.add(user);
                    users.remove(random);
                    System.out.println("Player: " + user.getFirstname());
                    rest -= rest;
                }
            }
        }
        this.teams = team;
        adapter.setUsers(team);
    }

    public void initViews(View view){
        teamsRecyclerView = view.findViewById(R.id.teamsRecView);
        swipeRefreshLayout = view.findViewById(R.id.nav_hometeam);
    }

}
