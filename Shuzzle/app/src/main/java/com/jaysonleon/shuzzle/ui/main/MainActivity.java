package com.jaysonleon.shuzzle.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.controllers.webapi.WebApiListener;
import com.jaysonleon.shuzzle.model.post.Post;
import com.jaysonleon.shuzzle.model.gallery.SavedPost;
import com.jaysonleon.shuzzle.model.gallery.SavedPostViewModel;
import com.jaysonleon.shuzzle.model.webapi.WebApiRequest;
import com.jaysonleon.shuzzle.model.post.PostViewModel;
import com.jaysonleon.shuzzle.model.subreddit.CategoryEnum;
import com.jaysonleon.shuzzle.ui.gallery.GalleryActivity;
import com.jaysonleon.shuzzle.util.PostUtil;
import com.jaysonleon.shuzzle.util.SharedPreferencesUtil;
import com.jaysonleon.shuzzle.util.SnackbarUtil;
import com.jaysonleon.shuzzle.util.SubRedditUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements WebApiListener, View.OnClickListener, RequestListener<Drawable> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final String SP_TAG = "FILTER_SETTINGS";
    private final String CAT_FILTER_TAG = "CATEGORY_FILTER";

    private PostViewModel postViewModel;
    private SavedPostViewModel savedPostViewModel;
    private List<Post> dataSet;
    private PhotoView image_v;
    private MaterialTextView title_tv;
    private AppCompatImageButton left_ib, right_ib, left_tb, right_tb;
    private MaterialButton filterButton;
    private Spinner spinner;
    private LinearLayout filterLayout;
    private boolean filterButtonActive;
    private WebApiRequest webApiRequest;
    private ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        this.progressBar = findViewById(R.id.main_progressbar);

        this.left_ib.setOnClickListener(MainActivity.this);
        this.right_ib.setOnClickListener(MainActivity.this);
        this.left_tb.setOnClickListener(MainActivity.this);
        this.right_tb.setOnClickListener(MainActivity.this);
        this.filterButton.setOnClickListener(MainActivity.this);

        this.left_tb.setVisibility(View.VISIBLE);
        this.right_tb.setVisibility(View.VISIBLE);

        this.postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        this.savedPostViewModel = ViewModelProviders.of(this).get(SavedPostViewModel.class);
        this.postViewModel.deleteAll();

        filterButtonActive = false;
        setFilterSpinner();

        this.webApiRequest = new WebApiRequest(
                SubRedditUtil.retrieveSubReddits(
                        MainActivity.this,
                        CategoryEnum.valueOf(((CategoryEnum)spinner.getSelectedItem()).name())
                ),
                "",
                "new",
                25,
                ""
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkApiRequest();

        this.postViewModel.getList().observe(MainActivity.this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                dataSet = posts;
                updateData();
            }
        });
    }

    @Override
    public void onApiStart() {
        Log.i(TAG, "Api is starting");
        webApiRequest.setActive(true);
        progressBar.show();
    }

    @Override
    public void onApiSuccess(ArrayList<Post> posts) {
        Log.i(TAG, "Api success");

        for (Post post : posts) {
            this.postViewModel.insert(post);
        }

        webApiRequest.setActive(false);
        progressBar.hide();
    }

    @Override
    public void onApiCancelled() {
        Log.i(TAG, "Api is canceled");
        webApiRequest.setActive(false);
        progressBar.hide();
    }

    @Override
    public void onApiFailure(Exception e) {
        Log.wtf(TAG, e.getMessage());
        webApiRequest.setActive(false);
        progressBar.hide();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_filter_button:
                String selected = ((CategoryEnum)spinner.getSelectedItem()).name();
                Log.i(TAG, selected);

                SharedPreferencesUtil.set(MainActivity.this, CAT_FILTER_TAG, selected, SP_TAG);

                webApiRequest.setSubreddit(
                        SubRedditUtil.retrieveSubReddits(
                        MainActivity.this,
                                CategoryEnum.valueOf(selected)
                        )
                );

                webApiRequest.setSubredditChanged(true);

                this.postViewModel.deleteAll();
                this.dataSet.clear();
                checkApiRequest();

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
                if (this.dataSet.size() > 0) {
                    checkApiRequest();
                    this.postViewModel.delete(dataSet.get(0));
                }
                break;
            case R.id.main_right_image_button:
                if (this.dataSet.size() > 0) {
                    Post tempPost = this.dataSet.get(0);

                    this.savedPostViewModel.insert(
                            new SavedPost(
                                    tempPost.getTitle(),
                                    tempPost.getCreated(),
                                    tempPost.getUrl(),
                                    tempPost.getName()
                    ));

                    checkApiRequest();
                    this.postViewModel.delete(dataSet.get(0));
                }
                break;
        }
        updateData();
    }

    private void setFilterSpinner() {
        final ArrayAdapter<CategoryEnum> arrayAdapter = new ArrayAdapter<CategoryEnum>(this, android.R.layout.simple_spinner_item, CategoryEnum.values());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        String storedCategory = SharedPreferencesUtil.get(MainActivity.this, CAT_FILTER_TAG, SP_TAG);
        CategoryEnum chosenCategory;

        if (storedCategory != null){
            chosenCategory = CategoryEnum.valueOf(storedCategory);
        } else {
            chosenCategory = CategoryEnum.MAN_MADE;
        }

        spinner.setSelection(arrayAdapter.getPosition(chosenCategory));
    }

    private void updateData() {
        if (this.dataSet.size() > 0) {

            progressBar.show();
            Glide.with(MainActivity.this)
                    .load(this.dataSet.get(0).getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .listener(MainActivity.this)
                    .into(this.image_v);

            this.title_tv.setText(dataSet.get(0).getTitle());
        }
    }

    public void checkApiRequest(){
        Log.i(TAG, String.valueOf(this.dataSet.size()));

        if (this.dataSet.size() > 0) webApiRequest.setAfter(this.dataSet.get(0).getName());

        if (this.dataSet.size() <= 1 && !webApiRequest.isActive()) {

            if(webApiRequest.isSubredditChanged()){
                webApiRequest.setAfter("");
                webApiRequest.setSubredditChanged(false);
            }

            PostUtil.requestApiData(
                    webApiRequest,
                    MainActivity.this,
                    MainActivity.this);
        }
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        SnackbarUtil.showSnackBar(MainActivity.this, "Couldn't load image");
        progressBar.hide();
        if (this.dataSet.size() > 0) {
            checkApiRequest();
            this.postViewModel.delete(dataSet.get(0));
        }
        updateData();
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        progressBar.hide();
        return false;
    }
}
