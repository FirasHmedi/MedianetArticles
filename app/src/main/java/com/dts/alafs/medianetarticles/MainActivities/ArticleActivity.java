package com.dts.alafs.medianetarticles.mainactivities;

import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dts.alafs.medianetarticles.adapters.CommentsAdapter;
import com.dts.alafs.medianetarticles.adapters.ImagePager;
import com.dts.alafs.medianetarticles.model.Article;
import com.dts.alafs.medianetarticles.model.Comment;
import com.dts.alafs.medianetarticles.storage.LocalDatabase;
import com.dts.alafs.medianetarticles.storage.SharedPrefManager;
import com.dts.alafs.medianetarticles.others.URLs;
import com.dts.alafs.medianetarticles.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class ArticleActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private Article article;
    private TextView titleView;
    private TextView contentView;
    private TextView auteurNomView;
    private TextView dateView;
    private Button buttonPause;
    private Button buttonStart;
    EditText editcomment;
    private CircleIndicator circleIndicator;
    private ViewPager viewPager;
    private YouTubePlayerFragment myYouTubePlayerFragment;
    private  FrameLayout frameLayout;
    private  RelativeLayout podcastlayout;


    private LocalDatabase database;
    private  MediaPlayer mediaPlayer;
    private String[] imgArray;



    private SeekBar seekBar;
    private ListView listView;
    private Handler threadHandler = new Handler();
    private String content;
    private CommentsAdapter adapter;
    private Comment comment;
    private ArrayList<Comment> arrayListComments;
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    LinearLayout layoutBottomSheet;
    private RapidFloatingActionContentLabelList rfaContent;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;
    private List<RFACLabelItem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        getSupportActionBar().setTitle("Article");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        layoutBottomSheet=findViewById(R.id.bottom_sheet);

        rfaLayout=findViewById(R.id.activity_main_rfal);
        rfaBtn=findViewById(R.id.activity_main_rfab);
       //  requestQueue = Volley.newRequestQueue(this);
         article = new Gson().fromJson(getIntent().getStringExtra("article"), Article.class);
         seekBar=  findViewById(R.id.seekbar);
         seekBar.setClickable(false);
         Log.d("articarticleActv",article.toString());
         imgArray= article.getImgArr();

        database=new LocalDatabase(getApplicationContext());

          titleView= findViewById(R.id.tvTitleArticle);
          contentView= findViewById(R.id.tvContentArticle);
          auteurNomView= findViewById(R.id.tvAuteurNomArticle);
          viewPager = findViewById(R.id.view_pager);
          circleIndicator = findViewById(R.id.circle);
          editcomment=findViewById(R.id.editcomment);
          buttonPause=findViewById(R.id.pausepodcast);
          buttonStart=findViewById(R.id.startpodcast);
          buttonPause.setEnabled(false);
          dateView = findViewById(R.id.tvDateArticle);
          dateView.setPaintFlags(dateView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

          frameLayout=findViewById(R.id.frameLayoutVid);
          titleView.setText(article.getTitle());
          contentView.setText(article.getContent());
          auteurNomView.setText("By "+ article.getAuthorNom());
          dateView.setText(article.getDate());
          podcastlayout=findViewById(R.id.podcastlayout);

            checkingVidImgPodEx();
            arrayListComments=new ArrayList<>();
            loadComments();

        listView=findViewById(R.id.listview);
         adapter = new CommentsAdapter(this,arrayListComments);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        rfaContent = new RapidFloatingActionContentLabelList(getApplicationContext());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);

        items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>().setResId(R.drawable.favorite_filled).setWrapper(0));
        items.add(new RFACLabelItem<Integer>().setResId(R.drawable.bkm_fillbig).setWrapper(1));
        rfaContent.setItems(items);
        rfabHelper = new RapidFloatingActionHelper(getApplicationContext(), rfaLayout, rfaBtn, rfaContent).build();

    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {

    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        if((int)item.getWrapper()==0)  //likes
            addDelLike();
        else //bookmarks
             imageBookmarkFunc();

        rfabHelper.toggleContent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if (item.getItemId()==android.R.id.home){
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(article.getVidId());
        }
    }


    public void doStartPodcast(View view)   {
        int duration=mediaPlayer.getDuration();
        int currentPosition = mediaPlayer.getCurrentPosition();
        if(currentPosition== 0)
           seekBar.setMax(duration);
           else if(currentPosition== duration)  {
            // Resets the MediaPlayer to its uninitialized state.
            mediaPlayer.reset();
        }
        mediaPlayer.start();
        // Create a thread to update position of SeekBar.
        UpdateSeekBarThread updateSeekBarThread= new UpdateSeekBarThread();
        threadHandler.postDelayed(updateSeekBarThread,50);

        buttonPause.setEnabled(true);
        buttonStart.setEnabled(false);

    }

    public void pausePodcast(View view){
        mediaPlayer.pause();
        buttonPause.setEnabled(false);
        buttonStart.setEnabled(true);
    }

    // When user click to "Rewind".
    public void doRewind(View view)  {
        int currentPosition = this.mediaPlayer.getCurrentPosition();

        // 5 seconds.
        int SUBTRACT_TIME = 5000;

        if(currentPosition - SUBTRACT_TIME > 0 )  {
            this.mediaPlayer.seekTo(currentPosition - SUBTRACT_TIME);
        }
    }

    // When user click to "Fast-Forward".
    public void doFastForward(View view)  {
        int currentPosition = this.mediaPlayer.getCurrentPosition();
        int duration = this.mediaPlayer.getDuration();
        // 5 seconds.
        int ADD_TIME = 5000;

        if(currentPosition + ADD_TIME < duration)  {
            this.mediaPlayer.seekTo(currentPosition + ADD_TIME);
        }
    }

    // Thread to Update position for SeekBar.
    class UpdateSeekBarThread implements Runnable {

        public void run()  {
            int currentPosition = mediaPlayer.getCurrentPosition();
             seekBar.setProgress(currentPosition);
            // Delay thread 50 milisecond.
            threadHandler.postDelayed(this, 50);
        }
    }


    public void showPics(View view){
        if(viewPager.getVisibility()==View.GONE) {
            viewPager.setVisibility(View.VISIBLE);
            circleIndicator.setVisibility(View.VISIBLE);
        }
        else {
            viewPager.setVisibility(View.GONE);
            circleIndicator.setVisibility(View.GONE);
        }

        }

    public void showVideoPodcast(View view){

        if(frameLayout.getVisibility()==View.GONE)
            frameLayout.setVisibility(View.VISIBLE);
        else
            frameLayout.setVisibility(View.GONE);

        if( podcastlayout.getVisibility()==View.GONE)
            podcastlayout.setVisibility(View.VISIBLE);
        else
            podcastlayout.setVisibility(View.GONE);


    }


    private void checkingVidImgPodEx(){

        if(!imgArray[0].equals("")) {
            ImagePager myPager = new ImagePager(this, imgArray);
            viewPager.setAdapter(myPager);
            circleIndicator.setViewPager(viewPager);

           // Toast.makeText(getApplicationContext(),"images availables :"+imgArray[0]+":",Toast.LENGTH_SHORT).show();
        } else{
            Button showPics=findViewById(R.id.showPics);
            ((ViewManager)viewPager.getParent()).removeView(viewPager);
            ((ViewManager)circleIndicator.getParent()).removeView(circleIndicator);
            ((ViewManager)showPics.getParent()).removeView(showPics);
           // Toast.makeText(getApplicationContext(),"images not availables",Toast.LENGTH_SHORT).show();
        }

        if(article.getVidId()!=null) {
            myYouTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                    .findFragmentById(R.id.youtubeplayerfragment);
            myYouTubePlayerFragment.initialize(URLs.YoutubeApiKey, this);
        }else{

            ((ViewManager)frameLayout.getParent()).removeView(frameLayout);

        }

        if(article.getPodUrl()!=null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                String URL=getResources().getString(R.string.URL_RETRIEVE_PODCAST,getString(R.string.ROOT_URL));
                mediaPlayer.setDataSource( URL+ article.getPodUrl()+".mp3");
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{

            ((ViewManager)podcastlayout.getParent()).removeView(podcastlayout);

        }

        if((article.getPodUrl()==null)&&(article.getVidId()==null)){
            Button showVideoPodcast=findViewById(R.id.showVideoPodcast);
            ((ViewManager)showVideoPodcast.getParent()).removeView(showVideoPodcast);
        }


    }

    private void imageBookmarkFunc(){
        //deleting bookmark
        if(database.checkBookmarkExInDb(article.getId()+"")){
            database.removeData(article.getId());
            Toast.makeText(getApplicationContext(),"Article Removed from Bookmark",Toast.LENGTH_SHORT).show();
        }
        else{  // adding bookmark
            database.insertData(article.getId(), article.getTitle(), article.getSummary(), article.getContent(), article.getAuthorNom());
            Toast.makeText(getApplicationContext(),"Article  Bookmarked",Toast.LENGTH_SHORT).show();
        }
    }

    private void loadComments(){

        String URL=getResources().getString(R.string.URL_RETRIEVE_COMMENTS,getString(R.string.ROOT_URL));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+ article.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.d("response comment ",response);
                            arrayListComments.clear();

                            JSONArray jsonArray=new JSONArray(response);
                            if(jsonArray.length()==0)
                                listView.setVisibility(View.GONE);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                //getting json object from the json array
                                JSONObject obj = jsonArray.getJSONObject(i);
                                comment =new Gson().fromJson(obj.toString(), Comment.class);
                                Log.d("response  Object id ", comment.getId()+"");
                                //getting the object from the json object and putting it inside list
                                arrayListComments.add(comment);
                            }

                            //Notifying the adapter that data has been added or changed
                            adapter.notifyDataSetChanged();



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
                });

        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void addComment(View view) {

        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){

        content = editcomment.getText().toString().trim();
        // send request with parm to save comment

        //if everything is fine
            String URL=getResources().getString(R.string.URL_INSERTTHINGS,getString(R.string.ROOT_URL));
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"comments",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response comments", response);

                            arrayListComments.clear();
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                //getting json object from the json array
                                JSONObject obj = jsonArray.getJSONObject(i);
                                comment = new Gson().fromJson(obj.toString(), Comment.class);
                                //getting the object from the json object and putting it inside list
                                arrayListComments.add(comment);
                            }

                            //Notifying the adapter that data has been added or changed
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Check Your Network", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idArticle", article.getId() + "");
                params.put("content", content);
                params.put("idUser", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
        listView.setVisibility(View.VISIBLE);

    }
    else
        Toast.makeText(getApplicationContext(),"Please Sign in",Toast.LENGTH_SHORT).show();

    }

    private void addDelLike(){

        if(SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            String URL=getResources().getString(R.string.URL_INSERTTHINGS,getString(R.string.ROOT_URL));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL+"addDelLike",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("response likes ", "c" + response + "c");
                            int likes = Integer.parseInt(response);

                            if(likes< article.getLikes()){
                                SharedPrefManager.getInstance(getApplicationContext()).removeLike(article.getId());
                                Toast.makeText(getApplicationContext(),"Article Disliked",Toast.LENGTH_SHORT).show();
                            }
                            else if(likes> article.getLikes()){
                                SharedPrefManager.getInstance(getApplicationContext()).addLike(article.getId());
                                Toast.makeText(getApplicationContext(),"Article Liked",Toast.LENGTH_SHORT).show();
                            }

                            article.setLikes(likes);
                            Log.d("likes : ", "" + article.getLikes());

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Check Your Network", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idUser", String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()));
                    params.put("idArticle", "" + article.getId());
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(),"Please Sign in",Toast.LENGTH_LONG).show();
        }


        }




}


