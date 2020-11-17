package no.ntnu.sportsapp.rest;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import no.ntnu.sportsapp.model.Event;
import no.ntnu.sportsapp.model.User;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AppInterface {

    //LOGIN, REGISTRATION AND CURRENTUSER\\

    @FormUrlEncoded
    @POST("auth/login")
    public Call<ResponseBody> login(@Field("uid") String username,
                                    @Field("pwd") String password);

    @FormUrlEncoded
    @POST("auth/create")
    public Call<ResponseBody> registerUser(@Field("fname") String firstName,
                                           @Field("lname") String lastName,
                                           @Field("email") String email,
                                           @Field("pwd") String pwd);
    @GET("auth/currentuser")
    public Call<ResponseBody> currentUser();

    //------------EVENT SERVICES------------\\
    @GET("event/allevents")
    public Call<List<Event>> getAllEvents();

    @DELETE("event/remove")
    public Call<ResponseBody> removeEvent(@Query("eventid") Long eventid);

    @FormUrlEncoded
    @POST("event/add")
    public Call<ResponseBody> addEvent(@Header("Authorization") String token,
                                       @Field("sport") String sport,
                                       @Field("description") String description,
                                       @Field("date") String date,
                                       @Field("location") String location,
                                       @Field("time") String time,
                                       @Field("maxPlayers") int maxPlayers,
                                       @Field("latLng") String latLng);

    @GET
    public Call<List<User>> getAttenders(@Query("eventid") Long eventid);


    //------------EVENT-INTERACTING------------\\

    @PUT("event/joinevent")
    public Call<ResponseBody> joinEvent(@Query("eventid") Long eventid);

    @PUT("event/leave")
    public Call<ResponseBody> leaveEvent(@Query("eventid") Long eventid);

    @GET("event/myevents")
    public Call<List<Event>> myEvents(@Query("userid") Long userid);
}

