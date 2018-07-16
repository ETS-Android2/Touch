package com.seeds.touch.Management.Manager;

import android.content.Context;

import com.seeds.touch.Management.Interface.PostTimeInterface;
import com.seeds.touch.Technical.Enums;

import java.io.IOException;

public class ItemManager {
    private static ItemManager instance;

    public static ItemManager getInstance() {
        if (instance == null)
            instance = new ItemManager();
        return instance;
    }

    private ItemManager() {
    }

    public void readItems(Context context, Enums.EventTypes eventTypes, PostTimeInterface onPost) {
        Object[] objects=new Object[2];
        try {
            onPost.does(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
