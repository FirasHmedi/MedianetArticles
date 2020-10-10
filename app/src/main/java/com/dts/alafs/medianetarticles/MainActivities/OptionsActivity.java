package com.dts.alafs.medianetarticles.MainActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.dts.alafs.medianetarticles.Others.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class OptionsActivity extends AppCompatActivity {

    ToggleButton togglePol;
    ToggleButton toggleTech;
    ToggleButton toggleSci;
    ToggleButton toggleCult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle("Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      togglePol = findViewById(R.id.toggleButtonPol);
      toggleTech = findViewById(R.id.toggleButtonTech);
      toggleSci = findViewById(R.id.toggleButtonSci);
      toggleCult = findViewById(R.id.toggleButtonCult);

        togglePol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // The toggle is enabled
                SharedPrefManager.getInstance(getApplicationContext()).addCategToUser("PoliticsOpt");
                buttonView.setBackgroundColor(Color.GREEN);
            } else {
                // The toggle is disabled
                SharedPrefManager.getInstance(getApplicationContext()).removeCategUser("PoliticsOpt");
                buttonView.setBackgroundColor(Color.WHITE);
            }
        }
    });

        toggleTech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    SharedPrefManager.getInstance(getApplicationContext()).addCategToUser("TechnologyOpt");
                    buttonView.setBackgroundColor(Color.GREEN);
                } else {
                    // The toggle is disabled
                    SharedPrefManager.getInstance(getApplicationContext()).removeCategUser("TechnologyOpt");
                    buttonView.setBackgroundColor(Color.WHITE);
                }
            }
        });

        toggleSci.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    SharedPrefManager.getInstance(getApplicationContext()).addCategToUser("ScienceOpt");
                    buttonView.setBackgroundColor(Color.GREEN);
                } else {
                    // The toggle is disabled
                    SharedPrefManager.getInstance(getApplicationContext()).removeCategUser("ScienceOpt");
                    buttonView.setBackgroundColor(Color.WHITE);
                }
            }
        });

        toggleCult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    SharedPrefManager.getInstance(getApplicationContext()).addCategToUser("CultureOpt");
                    buttonView.setBackgroundColor(Color.GREEN);
                } else {
                    // The toggle is disabled
                    SharedPrefManager.getInstance(getApplicationContext()).removeCategUser("CultureOpt");
                    buttonView.setBackgroundColor(Color.WHITE);
                }
            }
        });

        findViewById(R.id.submitOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsActivity.this, FilterArticlesActivity.class);
                intent.putExtra("categ","Filtered Articles");
                startActivity(intent);
            }
        });

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        Log.d("date  ",date);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
