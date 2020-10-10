package com.dts.alafs.medianetarticles.others;

public interface URLs {

   String YoutubeApiKey = "AIzaSyAE4OoCq6Al_53M3edSWKujiZz94DtLoEQ";

}

    /*
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
   /* togglePol = findViewById(R.id.PolButton);
        toggleTech = findViewById(R.id.TechButton);
        toggleSci = findViewById(R.id.SciButton);
        toggleCult = findViewById(R.id.CulButton);*/

//  settingBackCateg();

      /*  togglePol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked){

                    // The toggle is enabled
                    addCategPref("Politics,");
                    buttonView.setBackgroundResource(R.color.softGreen);
                } else {
                    // The toggle is disabled
                    delCategPref("Politics,");
                    buttonView.setBackgroundResource(R.color.colorOldcardView);
                }

            }
        });
        toggleTech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    addCategPref("Technology,");

                    buttonView.setBackgroundResource(R.color.softGreen);
                } else {
                    // The toggle is disabled
                    delCategPref("Technology,");
                    buttonView.setBackgroundResource(R.color.colorOldcardView);
                }
            }
        });
        toggleSci.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    addCategPref("Science,");
                    buttonView.setBackgroundResource(R.color.softGreen);
                } else {
                    // The toggle is disabled
                    delCategPref("Science,");
                    buttonView.setBackgroundResource(R.color.colorOldcardView);
                }
            }
        });

        toggleCult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    addCategPref("Culture,");
                    buttonView.setBackgroundResource(R.color.softGreen);
                } else {
                    // The toggle is disabled
                    delCategPref("Culture,");
                    buttonView.setBackgroundResource(R.color.colorOldcardView);
                }
            }
        });*/


   /*
    String ROOT_URL ="http://192.168.11.82/MedianetArticlesApi/";
   String URL_REGISTER = ROOT_URL + "RegLog.php?apicall=signup";
   String URL_LOGIN= ROOT_URL + "RegLog.php?apicall=login";
   String URL_RETRIEVE_ARTICLES= ROOT_URL + "retrieveArticles.php?page=";
   String URL_RETRIEVE_IMAGES= ROOT_URL + "img/articleImg/";
   String URL_RETRIEVE_PODCAST=ROOT_URL + "podcast/";

   String URL_SEND_COMMENT = ROOT_URL + "insertThings.php?apicall=comments";
   String URL_LIKE_MANAGER = ROOT_URL + "insertThings.php?apicall=addDelLike";
   String URL_PROFILE_EDIT = ROOT_URL + "insertThings.php?apicall=profileEdit";

    String URL_RETRIEVE_CATEGORIES= ROOT_URL + "retrieveCategories.php";
    String URL_RETRIEVE_COMMENTS =ROOT_URL + "retrieveComments.php?articleId=";
    String URL_RETRIEVE_FILTERED_ARTICLES =ROOT_URL + "retrieveFilteredArticles.php?page=";*/

/*
    private void getJSON(final String urlWebService) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         *
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {



                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
*/
        /*    MyAdapter adapter=new MyAdapter(this, titles, contents);
            listView.setAdapter(adapter);*/
//  getJSON("http://192.168.10.91/MedianetArticlesApi/retrieveArticles.php");

          /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub
                    Article value=arrayAdapter.getItem(position);
                    //Toast.makeText(getApplicationContext(),value.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FavoriteCategoriesActivity.this, ArticleActivity.class);
                    intent.putExtra("title",value.getTitle());
                    intent.putExtra("content",value.getContent());
                    intent.putExtra("auteurNom",value.getAuthorNom());
                    startActivity(intent);
                }
            });*/

                    /*  articleObject.setTitle(obj.getString("title"));
            articleObject.setContent(obj.getString("content"));
            articleObject.setAuthorNom(obj.getString("auteurNomComplet"));
            articleObject.setImgArr(obj.getJSONArray("ImgArr"));
            articleObject.setVidUrl(obj.getString("VidUrl"));
            articleObject.setPodUrl(obj.getString("PodUrl"));*/

                                    /*    intent.putExtra("title",articleObject.getTitle());
                    intent.putExtra("content",articleObject.getContent());
                    intent.putExtra("auteurNom",articleObject.getAuthorNom());
                    intent.putExtra("ImgArr",articleObject.getImgArr());
                    intent.putExtra("VidUrl",articleObject.getVidUrl());
                    intent.putExtra("PodUrl",articleObject.getPodUrl());*/




/*
    private void loadImageArray(){

        for( int i=0;i<imgArray.length;i++) {
            imageName=i;
            ImageRequest imageRequest = new ImageRequest(URLs.URL_RETRIEVE_IMAGES+ imgArray[i]+".jpg",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {

                                imgBitmapArray[imageName]=bitmap;
                                successResponse=true;
                                Log.d("  loading images","success "+imgBitmapArray);
                        }
                    },0,0, ImageView.ScaleType.CENTER_CROP,null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("problem loading images","problem"+imgArray);
                            Toast.makeText(getApplicationContext(),"Problem no image loaded "+imgArray, Toast.LENGTH_LONG);
                        }
                    });

            //adding our stringrequest to queue
            requestQueue.add(imageRequest);


        }
        if(successResponse) {
            ImagePager myPager = new ImagePager(this, imgBitmapArray);
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(myPager);
            circleIndicator = findViewById(R.id.circle);
            circleIndicator.setViewPager(viewPager);
        }

    }*/

//categories =new LoadedThings();
        /*String URL=getResources().getString(R.string.URL_RETRIEVE_CATEGORIES,getString(R.string.ROOT_URL));
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
                            categories.setCategoriesNames(categoriesNames);

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

           private void checkingBookmarkLikeEx(){

        if(SharedPrefManager.getInstance(getApplicationContext()).isArticleLiked(article.getId())){
            items.add(new RFACLabelItem<Integer>().setResId(R.drawable.favorite_filled).setWrapper(0));
        }
        else{
            items.add(new RFACLabelItem<Integer>().setResId(R.drawable.favorite_empty).setWrapper(0));
        }

        if(database.checkBookmarkExInDb(article.getId()+"")) {

            items.add(new RFACLabelItem<Integer>().setResId(R.drawable.bkm_fillbig).setWrapper(1));
            Log.d("article   bookmarked","article   bookmarked "+ article.getId());
        }
        else{
            items.add(new RFACLabelItem<Integer>().setResId(R.drawable.bkm_emptybig).setWrapper(1));
            Log.d("article not bookmarked","article not bookmarked");
        }

    }

   3 key elements of motivation : process that account's for individual 3 key elements toward attaining a goal
    intensity direction persistence


   Old Theories 4 :
   {
   Not valid but they form the basis and they are still used by practicing managers

    Maslow low hierarchy : psychological safety social esteem self-actualization
        (cant move to another level unless they satisfied the previous one ) 5 needs

    Mcgregor theory x (negative) and theory y (positive)
         no empirical evidence to support this theory,It's only managers assumptions

    Herzbergs two-factory theory satisfaction dissatisfaction
       satisfaction(intrinsic) : growth responsib achievm / dissatisfaction(extrinsic) : policies ,salaries , work conditions
      (assumed relation between productivity and satisfaction , didn't research , participants had self-serving bias , errors of observation)

    McClellend  theory of needs
         needFor power :   ( need to make other behave in different way )
         needFor affiliation :   ( desire for friendly and desirable relationships )
         needFor achievement :  ( drive to excel , to strive to succeed )
      Problem : hard to measure
      }

   New : Contemporary theories of motivation : //(fama zeda expactancy theory ama no definition)

   self determination theory :
   {
   people prefer to have control over their actions ( not obligation ) it will motivate them more for freely chosen activities
   goal-setting more effective to improve motivation , extrinsic rewards decrease intrinsic rewards
   verbale rewards increase intrinsic rewards , tangible rewards decrease it
   }


   goal-setting  theory : ( Management by objectives (MBO) ) mta3 locke
   {
   specific and difficult goals with self-generated feedback lead to higher performance
   diff goals  force people to be more 2eff increase persistence , focus , energize person to work harder
   relation between goals and performa : goals commitment , task characterist (simple well learned ) , culture

   MBO is the systematic way to utilize goal-setting . goals must be verified tangible measurable
   4 ingredients to mbo programs : participative decision making , explicit time period , performance feedback , goal specificity
   }


   self-efficacy theory : mta3 Bandura , individual thinks he is capable of performing a task
   {
   higher efficacy is related to high confidence persistence better response to negative feedback
   self-efficacy complements goal-setting theory
   (given hard goal->increase confidence -> self efficacy -> higher performance)
   }


   reinforcement theory : (similar to goal-setting theory but focusing on behavioral approach rather than cognitive one )
   {
    reinforcement influence,control behaviour
    not a motivation theory but means of analysis of behavior
   }


   adams equity theory : ( employees compare their ratios of outcome-input of relevant others )
   {
    ratios == -> no tension -> situation fair
    rations != -> tension -> underrewarded : anger , overrewarded : guilt
    tension motivate people to act to bring their situation to equity
   }



   THE GROUP :
   {

   2 or more indiv interdependent who come together to achieve set of objectives

   formal groups :
   defined by organization with designated   work assignments
   command group (report directly to manager) task group (work to do tasks)

   informal group :
   alliance that appear naturally in response for need of social contact , deeply affect performance behavior
   interest group : work together to achieve goals with which each is concerned
   friendship group : have one or more characteristics in common

   reasons that people join groups : status,uncertainty reduction,similarity,distinctiveness
   Group properties : performance,size,cohesiveness,roles,status,norms

   5 STAGES OF GROUP DEVELOPMENT :
   1 - forming  : members feel uncertain
   2 - storming  : lots of conflict
   3 - norming stage : members have developed close relationships and cohesiveness
   4 - performing stage : group is finally fully functional
   5 - adjourning stage : in temporary groups , concerned with wrapping up activities rather than performance

   }

   Conflict and Negotiation :
   {
   conflict : process that begins when one party  perceives that another party has negatively affected or goind to affect something that the
   first party cares about
   if no one noticed there is no conflict / conflict begins with interaction,incompatibility,opposition

   traditional view of conflict : bad,to be avoided,viewed negatively and associated with terms : irrationality,destruction,violence
   interactionist view of conflict : supports goal , necessary to innovate and change ..

   functional conflicts support goals
   dysfunctional conflicts are bad and hinder the group performance

      3 types of conflict :
      Task conflicts  : relates to the content and goals (goals and content)
      Relationship conflicts : focuses on interpersonal relations (relations)
      Process conflicts : is about how the work gets done (process of work)

      3 types mta3 loci of conflict  (occur) :
      dyadic conflict : between 2 people
      intragroup conflict : within the group or team
      intergroup conflict : between teams or groups


      THE CONFLICT PROCESS :
      stage 1 : incompatibility or potentiel opposition : famma conditions w variables w communications ...
      stage 2 : cognition and personalization : perceived or felt conflict
      stage 3 : intentions : competing , avoiding , compromising , collaborating
      stage 4 : behavior : party behavior , reaction
      stage 5 : outcomes : increase or decrease group performance

      conflict intensity continuum : misunderstaning -> challenging over questiong -> verbal attacks -> threats -> physical -> destruction
      outcomes :
         constructive : improves quality , stimulates creativity innovation ,curiosity interest  envir self-evaluation change
         destructive : reduces group effectiveness , threats groups survival ,  breeds discontent


         Negotiation or bargaining :
         process in which two or more parties exchange goods or services and attempt to agree upon the exchange rate for them

         5 steps of negotiation process :
         1-Preparation and planning
         2-definition of ground rules
         3-clarification and justification
         4-bargaining and problem solving
         5-closure and implementation
         }








        */

