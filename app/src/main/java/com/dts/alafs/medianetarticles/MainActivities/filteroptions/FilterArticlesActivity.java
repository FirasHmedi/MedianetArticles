package com.dts.alafs.medianetarticles.mainactivities.filteroptions;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dts.alafs.medianetarticles.mainactivities.ArticleActivity;
import com.dts.alafs.medianetarticles.mainactivities.HomeActivity;
import com.dts.alafs.medianetarticles.model.Article;
import com.dts.alafs.medianetarticles.adapters.CardAdapter;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterArticlesActivity extends AppCompatActivity implements RecyclerView.OnScrollChangeListener  {

    //Creating a List of articles
    private List<Article> articles;
    private Article article;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressBar progressBar;
    private ProgressBar middleProgressBar;
    //The request counter to send ?page=0, ?page=1 , ?page=+8 pages are over  requests
    private int requestCount = 0;
    private String categ,sortType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_articles);

        getSupportActionBar().setTitle("Filter Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categ=getIntent().getStringExtra("categ");
        sortType=getIntent().getStringExtra("sortType");

        if(categ.equals("")){
            categ = SharedPrefManager.getInstance(getApplicationContext()).getCategPref();
            Log.d("favorite categ",categ);
            getSupportActionBar().setTitle("Favorite Categories");

        }

        middleProgressBar=findViewById(R.id.middleProgressBar);
        progressBar = findViewById(R.id.progressBar);

        //Initializing Views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our articles list
        articles = new ArrayList<>();
        loadArticles(requestCount);

        //Adding an scroll change listener to recyclerview
        recyclerView.setOnScrollChangeListener(this);

        //initializing our adapter
        adapter = new CardAdapter(articles, this,new CardAdapter.OnItemClickListener() {
            @Override public void onItemClick(Article article) {

                Toast.makeText(getApplicationContext(), article.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FilterArticlesActivity.this, ArticleActivity.class);
                String s = (new Gson().toJson(article));
                intent.putExtra("article",s);
                startActivity(intent);
            }
        });
        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }


    private void loadArticles(final int requestCount) {
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */



        String URL=getResources().getString(R.string.URL_RETRIEVE_FILTERED_ARTICLES,getString(R.string.ROOT_URL));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL+requestCount+"&limit=15",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Filter response",response);
                            if(response.contains("Pages are over")) {
                                progressBar.setVisibility(View.GONE);
                                Log.d("ProgressBar","is gone");
                            }
                            //converting the string to json array object
                            JSONObject obj=new JSONObject(response);
                            JSONArray array  = obj.getJSONArray("article");

                            loadIntoListView(array);

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
                    }
                }) {
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
                    params.put("optArray", categ);
                    params.put("sortType", sortType);
                return params;
            }
        };
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadIntoListView(@NonNull JSONArray jsonArray) throws JSONException {

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            article =new Gson().fromJson(obj.toString(), Article.class);
            //getting the object from the json object and putting it inside list
            articles.add(article);

        }
        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
        if(requestCount==0)
            middleProgressBar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(@NonNull RecyclerView recyclerView) {
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

            progressBar.setVisibility(View.VISIBLE);
            requestCount++;
            Log.d("requestCount",requestCount+"");
            //Calling the method loadArticles again
            loadArticles(requestCount);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        SharedPrefManager.getInstance(getApplicationContext()).clearOptionsFiltCateg();
        startActivity(new Intent(FilterArticlesActivity.this, HomeActivity.class));

        return true;
    }

    private JSONArray filterJsonArray(@NonNull JSONArray array, String categ) throws JSONException {

        JSONArray newArray=new JSONArray();
        for (int i = 0; i < array.length(); ++i) {

            String cat = array.getJSONObject(i).getString("categTitle");
            if(!categ.equals("Filtered Articles")){
                if (cat.equals(categ)) {
                    newArray.put(array.getJSONObject(i));
                    Log.d("item Added", "item Added : "+cat);
                }}
            else
            if(SharedPrefManager.getInstance(getApplicationContext()).IsExsOptionCategPref(cat+"Opt")){
                newArray.put(array.getJSONObject(i));
                Log.d("items Added", "items Added from option : "+cat);
            }

        }
        return newArray;
    }
}

