package com.seeds.touch.Management.Manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.seeds.touch.Technical.VolleyRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendNotification {
    public static void send_HTTP_req(Context context,String pushID, String title, String text) {
        final String token="7876400cd724ab913d8a99e8c4bb4e393b95c4a1";
        String url="https://panel.pushe.co/api/v1/notifications/";

        try{
            JSONObject jsonBody=new JSONObject("{ \"applications\": [\"com.seeds.touch\"], " +
                    "\"notification\": { \"title\": \""+title+"\", \"content\": \""+text+"\" } }");
            JsonObjectRequest req=new JsonObjectRequest(url,jsonBody,

                    response -> Log.d("Response",response.toString()),

                    error -> Log.d("Error msg","error occured")
            ) {

                @Override
                public Map<String, String> getHeaders()throws AuthFailureError {
                    Map<String, String> params=new HashMap<>();
                    params.put("Authorization","Token "+token);
                    params.put("Content-Type","application/json");
                    params.put("Accept","application/json");

                    return params;
                }
            };
            VolleyRequestQueue.getInstance(context).add(req);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
