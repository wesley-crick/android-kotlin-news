package com.wesley.crick.kotlinnews;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wesley.crick.kotlinnews.objects.Article;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    /// Stores a list of the articles to display
    private Article[] articles;

    public ArticleAdapter(Article[] articles) {
        this.articles = articles;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView articleTitle;
        public ImageView articleImage;
        public MyViewHolder(View v) {
            super(v);
            this.articleTitle = v.findViewById(R.id.article_item_title);
            this.articleImage = v.findViewById(R.id.article_item_image);
        }
    }

    @NonNull
    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.MyViewHolder holder, int position) {
        Article a = this.articles[position];
        holder.articleTitle.setText(a.getTitle());

        //Log.e("ArticleAd", "Has Thumbnail: " + a.hasThumbnail() + " thumbnail: " + a.getThumbnail());

        if ( a.hasThumbnail() ) {
            Picasso.get().load(a.getThumbnail()).into(holder.articleImage);
            holder.articleImage.setVisibility(View.VISIBLE);
        } else {
            holder.articleImage.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return this.articles.length;
    }
}
