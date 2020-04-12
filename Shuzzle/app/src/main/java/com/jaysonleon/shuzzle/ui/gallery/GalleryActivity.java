package com.jaysonleon.shuzzle.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.controllers.gallery.SavedArticleAdapter;
import com.jaysonleon.shuzzle.model.gallery.SavedArticle;
import com.jaysonleon.shuzzle.model.gallery.SavedArticleViewModel;
import com.jaysonleon.shuzzle.ui.main.MainActivity;

import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private SavedArticleAdapter adapter;
    private RecyclerView recyclerView;
    private SavedArticleViewModel savedArticleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        AppCompatImageButton right_tb = findViewById(R.id.toolbar_right_button);
        right_tb.setImageResource(R.drawable.ic_keyboard_arrow_right_custom_24dp);

        AppCompatImageButton left_tb = findViewById(R.id.toolbar_left_button);
        left_tb.setVisibility(View.INVISIBLE);

        right_tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        this.recyclerView = findViewById(R.id.gallery_recyclerview);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        savedArticleViewModel = ViewModelProviders.of(this).get(SavedArticleViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        savedArticleViewModel.getAllEvents().observe(this, new Observer<List<SavedArticle>>() {
            @Override
            public void onChanged(List<SavedArticle> events) {
                adapter.submitList(events);
                adapter.notifyDataSetChanged();
            }
        });

        this.adapter = new SavedArticleAdapter(GalleryActivity.this, this.savedArticleViewModel);
        this.recyclerView.setAdapter(this.adapter);
    }
}
