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

    public sk.upjs.ics.diplomovka.data.GeneralStorage parseDataFromJsons(String categoriesFile, String aircraftsFile, String engineTypesFile,
                                                                         String gatesFile, String standsFile, String flightsFile) {
        parseFlightAttributes(categoriesFile, aircraftsFile, engineTypesFile);
        parseStandAttributes(gatesFile);
        parseStands(standsFile);
        parseFlights(flightsFile);
        return new GeneralStorage(flightStorage, standsStorage, 0);
    }

    private void parseFlightAttributes(String categoriesFile, String aircraftsFile, String engineTypesFile) {
        flightAttributes = new FlightAttributes()
                .setCategories(parseAttribute(categoriesFile))
                .setEngineTypes(parseAttribute(engineTypesFile))
                .setAircrafts(parseAircrafts(aircraftsFile));
    }

    private Map<Integer, Aircraft> parseAircrafts(String aircraftsFile) {
        Aircraft[] aircrafts = parseObjects(aircraftsFile, Aircraft.class);

        Map<Integer, Aircraft> aircraftMap = new HashMap<>();
        for (Aircraft aircraft : aircrafts) {
            aircraftMap.put(aircraft.getId(), aircraft);
        }

        return aircraftMap;
    }

    private void parseStandAttributes(String gatesFile) {
        standAttributes = new StandAttributes()
                .setGates(parseAttribute(gatesFile));
    }

    private void parseStands(String standsFile) {
        Stand[] stands = parseObjects(standsFile, Stand.class);

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
        FlightDataModel[] flights = parseObjects(flightsFile, FlightDataModel.class);

        Map<Integer, Flight> flightsMap = new HashMap<>();

        for (FlightDataModel flight : flights) {
            flightsMap.put(flight.getId(), new Flight(flight, flightAttributes));
        }

        flightStorage = new FlightStorage(flightsMap, flightAttributes);
    }

    private Map<Integer, String> parseAttribute(String attributeFile) {
        FlightAttribute[] attributes = parseObjects(attributeFile, FlightAttribute.class);

        Map<Integer, String> attributeMap = new HashMap<>();
        for (FlightAttribute attribute : attributes) {
            attributeMap.put(attribute.id, attribute.name);
        }

        return attributeMap;
    }

    private <T> T[] parseObjects(String fileName, Class<T> type) {
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
