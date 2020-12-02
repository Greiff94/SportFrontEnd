package no.ntnu.sportsapp.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //URL FOR SERVER
    private static final String BASE_URL = "http://158.38.101.251:8080/Sport-1.0/api/";

    private static ApiClient singleton;

    // Turns HTTP API into a Java Interface
    Retrofit retrofit;

    public ApiClient() {
        // Logs request and response information.
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Used to send HTTP requests and read responses.
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    // Checks if an instance has been created
    public static synchronized ApiClient getSingleton() {
        if (singleton == null) {
            singleton = new ApiClient();
        }
        return singleton;
    }

    public AppInterface getApi() {
        return retrofit.create(AppInterface.class);
    }
}
