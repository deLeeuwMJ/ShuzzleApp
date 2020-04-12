package com.jaysonleon.shuzzle.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

public class ActivityUtil {

    public static Activity getActivity(Context context)
    {
        if (context == null)
        {
            return null;
        }
        else if (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity) context;
            }
            else
            {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }
}
