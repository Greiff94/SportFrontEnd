package no.ntnu.sportsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.adapter.EventListAdapter;
import no.ntnu.sportsapp.model.Event;
import no.ntnu.sportsapp.rest.ApiClient;
import no.ntnu.sportsapp.rest.AppInterface;

public class EventsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView eventsRecyclerView;
    private ArrayList<Event> events = new ArrayList<>();
    private EventListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        getActivity().setTitle("Home page");

        initViews(view);
        setEventList();

        adapter = new EventListAdapter(getContext());
        adapter.setEvents(events);

        eventsRecyclerView.setAdapter(adapter);
        eventsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setEventList();
            }
        });
        return view;
    }

    public void setEventList() {
    //api call to get events

    }

    public void initViews(View view){
        eventsRecyclerView = view.findViewById(R.id.eventsRecView);
        swipeRefreshLayout = view.findViewById(R.id.nav_home);
    }
}
