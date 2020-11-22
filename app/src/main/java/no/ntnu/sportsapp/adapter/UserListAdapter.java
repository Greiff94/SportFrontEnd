
package no.ntnu.sportsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.model.Event;
import no.ntnu.sportsapp.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private ArrayList<User> users = new ArrayList<>();
    private Context context;

    public UserListAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {
        holder.txtFname.setText(users.get(position).getFirstname());
        holder.txtLname.setText(users.get(position).getLastname());
    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtFname, txtLname;
        private CardView parent;
        private ImageView image;
        public ViewHolder(@NonNull View userView) {
            super(userView);
            txtFname = userView.findViewById(R.id.userfname);
            txtLname = userView.findViewById(R.id.userlname);
            parent = userView.findViewById(R.id.uparent);
            image = userView.findViewById(R.id.userimg);
        }
    }
}

