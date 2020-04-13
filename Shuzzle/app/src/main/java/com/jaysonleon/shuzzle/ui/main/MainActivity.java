package com.jaysonleon.shuzzle.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.controllers.article.WebApiListener;
import com.jaysonleon.shuzzle.model.gallery.SavedArticle;
import com.jaysonleon.shuzzle.model.gallery.SavedArticleViewModel;
import com.jaysonleon.shuzzle.model.webapi.WebApiRequest;
import com.jaysonleon.shuzzle.model.article.Article;
import com.jaysonleon.shuzzle.model.article.ArticleViewModel;
import com.jaysonleon.shuzzle.model.subreddit.CategoryEnum;
import com.jaysonleon.shuzzle.ui.gallery.GalleryActivity;
import com.jaysonleon.shuzzle.util.ArticleUtil;
import com.jaysonleon.shuzzle.util.SubRedditUtil;
import com.jaysonleon.shuzzle.util.UrlUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WebApiListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArticleViewModel articleViewModel;
    private SavedArticleViewModel savedArticleViewModel;
    private List<Article> dataSet;
    private PhotoView image_v;
    private MaterialTextView title_tv;
    private AppCompatImageButton left_ib, right_ib, left_tb, right_tb;
    private MaterialButton filterButton;
    private Spinner spinner;
    private LinearLayout filterLayout;
    private int currentImageId;
    private boolean filterButtonActive;
    private WebApiRequest webApiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.currentImageId = -1;
        this.dataSet = new LinkedList<>();
        this.image_v = findViewById(R.id.main_rounded_image);
        this.title_tv = findViewById(R.id.main_picture_title);
        this.left_ib = findViewById(R.id.main_left_image_button);
        this.right_ib = findViewById(R.id.main_right_image_button);
        this.left_tb = findViewById(R.id.toolbar_left_button);
        this.right_tb = findViewById(R.id.toolbar_right_button);
        this.filterLayout = findViewById(R.id.main_filter_layout);
        this.spinner = findViewById(R.id.main_filter_spinner);
        this.filterButton = findViewById(R.id.main_filter_button);

        this.left_ib.setOnClickListener(MainActivity.this);
        this.right_ib.setOnClickListener(MainActivity.this);
        this.left_tb.setOnClickListener(MainActivity.this);
        this.right_tb.setOnClickListener(MainActivity.this);
        this.filterButton.setOnClickListener(MainActivity.this);

        this.left_tb.setVisibility(View.VISIBLE);
        this.right_tb.setVisibility(View.VISIBLE);

        this.articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        this.savedArticleViewModel = ViewModelProviders.of(this).get(SavedArticleViewModel.class);
        this.articleViewModel.deleteAllEvents();

        this.webApiRequest = new WebApiRequest(
                SubRedditUtil.retrieveSubReddits(
                        MainActivity.this,
                        CategoryEnum.MAN_MADE
                ),
                "",
                "rio",
                10
        );

        filterButtonActive = false;
        setFilterSpinner();
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.articleViewModel.getAllEvents().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                dataSet = articles;
            }
        });
    }

    @Override
    public void onApiStart() {
        Log.i(TAG, "Api is starting");
    }

    @Override
    public void onApiSuccess(ArrayList<Article> articles) {
        Log.i(TAG, "Api success");

        for (Article article : articles) {
            this.articleViewModel.insert(article);
        }
    }

    @Override
    public void onApiCancelled() {
        Log.i(TAG, "Api is canceled");
    }

    @Override
    public void onApiFailure(Exception e) {
        Log.wtf(TAG, e.getMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_filter_button:
                String selected = ((CategoryEnum)spinner.getSelectedItem()).name();
                Log.i(TAG, selected);

                webApiRequest.setSubreddit(
                        SubRedditUtil.retrieveSubReddits(
                        MainActivity.this,
                                CategoryEnum.valueOf(selected)
                        )
                );

                filterLayout.setVisibility(View.INVISIBLE);
                filterButtonActive = false;
                return;
            case R.id.toolbar_left_button:
                Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.toolbar_right_button:
                if (filterButtonActive) {
                    filterButtonActive = false;
                    filterLayout.setVisibility(View.INVISIBLE);
                } else {
                    filterButtonActive = true;
                    filterLayout.setVisibility(View.VISIBLE);
                }
                return;
            case R.id.main_left_image_button:
                if (this.dataSet.size() > 3 && this.dataSet.size() - this.currentImageId >= 3) {
                    this.articleViewModel.delete(dataSet.get(currentImageId));
                    this.dataSet.remove(currentImageId);
                }
                break;
            case R.id.main_right_image_button:
                if (this.dataSet.size() > 3 && this.dataSet.size() - this.currentImageId >= 3) {
                    Article tempArticle = this.dataSet.get(currentImageId);

                    this.savedArticleViewModel.insert(
                            new SavedArticle(
                                    tempArticle.getSubreddit(),
                                    tempArticle.getTitle(),
                                    tempArticle.getCreated(),
                                    tempArticle.getUrl(),
                                    tempArticle.getImage()
                    ));
                }
                break;
        }
        this.currentImageId++;
        updateData();
        checkApiRequest();
    }

    private void setFilterSpinner() {
        final ArrayAdapter<CategoryEnum> arrayAdapter = new ArrayAdapter<CategoryEnum>(this, android.R.layout.simple_spinner_item, CategoryEnum.values());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
    }

    private void updateData() {
        if (this.currentImageId < this.dataSet.size()) {


            Glide.with(MainActivity.this)
                    .load(UrlUtil.convertImageUrl(this.dataSet.get(this.currentImageId).getImage()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(this.image_v);

            this.title_tv.setText(dataSet.get(this.currentImageId).getSubreddit());
        }
    }

    public void checkApiRequest(){
        Log.i(TAG, String.valueOf(this.dataSet.size()));
        Log.i(TAG, String.valueOf(this.currentImageId));

        if (this.dataSet.size() - this.currentImageId <= 5) {
            ArticleUtil.requestApiData(
                    webApiRequest,
                    MainActivity.this,
                    MainActivity.this);
        }
    }
}
