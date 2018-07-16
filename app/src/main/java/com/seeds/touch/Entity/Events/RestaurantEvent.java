package com.seeds.touch.Entity.Events;

import android.location.Location;
import java.util.Calendar;
import java.util.HashSet;

import static com.seeds.touch.Technical.Enums.*;

public class RestaurantEvent extends Event {
    private MealMode mealMode;

    public RestaurantEvent(String title, Calendar startDate, Calendar endDate, Location location,
                           String description, int ATTENDER_NUMBER_RANGE, MealMode mealMode) {
        super(title, startDate, endDate, location, description, ATTENDER_NUMBER_RANGE);
        this.mealMode = mealMode;
    }

    public MealMode getMealMode() {
        return mealMode;
    }

    public void setMealMode(MealMode mealMode) {
        this.mealMode = mealMode;
    }
}
