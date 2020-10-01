package com.wesley.crick.kotlinnews.controllers;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;
import com.wesley.crick.kotlinnews.ArticleActivity;
import com.wesley.crick.kotlinnews.R;
import com.wesley.crick.kotlinnews.objects.Article;

/**
 * Use to display the articles in a recycler view.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    /// Stores a list of the articles to display
    private Article[] articles;
    private Activity activity;

    public ArticleAdapter(Article[] articles, Activity activity) {
        this.articles = articles;
        this.activity = activity;
    }

    /**
     * Structure for the singular item that is reused in the recycler view
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView articleTitle;
        public ImageView articleImage;
        public MaterialCardView card;
        public MyViewHolder(View v) {
            super(v);
            this.articleTitle = v.findViewById(R.id.article_item_title);
            this.articleImage = v.findViewById(R.id.article_item_image);
            this.card = v.findViewById(R.id.article_item_card);
        }
    }

    @NonNull
    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout used for the items
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.MyViewHolder holder, int position) {
        final Article a = this.articles[position];
        holder.articleTitle.setText(a.getTitle());

        // If the post has a thumbnail, GET the image and put it in the ImageView
        if ( a.hasThumbnail() ) {
            Picasso.get().load(a.getThumbnail()).into(holder.articleImage);
            holder.articleImage.setVisibility(View.VISIBLE);
        } else {
            // Hide the ImageView
            holder.articleImage.setVisibility(View.GONE);
        }

        // Clear the previous click listener
        holder.card.setOnClickListener(null);
        holder.card.setOnClickListener( (v) -> {
            Intent i = new Intent(this.activity, ArticleActivity.class);
            i.putExtra(ArticleActivity.ARTICLE_BUNDLE, a);
            this.activity.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return this.articles.length;
    }
}
