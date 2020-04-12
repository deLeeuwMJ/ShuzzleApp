package com.jaysonleon.shuzzle.controllers.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.model.article.Article;
import com.jaysonleon.shuzzle.model.gallery.SavedArticle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SavedArticleAdapter extends RecyclerView.Adapter<SavedArticleAdapter.ImageViewHolder> {

    private List<SavedArticle> dataSet;

    public SavedArticleAdapter() {
        this.dataSet = new LinkedList<>();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_recycler_item, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewHolder, int i) {
        Picasso.get().load(dataSet.get(i).getUrl()).into(viewHolder.picture);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void submitList(List<SavedArticle> articles) {
        this.dataSet = articles;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;


        ImageViewHolder(View itemview) {
            super(itemview);
            picture = itemview.findViewById(R.id.gallery_image);
            itemview.setOnClickListener(v -> {
                SavedArticle n = dataSet.get(ImageViewHolder.super.getAdapterPosition());
            });
        }
    }

}