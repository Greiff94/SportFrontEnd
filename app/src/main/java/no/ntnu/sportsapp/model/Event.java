package no.ntnu.sportsapp.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class Event {
    private int id;
    private String sport;
    private String description;
    private Date date;
    //@todo instead of string location, google maps location
    private String location;
    private String creator;
    //private ArrayList<User> signedUsers = new ArrayList<>();
    private boolean oldEvent;

    public Event(int id, String sport, String description, Date date, String location, String creator, ArrayList signedUsers, boolean oldEvent) {
        this.id = id;
        this.sport = sport;
        this.description = description;
        this.date = date;
        this.location = location;
        this.creator = creator;
        //this.signedUsers = signedUsers;
        this.oldEvent = oldEvent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
//
//    public ArrayList getSignedUsers() {
//        return signedUsers;
//    }
//
//    public void setSignedUsers(ArrayList signedUsers) {
//        this.signedUsers = signedUsers;
//    }

    public boolean isOldEvent() {
        return oldEvent;
    }

    public void setOldEvent(boolean oldEvent) {
        this.oldEvent = oldEvent;
    }

    //@todo fix this when backend is finished
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + sport + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", oldevent=" + oldEvent +
                ", userid=" + creator +
                '}';
    }
}
