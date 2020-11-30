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
private ArrayList<ArrayList<String>> teams = new ArrayList<>();
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
        adapter.setUsers(users, numberOfTeams);

        teamsRecyclerView.setAdapter(adapter);
        teamsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
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
                    adapter.setUsers(users, numberOfTeams);
                }else{
                    Toast.makeText(getContext(), "failed to fetch users, try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    private void generateTeams(){
        int numberOfPlayers = users.size();
        int teamSize = Math.floorDiv(numberOfPlayers, numberOfTeams);
        int rest = numberOfPlayers % numberOfTeams;
        System.out.println(numberOfTeams);
        System.out.println(numberOfPlayers);
        System.out.println(teamSize);
        System.out.println(rest);

        for(int t=1; t<=numberOfTeams; t++){
            ArrayList<String> team = new ArrayList<>();
            System.out.println("TEAM " + t + ":");
            for(int s=0; s<teamSize; s++){
                numberOfPlayers -= numberOfPlayers;
                int random = (int)((Math.random()*numberOfPlayers));
                User user = users.get(random);
                System.out.println(user);
                String username = user.getFirstname() + " " + user.getLastname();
                team.add(username);
                users.remove(random);
                System.out.println(username);
            }
            if(rest != 0){
                int random = (int)((Math.random()*numberOfPlayers));
                User user = users.get(random);
                String username = user.getFirstname() + " " + user.getLastname();
                team.add(username);
                users.remove(random);
                System.out.println(username);
            }
            teams.add(team);
        }
    }

    public void initViews(View view){
        teamsRecyclerView = view.findViewById(R.id.teamsRecView);
        swipeRefreshLayout = view.findViewById(R.id.nav_hometeam);
    }

}
