package no.ntnu.sportsapp.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.sportsapp.model.User;

public class Event {
    private long id;
    private String sport;
    private String description;
    private String date;
    private String location;
    private String time;
    private int maxPlayers;
    private User creator;
    private ArrayList<User> signedUsers = new ArrayList<>();
//    private boolean oldEvent;


    public Event(long id, String sport, String description, String date, String location, String time, int maxPlayers, User creator) {
        this.id = id;
        this.sport = sport;
        this.description = description;
        this.date = date;
        this.location = location;
        this.time = time;
        this.maxPlayers = maxPlayers;
        this.creator = creator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ArrayList<User> getSignedUsers() {
        return signedUsers;
    }

    public void setSignedUsers(ArrayList<User> signedUsers) {
        this.signedUsers = signedUsers;
    }
}

//
//        //@todo fix this when backend is finished
//        @Override
//        public String toString () {
//            return "Event{" +
//                    "id=" + id +
//                    ", eventName='" + sport + '\'' +
//                    ", description='" + description + '\'' +
//                    ", date=" + date +
//                    ", oldevent=" + oldEvent +
//                    ", userid=" + creator +
//                    '}';
//        }

