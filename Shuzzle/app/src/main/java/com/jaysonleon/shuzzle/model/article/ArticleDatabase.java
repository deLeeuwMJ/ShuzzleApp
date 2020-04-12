package com.jaysonleon.shuzzle.model.article;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jaysonleon.shuzzle.model.gallery.SavedArticle;
import com.jaysonleon.shuzzle.model.gallery.SavedArticleDao;

@Database(entities = {Article.class, SavedArticle.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    private static ArticleDatabase instance;

    public abstract ArticleDao retrievedDao();
    public abstract SavedArticleDao savedDao();

    public static synchronized ArticleDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ArticleDatabase.class, "article_database")
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
        private ArticleDao retrievedDao;
        private SavedArticleDao savedDao;

        private PopulateDbAsyncTask(ArticleDatabase db) {
            retrievedDao = db.retrievedDao();
            savedDao = db.savedDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //insert
            return null;
        }
    }
}

