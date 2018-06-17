package sk.upjs.ics.diplomovka.storage.flights;

import java.util.*;

public class FlightAttributes {

    private Map<Integer, String> categories = Collections.emptyMap();
    private Map<Integer, Aircraft> aircrafts = Collections.emptyMap();
    private Map<Integer, String> engineTypes = Collections.emptyMap();
    private Map<Integer, Transfer> transfers = Collections.emptyMap();

    public Map<Integer, Aircraft> getAircrafts() {
        return aircrafts;
    }

    public Aircraft getAircraftById(int id) {
        return aircrafts.get(id);
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

    public Map<Integer, Transfer> getTransfers() {
        return transfers;
    }

    public List<Transfer> getTransfersByIds(List<Integer> transferIds) {
        List<Transfer> transfersWithIds = new ArrayList<>();

        for (Integer id : transferIds) {
            transfersWithIds.add(transfers.get(id));
        }

        return transfersWithIds;
    }

    public FlightAttributes setCategories(Map<Integer, String> categories) {
        this.categories = categories;
        return this;
    }

    public FlightAttributes setAircrafts(Map<Integer, Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
        return this;
    }

    public FlightAttributes setEngineTypes(Map<Integer, String> engineTypes) {
        this.engineTypes = engineTypes;
        return this;
    }

    public FlightAttributes setTransfers(Map<Integer, Transfer> transfers) {
        this.transfers = transfers;
        return this;
    }
}
