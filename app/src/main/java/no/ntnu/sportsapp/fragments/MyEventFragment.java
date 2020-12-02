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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.adapter.EventListAdapter;
import no.ntnu.sportsapp.model.Event;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEventFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView myEventsRecyclerView;
    private ArrayList<Event> myEvents = new ArrayList<>();
    private EventListAdapter adapter;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myevents, container, false);

        initViews(view);
        setMyEventList();

        adapter = new EventListAdapter(getContext());

        myEventsRecyclerView.setAdapter(adapter);
        myEventsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setMyEventList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }
    public void setMyEventList(){
        final UserPrefs userPrefs = new UserPrefs(this.getContext());
        uid = userPrefs.getUid();
        String token = "Bearer " + userPrefs.getToken();
        System.out.println(token);
        System.out.println(uid);

        Call<List<Event>> call = ApiClient
                .getSingleton()
                .getApi()
                .myEvents(token, uid);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if(response.isSuccessful()){
                    myEvents = (ArrayList<Event>) response.body();
                    sortList(myEvents);
                }else{
                    Toast.makeText(getContext(), "Failed to see your events, please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getContext(), "Could not connect...", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void sortList(ArrayList<Event> event){
        Collections.sort(event, new Comparator<Event>() {
            @Override
            public int compare(Event event, Event t1) {
                return event.getSport().compareTo(t1.getSport());
            }
        });
        adapter.setEvents(event);
    }

    public void initViews(View view) {
        myEventsRecyclerView = view.findViewById(R.id.myEventsRecView);
        swipeRefreshLayout = view.findViewById(R.id.nav_homem);
    }
}
