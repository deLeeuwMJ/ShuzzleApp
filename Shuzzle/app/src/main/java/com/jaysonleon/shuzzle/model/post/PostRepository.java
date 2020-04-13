package com.jaysonleon.shuzzle.model.post;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PostRepository {

    private PostDao postDao;
    private LiveData<List<Post>> livePostList;

    public PostRepository(Application application) {
        PostDatabase database = PostDatabase.getInstance(application);
        postDao = database.postDao();
        livePostList = postDao.getAll();
    }

    public void insert(Post post) {
        new InsertAsyncTask(postDao).execute(post);
    }

    public void update(Post post) {
        new UpdateAsyncTask(postDao).execute(post);
    }

    public void delete(Post post) {
        new DeleteAsyncTask(postDao).execute(post);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(postDao).execute();
    }

    public LiveData<List<Post>> getList() {
        return livePostList;
    }

    private static class InsertAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao postDao;

        private InsertAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDao.insert(posts[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao postDao;

        private UpdateAsyncTask(PostDao noteDao) {
            this.postDao = noteDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDao.update(posts[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Post, Void, Void> {
        private PostDao postDao;

        private DeleteAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(Post... posts) {
            postDao.delete(posts[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private PostDao postDao;

        private DeleteAllAsyncTask(PostDao postDao) {
            this.postDao = postDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postDao.deleteAll();
            return null;
        }
    }
}
