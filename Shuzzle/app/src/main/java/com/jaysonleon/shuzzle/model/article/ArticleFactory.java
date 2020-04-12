package com.jaysonleon.shuzzle.model.article;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jaysonleon.shuzzle.controllers.article.WebApiListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticleFactory {
    private RequestQueue mQueue;
    private WebApiListener wrapApiListener;

    private final String TAG = getClass().getSimpleName();


    public ArticleFactory(Context context, WebApiListener listener) {
        this.mQueue = Volley.newRequestQueue(context);
        this.wrapApiListener = listener;
    }

    public void getEvents(String webUrl) {
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, webUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                try {
                    wrapApiListener.onApiSuccess(parseJson(response));
                } catch (JSONException e) {
                    Log.e("JSON error in " + TAG, e.getMessage());
                    wrapApiListener.onApiFailure(e);
                }
            }
        }, error -> Log.e(TAG, error.toString()));

        this.mQueue.add(request);
    }

    private ArrayList<Article> parseJson(JSONArray jsonArr) throws JSONException {

        ArrayList<Article> articles = new ArrayList<>();

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject tempArticle = jsonArr.getJSONObject(i);

            String subreddit = tempArticle.getString("subreddit");
            String title = tempArticle.getString("title");
            int created = tempArticle.getInt("created");
            String url = tempArticle.getString("url");
            String image = tempArticle.getString("image");

            articles.add(
                    new Article(
                            subreddit,
                            title,
                            created,
                            url,
                            image
                    )
            );
        }

        return articles;
    }
}
