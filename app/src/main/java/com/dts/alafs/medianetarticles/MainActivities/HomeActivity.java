package com.dts.alafs.medianetarticles.mainactivities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.dts.alafs.medianetarticles.adapters.MainCategPager;
import com.dts.alafs.medianetarticles.mainactivities.filteroptions.CategoriesActivity;
import com.dts.alafs.medianetarticles.mainactivities.filteroptions.FilterArticlesActivity;
import com.dts.alafs.medianetarticles.mainactivities.filteroptions.OptionsActivity;
import com.dts.alafs.medianetarticles.mainactivities.user.LoginActivity;
import com.dts.alafs.medianetarticles.mainactivities.user.ProfileActivity;
import com.dts.alafs.medianetarticles.model.Article;
import com.dts.alafs.medianetarticles.model.LoadedThings;
import com.dts.alafs.medianetarticles.others.AlarmBroadcastReceiver;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

import static android.app.AlarmManager.INTERVAL_DAY;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private JSONArray array;
    //Creating a List of articleObjects
    private Article article;
    private ViewGroup linearLayout;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private View HeaderView;
    private ProgressBar middleProgressBar;

    private String[] categoriesNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        startAlarmBroadcastReceiver(getApplicationContext(),INTERVAL_DAY);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        middleProgressBar = findViewById(R.id.middleProgressBar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        linearLayout = findViewById(R.id.linearCategMain);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            MenuItem logout = menu.findItem(R.id.nav_logout);
            if (!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn())
                logout.setTitle("Sign In");
            else
                logout.setTitle("Log Out");
        }

        HeaderView = navigationView.getHeaderView(0);
        settingUserInfoInMenu(HeaderView);
        //loading categories and then articles
        loadCategories();

        findViewById(R.id.filterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  startActivity(new Intent(HomeActivity.this, OptionsActivity.class));
            }
        });
    }

        private void loadArticles(int requestCount) {

  /*      if (loadedThings.smallArrayArticles != null) {
            Log.d("LOADING articHome","didnt need to LOADING articles");
            try {
            for (int i = 0; i < LoadedThings.categoriesNames.length; i++)
                linearLayout.addView(creatingCategItem(LoadedThings.smallArrayArticles, LoadedThings.categoriesNames[i]));
                middleProgressBar.setVisibility(View.GONE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {*/
            Log.d("LOADING articHome"," LOADING articles");
            String url = getResources().getString(R.string.URL_RETRIEVE_ARTICLES, getString(R.string.ROOT_URL));
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url + requestCount + "&limit=20",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                //converting the string to json array object
                               JSONObject obj=new JSONObject(response);
                               JSONArray jsonArray = obj.getJSONArray("article");
                                Log.d("array", jsonArray.toString());

                            for (int i = 0; i < categoriesNames.length; i++)
                                 linearLayout.addView(creatingCategItem(jsonArray, categoriesNames[i]));

                              middleProgressBar.setVisibility(View.GONE);

                           } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Check Your Network", Toast.LENGTH_SHORT).show();
                            middleProgressBar.setVisibility(View.GONE);
                        }
                    });

            //adding our stringrequest to queue
            Volley.newRequestQueue(this).add(stringRequest);

      //  }
    }

        private ArrayList<Article> loadIntoListView (@NonNull JSONArray jsonArray, String categ) throws
        JSONException {
            ArrayList<Article> articleObjectsy = new ArrayList<>();
            articleObjectsy.clear();
            //looping through all the elements in json array
            for (int i = 0; i < jsonArray.length(); i++) {

                //getting json object from the json array
                JSONObject obj = jsonArray.getJSONObject(i);
                article = new Gson().fromJson(obj.toString(), Article.class);
                //getting the object from the json object and putting it inside list
                if ((categ.equals(article.getCategorieTitle())) && (articleObjectsy.size() < 3))
                    articleObjectsy.add(article);
            }
            return articleObjectsy;
        }

        private View creatingCategItem (JSONArray articleArray, String categ) throws JSONException {

            final LayoutInflater factory = getLayoutInflater();
            final View categMainView = factory.inflate(R.layout.item_categ, null);
            final String cate = categ;

            ArrayList<Article> articleObjectsses;
            ViewPager viewPager = categMainView.findViewById(R.id.CategViewPager);
            CircleIndicator circleIndicator = categMainView.findViewById(R.id.circlecateg);
            Button more = categMainView.findViewById(R.id.moreButton);
            TextView categTitle = categMainView.findViewById(R.id.categTitletItem);
            categTitle.setText(cate);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterArticlesCateg(cate);
                }
            });
            articleObjectsses = loadIntoListView(articleArray, cate);
            viewPager.setAdapter(new MainCategPager(viewPager.getContext(), articleObjectsses));
            circleIndicator.setViewPager(viewPager);
            Log.d("articles  filtered", articleObjectsses.toString());

            return categMainView;
        }

        private void filterArticlesCateg (String categ){
            Intent intent = new Intent(HomeActivity.this, FilterArticlesActivity.class);
            intent.putExtra("categ", categ);
            intent.putExtra("sortType", "date");
            startActivity(intent);
        }

        @Override
        public void onBackPressed () {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.navigation_drawer, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                startAlarmBroadcastReceiver(getApplicationContext(),5*1000);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onNavigationItemSelected (MenuItem item){

            // Handle navigation view item clicks here.
            int id = item.getItemId();
            if (id == R.id.nav_favcateg) {

                Intent intent = new Intent(HomeActivity.this, FilterArticlesActivity.class);
                intent.putExtra("categ", "");
                intent.putExtra("sortType", "date");
                startActivity(intent);

            }
            if (id == R.id.nav_bookm) {
                startActivity(new Intent(HomeActivity.this, BookmarkActivity.class));
            } else if (id == R.id.nav_sett) {
                startActivity(new Intent(HomeActivity.this, CategoriesActivity.class));

            } else if (id == R.id.nav_logout) {
                if (item.getTitle() == "Sign In")
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                else
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
            }

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        private void settingUserInfoInMenu (@NonNull View HeaderView){

            Log.d("User in sideMenu", SharedPrefManager.getInstance(getApplicationContext()).getUser().toString());

            TextView profileName = HeaderView.findViewById(R.id.profilName);
            profileName.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getUsername());
            TextView email = HeaderView.findViewById(R.id.profilEmail);
            email.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
            ImageView imageView = HeaderView.findViewById(R.id.profilImage);
            Bitmap bitmap = StringToBitMap(SharedPrefManager.getInstance(getApplicationContext()).getUser().getImage());
            imageView.setImageBitmap(bitmap);
        }

        public void navProfilFun (View view){
            startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
        }

        @Nullable
        private Bitmap StringToBitMap(String encodedString){
            try {
                Log.d("BitmapString", encodedString);
                byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }

        private void loadCategories(){

            if (LoadedThings.categoriesNames != null) {
                Log.d("LOADING categHome","didnt need to LOADING categ");
                categoriesNames = new String[LoadedThings.categoriesNames.length];
                for (int i = 0; i < LoadedThings.categoriesNames.length; i++)
                    categoriesNames[i] = LoadedThings.categoriesNames[i];

                loadArticles(0);
            }
            else {
                Log.d("LOADING categHome","LOADING");

                String URL = getResources().getString(R.string.URL_RETRIEVE_CATEGORIES, getString(R.string.ROOT_URL));
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //converting the string to json array object
                                    JSONArray jsonArray = new JSONArray(response);
                                    categoriesNames = new String[jsonArray.length()];
                                    Log.d("array", jsonArray.toString());
                                    //  loadedThings.setCategoriesNames(new String[jsonArray.length()]);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject obj = jsonArray.getJSONObject(i);
                                        categoriesNames[i] = (String) obj.get("title");
                                    }
                                    LoadedThings.categoriesNames = categoriesNames;
                                    // loadedThings.setCategoriesNames(categoriesNames);
                                    loadArticles(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Check Your Network", Toast.LENGTH_SHORT).show();
                            }
                        });
                //adding our stringrequest to queue
                Volley.newRequestQueue(this).add(stringRequest);
            }
        }


        public static void startAlarmBroadcastReceiver(Context context, long delay) {
        Intent _intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, _intent, 0);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        // Remove any previous pending intent.
        alarmManager.cancel(pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pendingIntent);
    }

    }



