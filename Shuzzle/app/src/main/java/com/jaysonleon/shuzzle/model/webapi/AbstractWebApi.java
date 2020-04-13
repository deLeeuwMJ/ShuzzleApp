package com.jaysonleon.shuzzle.model.webapi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jaysonleon.shuzzle.controllers.webapi.WebApiListener;
import com.jaysonleon.shuzzle.model.post.PostFactory;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractWebApi extends AsyncTask<Void, Void, Void> {

    private static final String TAG = AbstractWebApi.class.getSimpleName();

    protected ArrayList<WebApiListener> _aListeners = new ArrayList();
    protected PostFactory postFactory;

    protected AbstractWebApi(Context context, WebApiListener listener) {
        this.registerListener(listener);
        this.postFactory = new PostFactory(context, listener);
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
        this.postFactory.getContent(url);
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
