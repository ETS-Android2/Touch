package com.seeds.touch.Entity.Events;

import android.location.Location;
import java.util.Calendar;
import java.util.HashSet;

public class TripEvent extends Event {
    public TripEvent(String title, Calendar startDate, Calendar endDate, Location location,
                     String description, HashSet<String> tags, int ATTENDER_NUMBER_RANGE) {
        super(title, startDate, endDate, location, description, tags, ATTENDER_NUMBER_RANGE);
    }
}
