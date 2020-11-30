package no.ntnu.sportsapp.model;

public class TeamMember {
    private String userid;
    private String firstName;
    private String lastName;

    public TeamMember(String userid, String firstName, String lastName){
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
