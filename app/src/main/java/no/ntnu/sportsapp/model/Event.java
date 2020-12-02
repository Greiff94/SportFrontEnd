package no.ntnu.sportsapp.model;

import java.util.ArrayList;

import no.ntnu.sportsapp.R;

public class Event {
    int image;
    private long eventid;
    private String sport;
    private String description;
    private String date;
    private String location;
    private String time;
    private int maxPlayers;
    private String latLng;
    private User eventCreator;
    private ArrayList<User> signedUsers = new ArrayList<>();


    public Event(int image, long eventid, String sport, String description, String date, String location, String time, int maxPlayers, String latLng, User creator) {
        this.image = image;
        this.eventid = eventid;
        this.sport = sport;
        this.description = description;
        this.date = date;
        this.location = location;
        this.time = time;
        this.maxPlayers = maxPlayers;
        this.latLng = latLng;
        this.eventCreator = eventCreator;
    }
    public int getImage(){
        String football = "Football";
        String volleyball = "Volleyball";
        String basketball = "Basketball";
        if(this.sport.equals(football)){
            return R.drawable.football;
        } else if(this.sport.equals(volleyball)){
            return R.drawable.volleyball;
        } else if(this.sport.equals(basketball)){
            return R.drawable.basketball;
        }else {
            return R.drawable.logo400;
        }
    }
    public long getId() {
        return eventid;
    }

    public void setId(long eventid) {
        this.eventid = eventid;
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

    public User getEventCreator() {
        return eventCreator;
    }

    public void setEventCreator(User eventCreator) {
        this.eventCreator = eventCreator;
    }

    public ArrayList<User> getSignedUsers() {
        return signedUsers;
    }

    public void setSignedUsers(ArrayList<User> signedUsers) {
        this.signedUsers = signedUsers;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }
}


