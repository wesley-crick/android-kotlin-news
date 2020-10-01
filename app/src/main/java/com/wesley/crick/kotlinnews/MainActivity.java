package com.wesley.crick.kotlinnews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.wesley.crick.kotlinnews.controllers.ArticleAdapter;
import com.wesley.crick.kotlinnews.controllers.ArticleController;
import com.wesley.crick.kotlinnews.objects.Article;
import com.wesley.crick.kotlinnews.objects.ResponseTemplate;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link vars to ui
        this.mainProgressBar = findViewById(R.id.mainProgressBar);
        findViewById(R.id.mainRFunny).setOnClickListener( (v) -> {
            getArticles(SubReddit.funny);
        } );
        findViewById(R.id.mainRKotlin).setOnClickListener( (v) -> {
            getArticles(SubReddit.kotlin);
        } );

        getArticles(SubReddit.kotlin);
    }

    /**
     * Make an API request to the articles in the subreddit
     *
     * @param r The subreddit to pull articles from
     */
    private void getArticles(SubReddit r) {
        this.mainProgressBar.setVisibility(View.VISIBLE);

        switch (r) {
            case funny:
                ArticleController.getInstance().getFunnyArticles(this::articlesReturned);
                break;
            default:
                ArticleController.getInstance().getKotlinArticles(this::articlesReturned);
        }
    }

    /**
     * Direct the article response to success or error methods
     * @param rt The main details for the response
     */
    private void articlesReturned(ResponseTemplate<Article[]> rt) {
        // When this is called, it is run on a separate thread.
        // So we have to bring the data back to the main thread to interact with the UI.
        runOnUiThread( () -> {
            // If success, build the recycler view
            if ( rt.getCode() == 0 ) {
                buildArticleRecyclerView(rt.obj);
            } else {
                // Response failed, display error message.
                dislayAlert(rt.getMessage());
                this.mainProgressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Display an alert dialog with the given message.
     * TODO Flush out this method into a static function, with more options. title, pos/neg callbacks...etc
     * @param message
     */
    private void dislayAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (d, v) -> {
                   d.dismiss();
                })
                .create()
                .show();
    }

    /**
     * Build the recycler view and adapter.
     *
     * @param articles The articles to display
     */
    private void buildArticleRecyclerView(Article[] articles) {
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);

        // Depending on screen size and rotation, display the Articles in a certain view
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 3));
        }else if (isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this.getApplicationContext(), 2));
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        }

        recyclerView.setAdapter(new ArticleAdapter(articles, this));

        this.mainProgressBar.setVisibility(View.GONE);
    }

    /**
     * Supported subreddits
     */
    public enum SubReddit {
        funny, kotlin
    }
}