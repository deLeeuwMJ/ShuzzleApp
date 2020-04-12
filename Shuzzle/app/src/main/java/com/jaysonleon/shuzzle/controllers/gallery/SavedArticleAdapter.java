package com.jaysonleon.shuzzle.controllers.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.model.article.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedArticleAdapter extends RecyclerView.Adapter<SavedArticleAdapter.ImageViewHolder> {

    private ArrayList<Article> dataSet;

    public SavedArticleAdapter(ArrayList<Article> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_recycler_item, parent, false);
//        ImageViewHolder imageViewHolder = new ImageViewHolder(view);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewHolder, int i) {
//        Picasso.get().load(dataSet.get(i).getImageURL()[0]).into(viewHolder.picture);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;


        ImageViewHolder(View itemview) {
            super(itemview);

//            picture = itemview.findViewById(R.id.mural_id);

            itemview.setOnClickListener(v -> {
                Article n = dataSet.get(ImageViewHolder.super.getAdapterPosition());
            });
        }
    }

}