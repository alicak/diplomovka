package sk.upjs.ics.diplomovka.data.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import sk.upjs.ics.diplomovka.data.models.data.*;
import sk.upjs.ics.diplomovka.data.models.data.closureconditions.ClosureConditionDataModel;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.DisruptionDataModel;
import sk.upjs.ics.diplomovka.disruption.Disruption;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.flights.FlightAttributes;
import sk.upjs.ics.diplomovka.storage.flights.FlightStorage;
import sk.upjs.ics.diplomovka.storage.stands.DistancesMatrix;
import sk.upjs.ics.diplomovka.storage.stands.Stand;
import sk.upjs.ics.diplomovka.storage.stands.StandAttributes;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParser {

    private FlightAttributes flightAttributes;
    private StandAttributes standAttributes;
    private StandsStorage standsStorage;
    private FlightStorage flightStorage;

    public GeneralStorage parseDataFromJsons(File categoriesFile, File aircraftsFile, File engineTypesFile, File transfersFile,
                                             File gatesFile, File gateDistancesFile, File standDistancesFile,
                                             File standsFile, File flightsFile) {
        parseFlightAttributes(categoriesFile, aircraftsFile, engineTypesFile, transfersFile);
        parseStandAttributes(gatesFile, gateDistancesFile, standDistancesFile);
        parseStands(standsFile);
        parseFlights(flightsFile);
        return new GeneralStorage(flightStorage, standsStorage, 0);
    }

    private void parseFlightAttributes(File categoriesFile, File aircraftsFile, File engineTypesFile, File transfersFile) {
        flightAttributes = new FlightAttributes()
                .setCategories(parseStringFlightAttribute(categoriesFile))
                .setEngineTypes(parseStringFlightAttribute(engineTypesFile))
                .setAircrafts(parseObjectAttribute(aircraftsFile, Aircraft[].class))
                .setTransfers(parseObjectAttribute(transfersFile, Transfer[].class));
    }

    private void parseStandAttributes(File gatesFile, File gateDistancesFile, File standDistancesFile) {
        standAttributes = new StandAttributes()
                .setGates(parseStringFlightAttribute(gatesFile))
                .setGateDistances(parseDistances(gateDistancesFile))
                .setStandsDistances(parseDistances(standDistancesFile));
    }

    private void parseStands(File standsFile) {
        StandDataModel[] stands = parseObjects(standsFile, StandDataModel[].class);

        Map<Integer, Stand> standsMap = new HashMap<>();
        Map<Integer, Stand> gatesToStands = new HashMap<>();

        for (StandDataModel standDataModel : stands) {
            Stand stand = new Stand(standDataModel);
            standsMap.put(stand.getId(), stand);
            for (Integer gate : stand.getGates()) {
                gatesToStands.put(gate, stand);
            }
        }

        standsStorage = new StandsStorage(standsMap, gatesToStands, standAttributes);
    }

    private void parseFlights(File flightsFile) {
        FlightDataModel[] flights = parseObjects(flightsFile, FlightDataModel[].class);

        Map<Integer, Flight> flightsMap = new HashMap<>();

        for (FlightDataModel flight : flights) {
            flightsMap.put(flight.getId(), new Flight(flight, flightAttributes));
        }

        flightStorage = new FlightStorage(flightsMap, flightAttributes);
    }

    private Map<Integer, String> parseStringFlightAttribute(File attributesFile) {
        FlightAttribute[] attributes = parseObjects(attributesFile, FlightAttribute[].class);

        Map<Integer, String> attributeMap = new HashMap<>();
        for (FlightAttribute attribute : attributes) {
            attributeMap.put(attribute.id, attribute.name);
        }

        return attributeMap;
    }

    private <T extends Attribute> Map<Integer, T> parseObjectAttribute(File attributesFile, Class<T[]> type) {
        T[] attributes = parseObjects(attributesFile, type);

        Map<Integer, T> map = new HashMap<>();
        for (T attribute : attributes) {
            map.put(attribute.getId(), attribute);
        }

        return map;
    }

    private DistancesMatrix parseDistances(File distancesFile) {
        Map<Integer, Map<Integer, Double>> allDistances = new HashMap<>();

        DistancesList[] distancesLists = parseObjects(distancesFile, DistancesList[].class);

        for (DistancesList distancesList : distancesLists) {
            Map<Integer, Double> distances = new HashMap<>();
            for (Distance distance : distancesList.distances) {
                distances.put(distance.id, distance.distance);
            }
            distances.put(distancesList.id, 0.0);
            allDistances.put(distancesList.id, distances);
        }

        return new DistancesMatrix(allDistances);
    }

    private <T> T[] parseObjects(File file, Class<T[]> type, Gson gson) {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file " + file + " not found.");
        }
        return gson.fromJson(reader, type);
    }

    private <T> T[] parseObjects(File file, Class<T[]> type) {
        return parseObjects(file, type, new Gson());
    }

    // helper classes for flight and stand attribute parsing:

    private class FlightAttribute {
        private int id;
        private String name;
    }

    private class DistancesList {
        private int id;
        private List<Distance> distances;
    }

    private class Distance {
        private int id;
        private double distance;
    }

    public List<Disruption> parseDisruptions(File disruptionsFile, GeneralStorage storage) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DisruptionDataModel.class, new DisruptionDeserializer());
        gsonBuilder.registerTypeAdapter(ClosureConditionDataModel.class, new ClosureConditionDeserializer());
        Gson gson = gsonBuilder.create();

        DisruptionDataModel[] disruptions = parseObjects(disruptionsFile, DisruptionDataModel[].class, gson);

        List<Disruption> disruptionList = new ArrayList<>();
        for (DisruptionDataModel model : disruptions) {
            disruptionList.add(Types.getDisruptionFromModel(model, storage));
        }

        return disruptionList;
    }
}
