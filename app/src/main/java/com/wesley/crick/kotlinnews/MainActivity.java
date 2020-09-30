package com.wesley.crick.kotlinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.wesley.crick.kotlinnews.callbacks.GenericCallBack;
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

        this.mainProgressBar.setVisibility(View.VISIBLE);
        getArticles();
    }

    private void getArticles() {
        ArticleController.getInstance().getArticles(this::articlesReturned);
    }

    private void articlesReturned(ResponseTemplate<Article[]> rt) {
        runOnUiThread( () -> {
            if ( rt.getCode() == 0 ) {
                buildArticleRecyclerView(rt.obj);
            } else {
                // TODO Display Error
            }
        });
    }

    private void buildArticleRecyclerView(Article[] articles) {
        RecyclerView recyclerView = findViewById(R.id.mainRecyclerView);

        // use a linear layout manager
        // TODO change depending on screen size and orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(new ArticleAdapter(articles));

        this.mainProgressBar.setVisibility(View.GONE);
    }
}