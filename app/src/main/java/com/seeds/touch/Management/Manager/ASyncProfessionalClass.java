package com.seeds.touch.Management.Manager;

import android.os.AsyncTask;

import com.seeds.touch.Management.Interface.CurrentTimeInterface;
import com.seeds.touch.Management.Interface.PostTimeInterface;

public class ASyncProfessionalClass extends AsyncTask<Object, Void, Object[]> {

    CurrentTimeInterface onNow;
    PostTimeInterface onPost;

    public ASyncProfessionalClass(CurrentTimeInterface onNow, PostTimeInterface onPost) {
        this.onPost = onPost;
        this.onNow = onNow;
    }

    @Override
    protected Object[] doInBackground(Object... objects) {
        onNow.does(objects);
        return objects;
    }

    @Override
    protected void onPostExecute(Object[] objects) {
        try {
            onPost.does(objects);
        } catch (Exception e) {

        }
    }
}
