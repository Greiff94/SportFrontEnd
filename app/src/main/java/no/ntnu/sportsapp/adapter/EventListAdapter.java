
package no.ntnu.sportsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.activity.EventActivity;
import no.ntnu.sportsapp.model.Event;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private ArrayList<Event> events = new ArrayList<>();
    private Context context;

    public EventListAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtEvent.setText(events.get(position).getSport());
        holder.txtDate.setText(events.get(position).getDate());
        holder.txtTime.setText(events.get(position).getTime());
        holder.txtLocation.setText(events.get(position).getLocation());
        holder.image.setImageResource(events.get(position).getImage());


        holder.parent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context, events.get(position).getSport() + " selected", Toast.LENGTH_SHORT).show();
                Event event = events.get(position);



                Intent intent = new Intent(context, EventActivity.class);
                intent.putExtra("eventid", event.getId());
                intent.putExtra("sport", event.getSport());
                intent.putExtra("description", event.getDescription());
                intent.putExtra("date", event.getDate());
                intent.putExtra("time", event.getTime());
                intent.putExtra("location", event.getLocation());
                intent.putExtra("eventCreator", event.getEventCreator().getUid());
                intent.putExtra("maxPlayers", Integer.toString(event.getMaxPlayers()));
                intent.putExtra("latLng", event.getLatLng());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(ArrayList<Event> events){
        this.events = events;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtEvent, txtDate, txtTime, txtLocation;
        private CardView parent;
        private ImageView image;
        public ViewHolder(@NonNull View eventView) {
            super(eventView);
            txtEvent = eventView.findViewById(R.id.event);
            txtDate = eventView.findViewById(R.id.date);
            txtTime = eventView.findViewById(R.id.time);
            txtLocation = eventView.findViewById(R.id.location);
            parent = eventView.findViewById(R.id.parent);
            image = eventView.findViewById(R.id.image);
        }
    }
}

