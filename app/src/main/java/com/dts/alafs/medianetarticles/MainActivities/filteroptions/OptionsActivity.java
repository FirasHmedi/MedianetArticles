package com.dts.alafs.medianetarticles.mainactivities.filteroptions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dts.alafs.medianetarticles.model.LoadedThings;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OptionsActivity extends AppCompatActivity {

    private String categ="";
    LoadedThings loadedThings;
    String[] categoriesNames;
    private ViewGroup linearLayout;
    private LinearLayout.LayoutParams buttonLayoutParams;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        getSupportActionBar().setTitle("Options");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linearLayout=findViewById(R.id.linearOptionCateg);

        final RadioGroup radioGroup = findViewById(R.id.radio);
        loadCategories();

        findViewById(R.id.submitOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                Log.d("categ in options:",categ);

                Intent intent = new Intent(OptionsActivity.this, FilterArticlesActivity.class);
                if(selectedId==R.id.date){
                    intent.putExtra("sortType","date");
                }
                else if(selectedId==R.id.likes)
                    intent.putExtra("sortType","likes");

                intent.putExtra("categ",categ);

                startActivity(intent);
            }
        });
    }
    
    public View creatingCategItem(String catem){

        final LayoutInflater factory = getLayoutInflater();
        final View categMainView = factory.inflate(R.layout.toggle_categ_button, null);
        final String cate=catem;

        ToggleButton toggleCateg=categMainView.findViewById(R.id.toggleCateg);
        buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(20, 30, 20, 30);
        buttonLayoutParams.gravity= Gravity.CENTER_HORIZONTAL;
        toggleCateg.setLayoutParams(buttonLayoutParams);
        toggleCateg.setText(cate);
        toggleCateg.setGravity(Gravity.CENTER);
        toggleCateg.setTextOff(cate);
        toggleCateg.setTextOn(cate);

        toggleCateg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    SharedPrefManager.getInstance(getApplicationContext()).addCategToUser(cate+"Opt");
                    categ=categ+cate+",";
                    buttonView.setBackgroundResource(R.color.softGreen);
                } else {
                    // The toggle is disabled
                    SharedPrefManager.getInstance(getApplicationContext()).removeCategUser(cate+"Opt");
                    categ = categ.replace(cate+",","");
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

                                for (int i = 0; i < loadedThings.categoriesNames.length; i++)
                                    linearLayout.addView(creatingCategItem(loadedThings.categoriesNames[i]));
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

    @Override
    public boolean onSupportNavigateUp() {
        SharedPrefManager.getInstance(getApplicationContext()).clearOptionsFiltCateg();
        onBackPressed();
        return true;
    }
}
