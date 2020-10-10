package com.dts.alafs.medianetarticles.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dts.alafs.medianetarticles.model.Comment;
import com.dts.alafs.medianetarticles.R;

import java.util.ArrayList;

public class CommentsAdapter extends  ArrayAdapter<Comment> {

    private   Activity context;
    private ArrayList<Comment> arrayList;
    private Bitmap bitmap;

    public CommentsAdapter(Activity context,ArrayList<Comment>  arrayList) {
        super(context, R.layout.item_small_article,arrayList);
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_comment, null,true);

        TextView comment =  rowView.findViewById(R.id.commentTextv);
        TextView userName =  rowView.findViewById(R.id.commentUsername);
        ImageView userImage =   rowView.findViewById(R.id.imageProfileComment);

        comment.setText(arrayList.get(position).getContent());
        userName.setText(arrayList.get(position).getUsername());
        bitmap=StringToBitMap(arrayList.get(position).getUserImage());
        userImage.setImageBitmap(bitmap);

        return rowView;
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
}
