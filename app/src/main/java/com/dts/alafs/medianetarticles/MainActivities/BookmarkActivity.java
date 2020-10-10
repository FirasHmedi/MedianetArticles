package com.dts.alafs.medianetarticles.mainactivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import com.dts.alafs.medianetarticles.adapters.BookmarkAdapter;
import com.dts.alafs.medianetarticles.model.Article;
import com.dts.alafs.medianetarticles.storage.LocalDatabase;
import com.dts.alafs.medianetarticles.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity  {

    private LocalDatabase database;
    //Creating a List of articles
    private List<Article> articles;
    private Article article;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressBar middleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_articles);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bookmark");

        middleProgressBar=findViewById(R.id.middleProgressBar);
        middleProgressBar.setVisibility(View.GONE);

        database=new LocalDatabase(this);

        //Initializing Views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //Initializing our articles Arraylist
        articles = new ArrayList<>();
        //initializing our adapter
        adapter = new BookmarkAdapter(articles, this,new BookmarkAdapter.OnItemClickListener() {
            @Override public void onItemClick(Article article) {

                Intent intent = new Intent(BookmarkActivity.this, ArticleActivity.class);
                String s = (new Gson().toJson(article));
                intent.putExtra("article",s);

                startActivity(intent);
            }
        });

        //Adding adapter to recyclerview
        recyclerView.setAdapter(adapter);
        loadArticles();

    }


    private void loadArticles() {
            database.getWritableDatabase();

            try {
                JSONArray arrayList=database.fetchData();
                loadIntoListView(arrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

    }

    private void loadIntoListView(@NonNull JSONArray jsonArray) throws JSONException {
        String[] imgArray=new String[1];
        imgArray[0]="";
        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);
            article =new Gson().fromJson(obj.toString(), Article.class);
            article.setImgArr(imgArray);
            Log.d("articleObjt in bookmark","article in bookmark" + article.toString());

            //getting the object from the json object and putting it inside list
            articles.add(article);

        }
        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
