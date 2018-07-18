package com.seeds.touch.Entity.Entities;

import java.io.Serializable;

public class MovieModel implements Serializable {
    String title;
    String rating;
    String type;

    public MovieModel(String type) {
        this.type = type;
    }
}