package com.wesley.crick.kotlinnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wesley.crick.kotlinnews.objects.Article;

public class ArticleActivity extends AppCompatActivity {

    // Key used to retrieve the article from the bundle
    public static final String ARTICLE_BUNDLE = "ARTICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ActionBar actionBar = getSupportActionBar();
        if ( actionBar == null ) {
            // ToDo display error
            finish();
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);  // Set actionbar to have a back button

        Article a = (Article)getIntent().getSerializableExtra(ARTICLE_BUNDLE);
        if ( a == null ) {
            // ToDo Display an error
            finish();
            return;
        }
        // Set the title of the actionbar to the article title
        actionBar.setTitle(a.getTitle());

        // Link vars to UI
        ImageView articleImage = findViewById(R.id.article_image);
        TextView articleLink = findViewById(R.id.article_link);
        WebView articleWebview = findViewById(R.id.article_webview);

        if ( a.hasThumbnail() ) {
            articleImage.setVisibility(View.VISIBLE);
            Picasso.get().load(a.getThumbnail()).into(articleImage);
        } else {
            articleImage.setVisibility(View.GONE);
        }

        if ( a.getArticleType() == Article.ArticleType.link) {
            articleWebview.setVisibility(View.GONE);
            articleLink.setVisibility(View.VISIBLE);
            articleLink.setText(a.getOverrideUrl());
            articleLink.setOnClickListener((v)-> this.onArticleLinkClicked(a.getOverrideUrl()));
        } else if (a.getArticleType() == Article.ArticleType.text) {
            articleLink.setVisibility(View.GONE);
            articleWebview.setVisibility(View.VISIBLE);

            Spanned html = HtmlCompat.fromHtml(a.getHtml(), HtmlCompat.FROM_HTML_MODE_LEGACY);
            articleWebview.loadData(html.toString(), "text/html; charset=utf-8", "utf-8");
        } else {
            articleWebview.setVisibility(View.GONE);
            articleLink.setVisibility(View.GONE);
        }
    }

    private void onArticleLinkClicked(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == android.R.id.home ) {
            finish();
        }
        return true;
    }
}