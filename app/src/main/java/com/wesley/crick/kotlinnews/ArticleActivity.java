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

    /// Key used to retrieve the article from the bundle
    public static final String ARTICLE_BUNDLE = "ARTICLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ActionBar actionBar = getSupportActionBar();
        // Set actionbar to have a back button
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        Article a = (Article)getIntent().getSerializableExtra(ARTICLE_BUNDLE);
        assert a != null;
        // Set the title of the actionbar to the article title
        actionBar.setTitle(a.getTitle());

        // Link vars to UI
        ImageView articleImage = findViewById(R.id.article_image);
        TextView articleLink = findViewById(R.id.article_link);
        WebView articleWebview = findViewById(R.id.article_webview);

        // Load image into ImageView
        if ( a.hasThumbnail() ) {
            articleImage.setVisibility(View.VISIBLE);
            Picasso.get().load(a.getThumbnail()).into(articleImage);
        } else {
            // Hide the ImageView if there is no image
            articleImage.setVisibility(View.GONE);
        }

        // Set up UI when the Article is a link
        if ( a.getArticleType() == Article.ArticleType.link) {
            articleWebview.setVisibility(View.GONE);
            articleLink.setVisibility(View.VISIBLE);
            articleLink.setText(a.getOverrideUrl());
            articleLink.setOnClickListener((v)-> this.onArticleLinkClicked(a.getOverrideUrl()));
        } else if (a.getArticleType() == Article.ArticleType.text) {
            // Set up UI when Article is a text post
            articleLink.setVisibility(View.GONE);
            articleWebview.setVisibility(View.VISIBLE);

            Spanned html = HtmlCompat.fromHtml(a.getHtml(), HtmlCompat.FROM_HTML_MODE_LEGACY);
            articleWebview.loadData(html.toString(), "text/html; charset=utf-8", "utf-8");
        } else {
            // Hide the webview and link. Likely just an image
            articleWebview.setVisibility(View.GONE);
            articleLink.setVisibility(View.GONE);
        }
    }

    /**
     * When the link has been clicked on, open to browser or supported app.
     * @param url The URL to redirect too
     */
    private void onArticleLinkClicked(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Back button clicked, navigate back to previous screen
        if ( item.getItemId() == android.R.id.home ) {
            finish();
        }
        return true;
    }
}