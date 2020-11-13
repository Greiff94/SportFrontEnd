package no.ntnu.sportsapp.rest;

import java.util.List;

import no.ntnu.sportsapp.model.Event;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AppInterface {

    //LOGIN AND REGISTRATION

    @FormUrlEncoded
    @POST("auth/login")
    public Call<ResponseBody> login(@Field("uid") String username,
                                    @Field("pwd") String password);

    @FormUrlEncoded
    @POST("auth/create")
    public Call<ResponseBody> registerUser(@Field("uid") String username,
                                           @Field("pwd") String password,
                                           @Field("email") String email);
    //EVENT SERVICES
    @GET("event/allevents")
    public Call<List<Event>> getAllEvents();
}
