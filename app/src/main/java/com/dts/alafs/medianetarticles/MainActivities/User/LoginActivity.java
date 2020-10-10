package com.dts.alafs.medianetarticles.mainactivities.user;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dts.alafs.medianetarticles.mainactivities.filteroptions.CategoriesActivity;
import com.dts.alafs.medianetarticles.mainactivities.HomeActivity;
import com.dts.alafs.medianetarticles.model.Profile;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity  {


    EditText editTextUsername, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                finish();
            }
        });

        //if user presses he goes to CategoriesActivity
        findViewById(R.id.buttonLIgnore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                if ((!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Politics"))
                        &&(!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Technology"))&&
                        (!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Culture"))
                        &&(!SharedPrefManager.getInstance(getApplicationContext()).getCategPref().contains("Science"))) {
                    startActivity(new Intent(LoginActivity.this, CategoriesActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });

    }

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username or your email");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        String URL=getResources().getString(R.string.URL_REGISTER_LOGIN,getString(R.string.ROOT_URL));
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                Profile user = new Profile(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email"),
                                        userJson.getString("image")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(LoginActivity.this, CategoriesActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
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
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    public void googleSignIn(View view){

        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

      /*  //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username or your email");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }*/

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("Success", "signInWithEmail:success "+user.getUid());

                         //   userLogin();

                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Failure", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });



    }







}

