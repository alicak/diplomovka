package sk.upjs.ics.diplomovka.data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.stands.Stand;
import sk.upjs.ics.diplomovka.data.stands.StandAttributes;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class DataParser {

    private FlightAttributes flightAttributes;
    private StandAttributes standAttributes;
    private StandsStorage standsStorage;
    private FlightStorage flightStorage;
    private Gson gson = new Gson();

    public sk.upjs.ics.diplomovka.data.GeneralStorage parseDataFromJsons(String categoriesFile, String aircraftsFile, String engineTypesFile, String transfersFile,
                                                                         String gatesFile, String standsFile, String flightsFile) {
        parseFlightAttributes(categoriesFile, aircraftsFile, engineTypesFile, transfersFile);
        parseStandAttributes(gatesFile);
        parseStands(standsFile);
        parseFlights(flightsFile);
        return new GeneralStorage(flightStorage, standsStorage, 0);
    }

    private void parseFlightAttributes(String categoriesFile, String aircraftsFile, String engineTypesFile, String transfersFile) {
        flightAttributes = new FlightAttributes()
                .setCategories(parseStringAttribute(categoriesFile))
                .setEngineTypes(parseStringAttribute(engineTypesFile))
                .setAircrafts(parseObjectAttribute(aircraftsFile, Aircraft[].class))
                .setTransfers(parseObjectAttribute(transfersFile, Transfer[].class));
    }

    private void parseStandAttributes(String gatesFile) {
        standAttributes = new StandAttributes()
                .setGates(parseStringAttribute(gatesFile));
    }

    private void parseStands(String standsFile) {
        Stand[] stands = parseObjects(standsFile, Stand[].class);

        Map<Integer, Stand> standsMap = new HashMap<>();
        Map<Integer, Stand> gatesToStands = new HashMap<>();

        for (Stand stand : stands) {
            standsMap.put(stand.getId(), stand);
            for (Integer gate : stand.getGates()) {
                gatesToStands.put(gate, stand);
            }
        }

        standsStorage = new StandsStorage(standsMap, gatesToStands, standAttributes);
    }

    private void parseFlights(String flightsFile) {
        FlightDataModel[] flights = parseObjects(flightsFile, FlightDataModel[].class);

        Map<Integer, Flight> flightsMap = new HashMap<>();

        for (FlightDataModel flight : flights) {
            flightsMap.put(flight.getId(), new Flight(flight, flightAttributes));
        }

        flightStorage = new FlightStorage(flightsMap, flightAttributes);
    }

    private Map<Integer, String> parseStringAttribute(String attributesFile) {
        FlightAttribute[] attributes = parseObjects(attributesFile, FlightAttribute[].class);

        Map<Integer, String> attributeMap = new HashMap<>();
        for (FlightAttribute attribute : attributes) {
            attributeMap.put(attribute.id, attribute.name);
        }

        return attributeMap;
    }

    private <T extends Attribute> Map<Integer, T> parseObjectAttribute(String attributesFile, Class<T[]> type) {
        T[] attributes = parseObjects(attributesFile, type);

        Map<Integer, T> aircraftMap = new HashMap<>();
        for (T attribute : attributes) {
            aircraftMap.put(attribute.getId(), attribute);
        }

        return aircraftMap;
    }

    private <T> T[] parseObjects(String fileName, Class<T[]> type) {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("Input file " + fileName + " not found.");
        }
        return gson.fromJson(reader, type);
    }

    private class FlightAttribute {
        private int id;
        private String name;
    }
}
