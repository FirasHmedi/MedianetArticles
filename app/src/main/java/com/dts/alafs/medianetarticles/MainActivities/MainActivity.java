package com.dts.alafs.medianetarticles.MainActivities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dts.alafs.medianetarticles.Adapters.MainCategPager;
import com.dts.alafs.medianetarticles.MainActivities.User.LoginActivity;
import com.dts.alafs.medianetarticles.MainActivities.User.ProfileActivity;
import com.dts.alafs.medianetarticles.Objects.ArticleObject;
import com.dts.alafs.medianetarticles.Others.SharedPrefManager;
import com.dts.alafs.medianetarticles.Others.URLs;
import com.dts.alafs.medianetarticles.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    JSONArray array;
    //Creating a List of articleObjects
    private ArticleObject articleObject;
    private ViewGroup linearLayout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private View HeaderView;
    private ProgressBar middleProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_navigation_drawer);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        middleProgressBar=findViewById(R.id.middleProgressBar);


        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (navigationView != null) {
            Menu menu = navigationView.getMenu();

            if (!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                MenuItem logout=menu.findItem(R.id.nav_logout);
                logout.setTitle("Sign In");
            }
            else{
                MenuItem logout=menu.findItem(R.id.nav_logout);
                logout.setTitle("Log Out");
            }
        }

        HeaderView=navigationView.getHeaderView(0);
        settingUserInfoInMenu(HeaderView);

        linearLayout=findViewById(R.id.linearCategMain);


        loadArticles(0);


        findViewById(R.id.filterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(MainActivity.this, OptionsActivity.class));
            }
        });

    }


    private void loadArticles(int requestCount) {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_RETRIEVE_ARTICLES+requestCount+"&limit=20",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                              array = new JSONArray(response);

                            linearLayout.addView(creatingCategItem(array,"Technology"));
                            linearLayout.addView(creatingCategItem(array,"Science"));
                            linearLayout.addView(creatingCategItem(array,"Culture"));
                            linearLayout.addView(creatingCategItem(array,"Politics"));

                            middleProgressBar.setVisibility(View.GONE);

                    }catch (JSONException e) {
                            e.printStackTrace();
                        }
                }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Check Your Network", Toast.LENGTH_SHORT).show();
                        middleProgressBar.setVisibility(View.GONE);
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    private ArrayList<ArticleObject> loadIntoListView(JSONArray jsonArray,String categ) throws JSONException {
        ArrayList<ArticleObject> articleObjectsy=new ArrayList<>();
        articleObjectsy.clear();
        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            articleObject=new Gson().fromJson(obj.toString(), ArticleObject.class);
            //getting the object from the json object and putting it inside list
            if((categ.equals(articleObject.getCategorieTitle()))&&(articleObjectsy.size()<3))
                articleObjectsy.add(articleObject);
        }
        return articleObjectsy;
    }


    public View creatingCategItem(JSONArray articleArray,String categ) throws JSONException {

          final LayoutInflater factory = getLayoutInflater();
          final View categMainView = factory.inflate(R.layout.categitem, null);
           final  String cate=categ;
          ArrayList<ArticleObject> articleObjectss;
          ViewPager viewPager =  categMainView.findViewById(R.id.CategViewPager);
          CircleIndicator circleIndicator = categMainView.findViewById(R.id.circlecateg);
          Button more=categMainView.findViewById(R.id.moreButton);
          TextView categTitle =  categMainView.findViewById(R.id.categTitletItem);
            categTitle.setText(categ);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterArticlesCateg(cate);
                }
            });
        articleObjectss=loadIntoListView(articleArray,categ);
        viewPager.setAdapter(new MainCategPager(viewPager.getContext(),articleObjectss));
        circleIndicator.setViewPager(viewPager);
         Log.d("articles  filtered",articleObjectss.toString());


        return categMainView;
    }


    public void filterArticlesCateg(String categ){
        Intent intent = new Intent(MainActivity.this, FilterArticlesActivity.class);
        intent.putExtra("categ",categ);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_favcateg) {
            startActivity(new Intent(MainActivity.this, FavoriteCategoriesActivity.class));
        }
        if (id == R.id.nav_bookm) {
            startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
        } else if (id == R.id.nav_sett) {
            startActivity(new Intent(MainActivity.this,CategoriesActivity.class));
        } else if (id == R.id.nav_logout) {
            if(item.getTitle()=="Sign In")
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            else
                SharedPrefManager.getInstance(getApplicationContext()).logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void settingUserInfoInMenu(View HeaderView){
        TextView profileName =HeaderView.findViewById(R.id.profilName);
        profileName.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getUsername());
        TextView email =HeaderView.findViewById(R.id.profilEmail);
        email.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
        ImageView imageView=HeaderView.findViewById(R.id.profilImage);
        Picasso.with(getApplicationContext()).load(URLs.URL_RETRIEVE_PROFILE_IMAGES+
                SharedPrefManager.getInstance(getApplicationContext()).getUser().getImage()+".jpg").fit().centerCrop().into(imageView);

    }

    public void navProfilFun(View view){
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }




}
