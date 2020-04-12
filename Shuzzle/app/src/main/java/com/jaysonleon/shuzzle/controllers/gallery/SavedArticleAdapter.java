package com.jaysonleon.shuzzle.controllers.gallery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.model.gallery.SavedArticle;
import com.jaysonleon.shuzzle.model.gallery.SavedArticleViewModel;
import com.jaysonleon.shuzzle.util.ActivityUtil;
import com.jaysonleon.shuzzle.util.SnackbarUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import rm.com.longpresspopup.LongPressPopup;
import rm.com.longpresspopup.LongPressPopupBuilder;
import rm.com.longpresspopup.PopupInflaterListener;

public class SavedArticleAdapter extends RecyclerView.Adapter<SavedArticleAdapter.ImageViewHolder> implements PopupInflaterListener {

    private SavedArticleViewModel savedArticleViewModel;
    private List<SavedArticle> dataSet;
    private LongPressPopup popup;
    private Context context;

    public SavedArticleAdapter(Context context, SavedArticleViewModel savedArticleViewModel) {
        this.dataSet = new LinkedList<>();
        this.context = context;
        this.savedArticleViewModel = savedArticleViewModel;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_recycler_item, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view, i);

        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewHolder, int i) {
        Glide.with(context).load(dataSet.get(i).getUrl()).into(viewHolder.picture);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void submitList(List<SavedArticle> articles) {
        this.dataSet = articles;
    }

    @Override
    public void onViewInflated(@Nullable String popupTag, View root) {
        SavedArticle savedArticle = dataSet.get(Integer.parseInt(popupTag));

        PhotoView pv =  root.findViewById(R.id.preview_image);
        MaterialTextView tv = root.findViewById(R.id.preview_text);
        AppCompatImageButton ivDelete = root.findViewById(R.id.preview_delete_button);
        AppCompatImageButton ivDownload = root.findViewById(R.id.preview_download_button);

        Glide.with(context).load(savedArticle.getUrl()).into(pv);
        tv.setText(savedArticle.getSubreddit());

        ivDelete.setOnClickListener(v -> {
            savedArticleViewModel.delete(savedArticle);
            dataSet.remove(savedArticle);
            SnackbarUtil.showSnackBar(ActivityUtil.getActivity(context), "Image removed from gallery");
            popup.dismissNow();
        });

        ivDownload.setOnClickListener(v -> {
            BitmapDrawable bd = (BitmapDrawable) pv.getDrawable();
            Bitmap bitmap = bd.getBitmap();

            File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "Shuzzle");
            directory.mkdirs();

            String fileName = "shuzzle_" + UUID.randomUUID().toString().substring(0,8) + ".jpg";
            File outFile = new File(directory, fileName);

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                try {
                    OutputStream outputStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    intent.setData(Uri.fromFile(outFile));
                    context.sendBroadcast(intent);

                    SnackbarUtil.showSnackBar(ActivityUtil.getActivity(context), "Image saved to device as " + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                SnackbarUtil.showSnackBar(ActivityUtil.getActivity(context), "Image not saved to device : permission denied");
            }
            popup.dismissNow();
        });
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        PhotoView preview;

        ImageViewHolder(View itemview, int i) {
            super(itemview);
            picture = itemview.findViewById(R.id.gallery_image);
            preview = itemview.findViewById(R.id.preview_image);

            itemview.setOnLongClickListener(v -> {
                popup = new LongPressPopupBuilder(context)
                        .setTarget(picture)
                        .setPopupView(R.layout.gallery_quickview, SavedArticleAdapter.this)
                        .setDismissOnLongPressStop(false)
                        .setAnimationType(LongPressPopup.ANIMATION_TYPE_FROM_CENTER)
                        .setTag(String.valueOf(ImageViewHolder.super.getAdapterPosition()))
                        .build();

                popup.showNow();
                return false;
            });
        }
    }

}