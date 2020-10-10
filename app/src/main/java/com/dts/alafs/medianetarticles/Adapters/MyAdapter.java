package com.dts.alafs.medianetarticles.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dts.alafs.medianetarticles.Objects.ArticleObject;
import com.dts.alafs.medianetarticles.R;

public class MyAdapter extends  ArrayAdapter<ArticleObject> {

    private final Activity context;
    private final ArticleObject[] articleObject;


    public MyAdapter(Activity context,ArticleObject[] articleObject) {
        super(context, R.layout.small_article, articleObject);
        // TODO Auto-generated constructor stub

        this.context=context;

        this.articleObject=articleObject;
    }




    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.small_article, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView contentText = (TextView) rowView.findViewById(R.id.textViewContent);

       titleText.setText(articleObject[position].getTitle());
       contentText.setText(articleObject[position].getContent());

        return rowView;

    };

}
