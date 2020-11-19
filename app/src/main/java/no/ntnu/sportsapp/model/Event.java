package no.ntnu.sportsapp.model;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import no.ntnu.sportsapp.model.User;

public class Event {
    private long eventid;
    private String sport;
    private String description;
    private Date date;
    private String location;
    private Time time;
    private int maxPlayers;
    private String latLng;
    private User creator;
    private ArrayList<User> signedUsers = new ArrayList<>();
//    private boolean oldEvent;



    public Event(long eventid, String sport, String description, String date, String location, String time, int maxPlayers, String latLng, User creator) {
        this.eventid = eventid;
        this.sport = sport;
        this.description = description;
        this.date = date;
        this.location = location;
        this.time = time;
        this.maxPlayers = maxPlayers;
        this.latLng = latLng;
        this.creator = creator;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
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

