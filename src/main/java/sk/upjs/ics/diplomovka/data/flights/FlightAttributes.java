package sk.upjs.ics.diplomovka.data.flights;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightAttributes {

    private Map<Integer, String> categories = Collections.emptyMap();
    private Map<Integer, Aircraft> aircrafts = Collections.emptyMap();
    private Map<String, Aircraft> namesToAircrafts = Collections.emptyMap();
    private Map<Integer, String> engineTypes = Collections.emptyMap();

    public Map<Integer, Aircraft> getAircrafts() {
        return aircrafts;
    }

    public Aircraft getAircraftByName(String name) {
        return namesToAircrafts.get(name);
    }

    public Map<Integer, String> getCategories() {
        return categories;
    }

    public String getCategoryById(int id) {
        return categories.get(id);
    }

    public Map<Integer, String> getEngineTypes() {
        return engineTypes;
    }

    public String getEngineTypeById(int id) {
        return engineTypes.get(id);
    }

    public FlightAttributes setCategories(Map<Integer, String> categories) {
        this.categories = categories;
        return this;
    }

    public FlightAttributes setAircrafts(Map<Integer, Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
        namesToAircrafts = new HashMap<>();
        for (Aircraft aircraft : aircrafts.values()) {
            namesToAircrafts.put(aircraft.getName(), aircraft);
        }
        return this;
    }

    public FlightAttributes setEngineTypes(Map<Integer, String> engineTypes) {
        this.engineTypes = engineTypes;
        return this;
    }
}
