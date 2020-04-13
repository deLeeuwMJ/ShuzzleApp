package com.jaysonleon.shuzzle.model.post;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jaysonleon.shuzzle.controllers.webapi.WebApiListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostFactory {
    private RequestQueue requestQueue;
    private WebApiListener webApiListener;

    private final String TAG = getClass().getSimpleName();

    public PostFactory(Context context, WebApiListener listener) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.webApiListener = listener;
    }

    public void getContent(String webUrl) {
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, webUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                try {
                    webApiListener.onApiSuccess(parseJson(response));
                } catch (JSONException e) {
                    Log.e("JSON error in " + TAG, e.getMessage());
                    webApiListener.onApiFailure(e);
                }
            }
        }, error -> {
            Log.e(TAG, error.toString());
            webApiListener.onApiFailure(error);
        });

        this.requestQueue.add(request);
    }

    private ArrayList<Post> parseJson(JSONArray jsonArr) throws JSONException {

        ArrayList<Post> posts = new ArrayList<>();

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject tempArticle = jsonArr.getJSONObject(i);

            String title = tempArticle.getString("title");
            int created = tempArticle.getInt("created");
            String url = tempArticle.getString("url");
            String name = tempArticle.getString("name");

            posts.add(
                    new Post(
                            title,
                            created,
                            url,
                            name
                    )
            );
        }

        return posts;
    }
}
