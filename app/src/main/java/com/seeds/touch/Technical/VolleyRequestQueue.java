package com.seeds.touch.Technical;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequestQueue {
    private static RequestQueue instance;

    public static RequestQueue getInstance(Context context) {
        if(instance==null)
            instance=Volley.newRequestQueue(context);
        return instance;
    }

    private VolleyRequestQueue() {
    }
}
