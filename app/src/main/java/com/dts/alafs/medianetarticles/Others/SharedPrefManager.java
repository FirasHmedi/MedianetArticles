package com.dts.alafs.medianetarticles.Others;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.dts.alafs.medianetarticles.MainActivities.User.LoginActivity;
import com.dts.alafs.medianetarticles.Objects.ProfileObject;


//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "medianetpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_IMAGE = "keyimage";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    //method to check if categ exists in pref
    public boolean IsExsOptionCategPref(String categ){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(categ, false) != false;
    }

    //method to check if article is liked in pref

    public boolean isArticleLiked(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Integer.toString(id), false) != false;
    }

    //method to add liked pref of user
    public void addLike(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Integer.toString(id),true);
        editor.apply();
    }

    //method to remove liked pref of user
    public void removeLike(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Integer.toString(id),false);
        editor.apply();

    }

    //method to check if article bookmarked
    public boolean isArticleBookmarked(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Integer.toString(id), false) != false;
    }

    //method to remove bookm pref of user
    public void addBookmark(int id){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Integer.toString(id),true);
        editor.apply();
    }

    //method to remove bookm pref of user
    public void removeBookmark(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Integer.toString(id),false);
        editor.apply();

    }


    //method to check if categ exists in pref
    public boolean IsExsCategPref(String categ){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if((!sharedPreferences.getBoolean("Technology", false))&&(!sharedPreferences.getBoolean("Politics", false))
                &&(!sharedPreferences.getBoolean("Science", false))&&(!sharedPreferences.getBoolean("Culture", false)))
            return false;

        if(!categ.equals(""))
            return sharedPreferences.getBoolean(categ, false) != false;
        else
            return  true;
    }

    //method to add categ pref of user
    public void addCategToUser(String categ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(categ,true);
        editor.apply();

    }

    //method to remove categ pref of user
    public void removeCategUser(String categ) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(categ,false);
        editor.apply();

    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(ProfileObject user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_IMAGE, user.getImage());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public ProfileObject getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ProfileObject(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_IMAGE, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
