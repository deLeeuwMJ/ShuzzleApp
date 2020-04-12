package com.jaysonleon.shuzzle.model.article;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jaysonleon.shuzzle.controllers.article.WebApiListener;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractWebApi extends AsyncTask<Void, Void, Void> {

    private static final String TAG = AbstractWebApi.class.getSimpleName();

    protected ArrayList<WebApiListener> _aListeners = new ArrayList();
    protected ArticleFactory articleFactory;

    protected AbstractWebApi(Context context, WebApiListener listener) {
        this.registerListener(listener);
        this.articleFactory = new ArticleFactory(context, listener);
    }

    public void registerListener(WebApiListener mListener) {
        if (mListener != null) {
            this._aListeners.add(mListener);
        }
    }

    protected void dispatchOnStart() {
        Iterator i$ = this._aListeners.iterator();

        while (i$.hasNext()) {
            WebApiListener mListener = (WebApiListener) i$.next();
            mListener.onApiStart();
        }
    }

    private void dispatchOnCancelled() {
        Iterator i$ = this._aListeners.iterator();

        while (i$.hasNext()) {
            WebApiListener mListener = (WebApiListener) i$.next();
            mListener.onApiCancelled();
        }
    }

    protected Void doInBackground(Void... voids) {
        String url = this.constructURL();
        this.articleFactory.getEvents(url);
        Log.d(TAG, url);

        return null;
    }

    protected abstract String constructURL();

    protected void onPreExecute() {
        this.dispatchOnStart();
    }

    protected void onCancelled() {
        this.dispatchOnCancelled();
    }
}
