package com.simplicityitself.navigation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationEventStore {

    final static String X = "x";
    final static String Y = "y";

    private List<Map<String, Number>> eventStore = new ArrayList<Map<String, Number>>();

    private Map<String, Number> currentLocation = new HashMap<String, Number>();
    private Map<String, Number> originalLocation = new HashMap<String, Number>();

    public LocationEventStore() {
        originalLocation.put(X, 0);
        originalLocation.put(Y, 0);
    }

    public void addEvent(Map<String, Number> event) {
        eventStore.add(event);
        recalculateCurrentLocation();
    }

    public void recalculateCurrentLocation() {
        //fully replay the event stream from the original position
        Map<String, Number> newCurrentLocation = new HashMap<String, Number>();
        newCurrentLocation.putAll(originalLocation);

        for(Map<String, Number> event: eventStore) {
            newCurrentLocation.put(X,
                    newCurrentLocation.get(X).doubleValue() + event.get("deltaX").doubleValue());
        }

        currentLocation = newCurrentLocation;
    }

    public List<Map<String, Number>> getEventStream() {
        return eventStore;
    }

    public Number getX() {
        return currentLocation.get(X);
    }

    public Number getY() {
        return currentLocation.get(Y);
    }
}
