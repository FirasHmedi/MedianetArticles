package com.dts.alafs.medianetarticles.MainActivities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dts.alafs.medianetarticles.MainActivities.User.LoginActivity;
import com.dts.alafs.medianetarticles.MainActivities.User.ProfileActivity;
import com.dts.alafs.medianetarticles.Objects.ArticleObject;
import com.dts.alafs.medianetarticles.Adapters.CardAdapter;
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


public class FavoriteCategoriesActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener {

    //Creating a List of articleObjects
    private List<ArticleObject> articleObjects;
    private ArticleObject articleObject;
    //Creating Views
        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private RecyclerView.Adapter adapter;
        private  Toolbar toolbar;
        private DrawerLayout drawer;
        private ActionBarDrawerToggle toggle;
        private NavigationView navigationView;
        private View HeaderView;
        private ProgressBar progressBar;
        private ProgressBar middleProgressBar;
        //The request counter to send ?page=1, ?page=2  requests
        private int requestCount = 0;
        private Menu menu;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recycle_articles);

            getSupportActionBar().setTitle("Favorite Categories");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            middleProgressBar=findViewById(R.id.middleProgressBar);
            progressBar = findViewById(R.id.progressBar);



            //Initializing Views
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            //Initializing our articleObjects list
            articleObjects = new ArrayList<>();

            loadArticles(requestCount);

            //Adding an scroll change listener to recyclerview
            recyclerView.setOnScrollChangeListener(this);

            //initializing our adapter
            adapter = new CardAdapter(articleObjects, this,new CardAdapter.OnItemClickListener() {
                @Override public void onItemClick(ArticleObject articleObject) {

                    Toast.makeText(getApplicationContext(),articleObject.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FavoriteCategoriesActivity.this, ArticleActivity.class);
                    String s = (new Gson().toJson(articleObject));
                    intent.putExtra("articleObject",s);
                    startActivity(intent);
                }
            });

            //Adding adapter to recyclerview
            recyclerView.setAdapter(adapter);

        }


    private void loadArticles(int requestCount) {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        if((requestCount!=0)&&(requestCount<2))
            progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,URLs.URL_RETRIEVE_ARTICLES+requestCount+"&limit=8",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            loadIntoListView(filterJsonArrayWithPrefCateg(array));

                          } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
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

    private void loadIntoListView(JSONArray jsonArray) throws JSONException {

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            articleObject=new Gson().fromJson(obj.toString(),ArticleObject.class);
            //getting the object from the json object and putting it inside list
            articleObjects.add(articleObject);

        }
        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
        if(requestCount==0)
        middleProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }



    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }



    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        //Ifscrolled at last then
        if (isLastItemDisplaying(recyclerView)) {
            requestCount++;
            //Calling the method loadArticles again
            loadArticles(requestCount);
        }
    }

    public JSONArray filterJsonArrayWithPrefCateg(JSONArray array) throws JSONException {

        JSONArray newArray=new JSONArray();

        for (int i = 0; i < array.length(); ++i) {
            String cat = array.getJSONObject(i).getString("categTitle");
            if (SharedPrefManager.getInstance(getApplicationContext()).IsExsCategPref(cat)) {
                newArray.put(array.getJSONObject(i));
                Log.d("item Added", "item Added : "+cat);
            }
        }
        if(newArray.length()>0)
            return newArray;
        else
            return array;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}

