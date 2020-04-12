package com.jaysonleon.shuzzle.model.article;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ArticleRepository {

    private ArticleDao articleDao;
    private LiveData<List<Article>> allArticles;

    public ArticleRepository(Application application) {
        ArticleDatabase database = ArticleDatabase.getInstance(application);
        articleDao = database.retrievedDao();
        allArticles = articleDao.getAllRetrievedArticles();
    }

    public void insert(Article article) {
        new InsertEventAsyncTask(articleDao).execute(article);
    }

    public void update(Article article) {
        new UpdateEventAsyncTask(articleDao).execute(article);
    }

    public void delete(Article article) {
        new DeleteEventAsyncTask(articleDao).execute(article);
    }

    public void deleteAllEvents() {
        new DeleteAllNotesAsyncTask(articleDao).execute();
    }

    public LiveData<List<Article>> getAllArticles() {
        return allArticles;
    }

    private static class InsertEventAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao articleDao;

        private InsertEventAsyncTask(ArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            articleDao.insert(articles[0]);
            return null;
        }
    }

    private static class UpdateEventAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao articleDao;

        private UpdateEventAsyncTask(ArticleDao noteDao) {
            this.articleDao = noteDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            articleDao.update(articles[0]);
            return null;
        }
    }

    private static class DeleteEventAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao articleDao;

        private DeleteEventAsyncTask(ArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            articleDao.delete(articles[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArticleDao articleDao;

        private DeleteAllNotesAsyncTask(ArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articleDao.deleteAllRetrievedArticles();
            return null;
        }
    }
}
