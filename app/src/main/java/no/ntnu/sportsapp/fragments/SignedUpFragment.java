package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.adapter.UserListAdapter;
import no.ntnu.sportsapp.model.User;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import no.ntnu.sportsapp.rest.AppInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignedUpFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView usersRecyclerView;
    private ArrayList<User> users = new ArrayList<>();
    private UserListAdapter adapter;
    private long eventid = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        getActivity().setTitle("Signed up users");

        initViews(view);
        setUserList();

        //eventid = getArguments().getLong("eventid");


        adapter = new UserListAdapter(getContext());
        adapter.setUsers(users);

        usersRecyclerView.setAdapter(adapter);
        usersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setUserList();
            }
        });
        return view;
    }

    //TODO Test if we don't need to do a call here, since we store the list of users in event?
    public void setUserList() {
        final UserPrefs userPrefs = new UserPrefs(this.getContext());
        String token = "Bearer " + userPrefs.getToken();
        System.out.println(eventid);
        Call<List<User>> call = ApiClient
                .getSingleton()
                .getApi()
                .getAttenders(token, eventid);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    users = (ArrayList<User>) response.body();
                    adapter.setUsers(users);
                }else{
                    Toast.makeText(getContext(), "failed to fetch users, try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    public void initViews(View view){
        usersRecyclerView = view.findViewById(R.id.usersRecView);
        swipeRefreshLayout = view.findViewById(R.id.nav_homeu);
    }
}
