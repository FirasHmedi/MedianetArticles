package com.dts.alafs.medianetarticles.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dts.alafs.medianetarticles.model.Article;
import com.dts.alafs.medianetarticles.R;

import java.util.List;


public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    //List to store all articles
    private List<Article> articles;
    private final OnItemClickListener listener;
    Context context;
    //Constructor of this class
    public BookmarkAdapter(List<Article> articles, Context context, OnItemClickListener listener){
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
        public LinearLayout laylikesComments;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSummary = itemView.findViewById(R.id.textViewSummary);
            laylikesComments = itemView.findViewById(R.id.laylikesComments);
            laylikesComments.setVisibility(View.GONE);

        }

        public void bind(final Article article, final OnItemClickListener listener) {
            textViewTitle.setText(article.getTitle());
            textViewSummary.setText(article.getContent());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(article);
                }
            });
        }

    }
}