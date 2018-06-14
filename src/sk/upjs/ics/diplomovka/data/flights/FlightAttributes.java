package sk.upjs.ics.diplomovka.data.flights;

import java.util.*;

public class FlightAttributes {

    private List<String> categories = Collections.emptyList();
    private List<Aircraft> aircrafts = Collections.emptyList();
    private Map<String, Aircraft> namesToAircrafts = Collections.emptyMap();
    private List<String> engineTypes = Collections.emptyList();

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public Aircraft getAircraftByName(String name) {
        return namesToAircrafts.get(name);
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getCategoryById(int id) {
        return categories.get(id);
    }

    public List<String> getEngineTypes() {
        return engineTypes;
    }

    public String getEngineTypeById(int id) {
        return engineTypes.get(id);
    }

    public FlightAttributes setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public FlightAttributes setAircrafts(List<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
        namesToAircrafts = new HashMap<>();
        for (Aircraft aircraft : aircrafts) {
            namesToAircrafts.put(aircraft.getName(), aircraft);
        }
        return this;
    }

    public FlightAttributes setEngineTypes(List<String> engineTypes) {
        this.engineTypes = engineTypes;
        return this;
    }
}
