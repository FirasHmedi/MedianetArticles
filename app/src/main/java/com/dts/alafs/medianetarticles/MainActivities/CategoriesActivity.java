package com.dts.alafs.medianetarticles.MainActivities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dts.alafs.medianetarticles.Others.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;

public class CategoriesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.TechButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackPref(view,"Technology");
            }
        });

        findViewById(R.id.PolButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackPref(view,"Politics");
            }
        });

        findViewById(R.id.CulButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackPref(view,"Culture");
            }
        });

        findViewById(R.id.SciButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackPref(view,"Science");
            }
        });

        findViewById(R.id.buttonMainPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoriesActivity.this, FavoriteCategoriesActivity.class));
            }
        });


    }


    public void setBackPref(View view,String categ){
        if ( SharedPrefManager.getInstance(getApplicationContext()).IsExsCategPref(categ)) {
            SharedPrefManager.getInstance(getApplicationContext()).removeCategUser(categ);
            view.setBackgroundResource(R.color.colorBackCardView);
        }
        else{
            SharedPrefManager.getInstance(getApplicationContext()).addCategToUser(categ);
            view.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
