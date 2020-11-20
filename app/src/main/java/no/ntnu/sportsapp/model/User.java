package no.ntnu.sportsapp.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private long id;
    @SerializedName("userid")
    private String userid;
    @SerializedName("firstName")
    private String firstname;
    @SerializedName("lastName")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("jwt")
    private String jwt;
    @SerializedName("password")
    private String password;


    public User() {

    }

    public User(long id, String userid, String firstname, String lastname, String email, String jwt, String password) {
        this.id = id;
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.jwt = jwt;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return userid;
    }

    public void setUid(String uid) {
        this.userid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
