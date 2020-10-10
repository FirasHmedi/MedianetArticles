package com.dts.alafs.medianetarticles.mainactivities.filteroptions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dts.alafs.medianetarticles.mainactivities.HomeActivity;
import com.dts.alafs.medianetarticles.model.LoadedThings;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoriesActivity extends AppCompatActivity {


     LoadedThings loadedThings;
    private ViewGroup linearLayout;
    private LinearLayout.LayoutParams buttonLayoutParams;
    private String[] categoriesNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().setTitle("Categories");
        linearLayout=findViewById(R.id.linearCategAct);


        findViewById(R.id.buttonMainPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoriesActivity.this, HomeActivity.class));
            }
        });
        loadCategories();
    }


    public void settingBackCateg(ToggleButton toggleButton,String categ){

        if(SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains(categ))
            toggleButton.setBackgroundResource(R.color.softGreen);

    }

    private void addCategPref(String s){
        if(!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains(s))
        SharedPrefManager.getInstance(getApplicationContext()).
              setCategPref(SharedPrefManager.getInstance(getApplicationContext()).getCategPref()+s);

    }
    private void delCategPref(String s){
        SharedPrefManager.getInstance(getApplicationContext()).
        setCategPref(SharedPrefManager.getInstance(getApplicationContext()).getCategPref().replace(s,""));
    }

    public View creatingCategItem(String categ){

        final LayoutInflater factory = getLayoutInflater();
        final View categMainView = factory.inflate(R.layout.toggle_categ_button, null);
        final String cate=categ;

        ToggleButton toggleCateg=categMainView.findViewById(R.id.toggleCateg);
        buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(20, 30, 20, 30);
        buttonLayoutParams.gravity= Gravity.CENTER_HORIZONTAL;
        toggleCateg.setLayoutParams(buttonLayoutParams);
        toggleCateg.setText(cate);
        toggleCateg.setGravity(Gravity.CENTER);
        toggleCateg.setTextOff(cate);
        toggleCateg.setTextOn(cate);
        settingBackCateg(toggleCateg,cate);

         toggleCateg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    addCategPref(cate+",");

                    buttonView.setBackgroundResource(R.color.softGreen);
                } else {
                    // The toggle is disabled
                    delCategPref(cate+",");
                    buttonView.setBackgroundResource(R.color.colorOldcardView);
                }
            }
        });
        return categMainView;
    }


        public void loadCategories(){

        if(loadedThings.categoriesNames!=null)
            for (int i = 0; i < loadedThings.categoriesNames.length; i++)
                linearLayout.addView(creatingCategItem(loadedThings.categoriesNames[i]));
        else{

            String URL=getResources().getString(R.string.URL_RETRIEVE_CATEGORIES,getString(R.string.ROOT_URL));
           StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray jsonArray = new JSONArray(response);
                            categoriesNames=new String[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                categoriesNames[i]=(String)obj.get("title");
                                linearLayout.addView(creatingCategItem(categoriesNames[i]));
                            }
                            LoadedThings.categoriesNames=categoriesNames;

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Check Your Network", Toast.LENGTH_SHORT).show();

                    }
                });
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

        }
    }

}
