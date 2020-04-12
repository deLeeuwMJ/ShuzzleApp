package com.jaysonleon.shuzzle.util;

import android.content.Context;
import android.content.res.Resources;

import com.jaysonleon.shuzzle.R;
import com.jaysonleon.shuzzle.model.subreddit.CategoryEnum;

public class SubRedditUtil {

    public static synchronized String retrieveSubReddits(Context context, CategoryEnum categoryEnum) {

        StringBuilder outputString = new StringBuilder();
        Resources res = context.getResources();
        String[] subRedditsArray = null;

        /* Get all subreddits based on selected category */
        switch (categoryEnum){
            case ART:
                subRedditsArray = res.getStringArray(R.array.Category_Art);
                break;
            case ANIMALS:
                subRedditsArray = res.getStringArray(R.array.Category_Animals);
                break;
            case NATURE:
                subRedditsArray = res.getStringArray(R.array.Category_Nature);
                break;
            case FOOD:
                subRedditsArray = res.getStringArray(R.array.Category_Food);
                break;
            case MAN_MADE:
                subRedditsArray = res.getStringArray(R.array.Category_ManMade);
                break;
        }

        /* If somehow to values didn't get assigned properly return null */
        if(subRedditsArray.length == 0){
            return null;
        }


        /* Loop througharray and make one long string with all subreddits in the chosen category */
        for(int i = 0; i< subRedditsArray.length; i++){

            /* Check if it is the last element in the array */
            StringBuilder result = subRedditsArray.length - 1 == i ? outputString.append(subRedditsArray[i]) : outputString.append(subRedditsArray[i] + "+");
            outputString = result;
        }

        return outputString.toString();
    }
}
