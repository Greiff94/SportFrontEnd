package no.ntnu.sportsapp.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import no.ntnu.sportsapp.R;
//import no.ntnu.sportsapp.model.TeamMember;
import no.ntnu.sportsapp.model.User;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.ViewHolder>{
   ArrayList<User> users = new ArrayList<>();
   //ArrayList<TeamMember> teams = new ArrayList<>();
   private Context context;
   private int numberOfTeams;

   public TeamListAdapter(Context context) {
       this.context = context;
   }

    @NonNull
    @Override
    public TeamListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list, parent, false);
       ViewHolder holder = new ViewHolder(view);
       return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamListAdapter.ViewHolder holder, int position) {
        holder.fName.setText(users.get(position).getFirstname());
        holder.lName.setText(users.get(position).getLastname());
    }

    public int getItemCount() {
       return users.size();
    }
    public void setUsers(ArrayList<User> users) {
     //  this.numberOfTeams = numberOfTeams;
       this.users = users;
      // this.teams = generateTeams();
       notifyDataSetChanged();
    }
/*    private ArrayList<TeamMember> generateTeams() {
        int numberOfPlayers = users.size();
        int teamSize = Math.floorDiv(numberOfPlayers, numberOfTeams);
        int rest = numberOfPlayers % numberOfTeams;
        ArrayList<TeamMember> team = new ArrayList<>();
        for (int t = 1; t <= numberOfTeams; t++) {
            System.out.println("NUMBER OF TEAMS " + numberOfTeams);
            System.out.println("NUMBER OF PLAYERS " + numberOfPlayers);
            System.out.println("TEAMSIZE " + teamSize);
            System.out.println("REST " + rest);
            TeamMember teamNumber = new TeamMember("teamleader@team.com","Team ", String.valueOf(t));
            team.add(teamNumber);
            System.out.println("TEAM " + t + ":");
            for (int s = 0; s < teamSize; s++) {
                if (users!= null && users.size() != 0) {
                    int random = (int) ((Math.random() * users.size()));
                    User user = users.get(random);
                    TeamMember member = new TeamMember(user.getUid(), user.getFirstname(), user.getLastname());
                    team.add(member);
                    users.remove(random);
                    System.out.println("Player: " + member.getFirstName());
                }
            }
            if (rest != 0) {
                if (users!= null && users.size() != 0) {
                    int random = (int) ((Math.random() * users.size()));
                    User user = users.get(random);
                    TeamMember member = new TeamMember(user.getUid(), user.getFirstname(), user.getLastname());
                    team.add(member);
                    users.remove(random);
                    System.out.println("Player: " + member.getFirstName());
                    rest -= rest;
                }
            }
        }
        return team;
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder{
       private TextView fName, lName;
       private CardView parent;
       public ViewHolder(@NonNull View teamView) {
           super(teamView);
           fName = teamView.findViewById(R.id.teamfname);
           lName = teamView.findViewById(R.id.teamlname);
       }
    }
}

