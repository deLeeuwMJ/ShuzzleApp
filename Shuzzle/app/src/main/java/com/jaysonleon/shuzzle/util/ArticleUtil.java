package com.jaysonleon.shuzzle.util;

import android.content.Context;

import com.jaysonleon.shuzzle.controllers.article.WebApiListener;
import com.jaysonleon.shuzzle.model.webapi.WebApiRequest;
import com.jaysonleon.shuzzle.model.webapi.WebApi;

public class ArticleUtil {

    public static synchronized void requestApiData(WebApiRequest webApiRequest, WebApiListener webApiListener, Context context) {

        WebApi webApi = new WebApi.Builder()
                .withListener(webApiListener)
                .context(context)
                .subreddit(webApiRequest.getSubreddit())
                .title(webApiRequest.getTitle())
                .sort(webApiRequest.getSort())
                .limit(webApiRequest.getLimit())
                .build();

        if(webApi != null)
            webApi.execute();
    }
}
