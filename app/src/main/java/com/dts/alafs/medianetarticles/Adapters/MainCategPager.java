package com.dts.alafs.medianetarticles.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dts.alafs.medianetarticles.mainactivities.ArticleActivity;
import com.dts.alafs.medianetarticles.model.Article;
import com.dts.alafs.medianetarticles.R;
import com.google.gson.Gson;

import java.util.List;

public class MainCategPager extends PagerAdapter {

    private TextView textViewTitle;
    private TextView textViewSummary;
    private TextView textViewLike;
    private TextView textViewComment;
    private Context context;
    private List<Article> articles;

    public MainCategPager(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }
    @Override
    public int getCount() {
            return this.articles.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    /*
        This callback is responsible for creating a page. We inflate the layout and set the drawable
        to the ImageView based on the position. In the end we add the inflated layout to the parent
        container .This method returns an object key to identify the page view, but in this example page view
        itself acts as the object key
        */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_small_article, null);

        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewSummary = view.findViewById(R.id.textViewSummary);
        textViewLike=view.findViewById(R.id.likeNbr);
        textViewComment=view.findViewById(R.id.commentNbr);

        textViewTitle.setText(articles.get(position).getTitle());
        textViewSummary.setText(articles.get(position).getSummary());
        textViewLike.setText(articles.get(position).getLikes()+"");
        textViewComment.setText(articles.get(position).getCommentsNbr()+"");

        textViewSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ArticleActivity.class);
                String s = (new Gson().toJson(articles.get(position)));
                Log.d("Article Object",s);
                intent.putExtra("article",s);
                 view.getContext().startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }
    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
    /*

    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

}