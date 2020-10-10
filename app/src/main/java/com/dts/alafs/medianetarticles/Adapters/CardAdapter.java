package com.dts.alafs.medianetarticles.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.alafs.medianetarticles.model.Article;
import com.dts.alafs.medianetarticles.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    //List to store all articles
    private List<Article> articles;
    private final OnItemClickListener listener;
    Context context;
    //Constructor of this class
    public CardAdapter(List<Article> articles, Context context, OnItemClickListener listener){
        super();
        //Getting all articles
        this.articles = articles;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small_article, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Getting the particular item from the list  //Showing data on the views
        holder.bind(articles.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return articles.size();
        }

    class ViewHolder extends RecyclerView.ViewHolder  {
        //Views
        public TextView textViewTitle;
        public TextView textViewSummary;
        public TextView textViewLike;
        public TextView textViewComment;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSummary = itemView.findViewById(R.id.textViewSummary);
            textViewLike =  itemView.findViewById(R.id.likeNbr);
            textViewComment = itemView.findViewById(R.id.commentNbr);
        }

        public void bind(final Article article, final OnItemClickListener listener) {
            textViewTitle.setText(article.getTitle());
            textViewSummary.setText(article.getSummary());
            Log.d("likes", article.getLikes()+"");
            textViewLike.setText(article.getLikes()+"");
            textViewComment.setText(article.getCommentsNbr()+"");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(article);
                }
            });
        }
    }
}