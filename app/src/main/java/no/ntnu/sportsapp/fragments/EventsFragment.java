package no.ntnu.sportsapp.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import no.ntnu.sportsapp.adapter.EventListAdapter;
import no.ntnu.sportsapp.model.Event;

public class EventsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView eventsRecyclerView;
    private ArrayList<Event> events = new ArrayList<>();
    private EventListAdapter adapter;

}
