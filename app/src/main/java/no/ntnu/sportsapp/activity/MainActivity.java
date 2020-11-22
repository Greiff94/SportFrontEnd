package no.ntnu.sportsapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import no.ntnu.sportsapp.R;
import no.ntnu.sportsapp.fragments.AddEventFragment;
import no.ntnu.sportsapp.fragments.EventsFragment;
import no.ntnu.sportsapp.fragments.LoginFragment;
import no.ntnu.sportsapp.fragments.MyEventFragment;
import no.ntnu.sportsapp.fragments.MyProfileFragment;
import no.ntnu.sportsapp.fragments.RegisterFragment;
import no.ntnu.sportsapp.fragments.SignedUpFragment;
import no.ntnu.sportsapp.model.User;
import no.ntnu.sportsapp.preference.UserPrefs;
import no.ntnu.sportsapp.rest.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu navMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---------Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navMenu = navigationView.getMenu();

        updateOnStartUp();

        //---------Toolbar
        setSupportActionBar(toolbar);

        //---------Navigation drawer menu

        //navigationView.bringToFront();  <-- if navbar is not clickable
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
        }
    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EventsFragment()).commit();
                break;

            case R.id.nav_my_events:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyEventFragment()).commit();
                break;

            case R.id.nav_new:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddEventFragment()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MyProfileFragment()).commit();
                break;

            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LoginFragment()).commit();
                break;

            case R.id.nav_register:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RegisterFragment()).commit();
                break;
            case R.id.nav_logout:
                UserPrefs userPrefs = new UserPrefs(this);
                userPrefs.setToken("");
                finish();
                startActivity(getIntent());
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateOnStartUp() {
        UserPrefs userPrefs = new UserPrefs(getApplicationContext());
        String token = "Bearer " + userPrefs.getToken();

        Call<User> call = ApiClient
                .getSingleton()
                .getApi()
                .currentUser(token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    navMenu.findItem(R.id.nav_events).setVisible(true);
                    navMenu.findItem(R.id.nav_my_events).setVisible(true);
                    navMenu.findItem(R.id.nav_new).setVisible(true);
                    navMenu.findItem(R.id.nav_profile).setVisible(true);
                    navMenu.findItem(R.id.nav_logout).setVisible(true);

                    navMenu.findItem(R.id.nav_login).setVisible(false);
                    navMenu.findItem(R.id.nav_register).setVisible(false);
                } else {
                    navMenu.findItem(R.id.nav_events).setVisible(true);
                    navMenu.findItem(R.id.nav_login).setVisible(true);
                    navMenu.findItem(R.id.nav_register).setVisible(true);

                    navMenu.findItem(R.id.nav_my_events).setVisible(false);
                    navMenu.findItem(R.id.nav_new).setVisible(false);
                    navMenu.findItem(R.id.nav_profile).setVisible(false);
                    navMenu.findItem(R.id.nav_logout).setVisible(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Could not connect!", Toast.LENGTH_SHORT).show();
            }
        });
    }

/*    public void updateOnStartUp() {

        UserPrefs userPrefs = new UserPrefs(getApplicationContext());

        if (userPrefs.getToken().isEmpty()) {
            navMenu.findItem(R.id.nav_profile).setVisible(false);
            navMenu.findItem(R.id.nav_logout).setVisible(false);
        } else {
            navMenu.findItem(R.id.nav_login).setVisible(false);
            navMenu.findItem(R.id.nav_register).setVisible(false);
        }

    }*/
}