package com.jaysonleon.shuzzle.model.gallery;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jaysonleon.shuzzle.model.post.PostDatabase;

import java.util.List;

public class SavedPostRepository {

    private SavedPostDao savedDao;
    private LiveData<List<SavedPost>> livePostList;

    public SavedPostRepository(Application application) {
        PostDatabase database = PostDatabase.getInstance(application);
        savedDao = database.savedDao();
        livePostList = savedDao.getAll();
    }

    public void insert(SavedPost post) {
        new InsertAsyncTask(savedDao).execute(post);
    }

    public void update(SavedPost post) {
        new UpdateAsyncTask(savedDao).execute(post);
    }

    public void delete(SavedPost post) {
        new DeleteAsyncTask(savedDao).execute(post);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(savedDao).execute();
    }

    public LiveData<List<SavedPost>> getList() {
        return livePostList;
    }

    private static class InsertAsyncTask extends AsyncTask<SavedPost, Void, Void> {
        private SavedPostDao savedDao;

        private InsertAsyncTask(SavedPostDao savedDao) {
            this.savedDao = savedDao;
        }

        @Override
        protected Void doInBackground(SavedPost... posts) {
            savedDao.insert(posts[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<SavedPost, Void, Void> {
        private SavedPostDao savedDao;

        private UpdateAsyncTask(SavedPostDao savedDao) {
            this.savedDao = savedDao;
        }

        @Override
        protected Void doInBackground(SavedPost... posts) {
            savedDao.update(posts[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<SavedPost, Void, Void> {
        private SavedPostDao articleDao;

        private DeleteAsyncTask(SavedPostDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(SavedPost... articles) {
            articleDao.delete(articles[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private SavedPostDao savedDao;

        private DeleteAllAsyncTask(SavedPostDao savedDao) {
            this.savedDao = savedDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            savedDao.deleteAll();
            return null;
        }
    }
}
