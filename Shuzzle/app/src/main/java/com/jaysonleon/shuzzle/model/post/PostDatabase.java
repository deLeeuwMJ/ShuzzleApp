package com.jaysonleon.shuzzle.model.post;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jaysonleon.shuzzle.model.gallery.SavedPost;
import com.jaysonleon.shuzzle.model.gallery.SavedPostDao;

@Database(entities = {Post.class, SavedPost.class}, version = 1, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase {

    private static PostDatabase instance;

    public abstract PostDao postDao();
    public abstract SavedPostDao savedDao();

    public static synchronized PostDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PostDatabase.class, "shuzzle_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PostDao postDao;
        private SavedPostDao savedDao;

        private PopulateDbAsyncTask(PostDatabase db) {
            postDao = db.postDao();
            savedDao = db.savedDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //insert
            return null;
        }
    }
}

