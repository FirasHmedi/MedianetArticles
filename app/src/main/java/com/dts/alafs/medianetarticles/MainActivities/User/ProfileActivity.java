package com.dts.alafs.medianetarticles.mainactivities.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dts.alafs.medianetarticles.mainactivities.HomeActivity;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName, email;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //if the user is not logged in
        //starting the login activity
         if (!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        settingUserInfo();

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               SharedPrefManager.getInstance(getApplicationContext()).logout();
               finish();
            }
        });

        findViewById(R.id.editProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                finish();
            }
        });

    }

    private void settingUserInfo(){
         profileName =findViewById(R.id.textViewUsername);
        profileName.setText("User name : "+SharedPrefManager.getInstance(getApplicationContext()).getUser().getUsername());
         email =findViewById(R.id.textViewEmail);
        email.setText("Email : "+SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
          imageView=findViewById(R.id.imageViewProfileImage);
          Bitmap bitmap=StringToBitMap(SharedPrefManager.getInstance(getApplicationContext()).getUser().getImage());
          imageView.setImageBitmap(bitmap);

    }


    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

   @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
   @Override
   public void onBackPressed()
   {
       startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
   }


}
