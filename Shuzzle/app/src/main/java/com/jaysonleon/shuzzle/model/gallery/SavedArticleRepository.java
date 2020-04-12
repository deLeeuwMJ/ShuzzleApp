package com.jaysonleon.shuzzle.model.gallery;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jaysonleon.shuzzle.model.article.ArticleDatabase;

import java.util.List;

public class SavedArticleRepository {

    private SavedArticleDao articleDao;
    private LiveData<List<SavedArticle>> allArticles;

    public SavedArticleRepository(Application application) {
        ArticleDatabase database = ArticleDatabase.getInstance(application);
        articleDao = database.savedDao();
        allArticles = articleDao.getAllSavedArticles();
    }

    public void insert(SavedArticle article) {
        new InsertSavedArticleAsyncTask(articleDao).execute(article);
    }

    public void update(SavedArticle article) {
        new UpdateSavedArticleAsyncTask(articleDao).execute(article);
    }

    public void delete(SavedArticle article) {
        new DeleteSavedArticleAsyncTask(articleDao).execute(article);
    }

    public void deleteAllSavedArticles() {
        new DeleteAllNotesAsyncTask(articleDao).execute();
    }

    public LiveData<List<SavedArticle>> getAllSavedArticles() {
        return allArticles;
    }

    private static class InsertSavedArticleAsyncTask extends AsyncTask<SavedArticle, Void, Void> {
        private SavedArticleDao articleDao;

        private InsertSavedArticleAsyncTask(SavedArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(SavedArticle... articles) {
            articleDao.insert(articles[0]);
            return null;
        }
    }

    private static class UpdateSavedArticleAsyncTask extends AsyncTask<SavedArticle, Void, Void> {
        private SavedArticleDao articleDao;

        private UpdateSavedArticleAsyncTask(SavedArticleDao noteDao) {
            this.articleDao = noteDao;
        }

        @Override
        protected Void doInBackground(SavedArticle... articles) {
            articleDao.update(articles[0]);
            return null;
        }
    }

    private static class DeleteSavedArticleAsyncTask extends AsyncTask<SavedArticle, Void, Void> {
        private SavedArticleDao articleDao;

        private DeleteSavedArticleAsyncTask(SavedArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(SavedArticle... articles) {
            articleDao.delete(articles[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private SavedArticleDao articleDao;

        private DeleteAllNotesAsyncTask(SavedArticleDao articleDao) {
            this.articleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            articleDao.deleteAllSavedArticles();
            return null;
        }
    }
}
