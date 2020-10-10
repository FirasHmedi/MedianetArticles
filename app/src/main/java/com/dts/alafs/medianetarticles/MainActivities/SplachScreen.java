package com.dts.alafs.medianetarticles.mainactivities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.dts.alafs.medianetarticles.mainactivities.filteroptions.CategoriesActivity;
import com.dts.alafs.medianetarticles.mainactivities.user.LoginActivity;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;



public class SplachScreen extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach_screen);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.colorBackCardView));


        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {

                if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){

                if ((!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Politics"))
                &&(!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Technology"))&&
                        (!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Culture"))
                        &&(!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Science"))) {
                    startActivity(new Intent(SplachScreen.this, CategoriesActivity.class));
                    finish();
                }
                else
                { startActivity(new Intent(SplachScreen.this, HomeActivity.class));
                  finish();
                }

                }
                else{
                    startActivity(new Intent(SplachScreen.this, LoginActivity.class));
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
