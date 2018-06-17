package sk.upjs.ics.diplomovka.data.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import sk.upjs.ics.diplomovka.data.Attribute;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.flights.*;
import sk.upjs.ics.diplomovka.data.models.data.FlightDataModel;
import sk.upjs.ics.diplomovka.data.models.data.closureconditions.ClosureConditionDataModel;
import sk.upjs.ics.diplomovka.data.models.data.disruptions.DisruptionDataModel;
import sk.upjs.ics.diplomovka.data.stands.DistancesMatrix;
import sk.upjs.ics.diplomovka.data.stands.Stand;
import sk.upjs.ics.diplomovka.data.stands.StandAttributes;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sk.upjs.ics.diplomovka.disruption.Disruption;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class DataParser {

    private FlightAttributes flightAttributes;
    private StandAttributes standAttributes;
    private StandsStorage standsStorage;
    private FlightStorage flightStorage;

    public GeneralStorage parseDataFromJsons(String categoriesFile, String aircraftsFile, String engineTypesFile, String transfersFile,
                                             String gatesFile, String gateDistancesFile, String standDistancesFile,
                                             String standsFile, String flightsFile) {
        parseFlightAttributes(categoriesFile, aircraftsFile, engineTypesFile, transfersFile);
        parseStandAttributes(gatesFile, gateDistancesFile, standDistancesFile);
        parseStands(standsFile);
        parseFlights(flightsFile);
        int startTime = flightStorage.getSortedFlights().get(0).getStart();
        return new GeneralStorage(flightStorage, standsStorage, startTime);
    }

    private void parseFlightAttributes(String categoriesFile, String aircraftsFile, String engineTypesFile, String transfersFile) {
        flightAttributes = new FlightAttributes()
                .setCategories(parseStringAttribute(categoriesFile))
                .setEngineTypes(parseStringAttribute(engineTypesFile))
                .setAircrafts(parseObjectAttribute(aircraftsFile, Aircraft[].class))
                .setTransfers(parseObjectAttribute(transfersFile, Transfer[].class));
    }

    private void parseStandAttributes(String gatesFile, String gateDistancesFile, String standDistancesFile) {
        standAttributes = new StandAttributes()
                .setGates(parseStringAttribute(gatesFile))
                .setGateDistances(parseDistances(gateDistancesFile))
                .setStandsDistances(parseDistances(standDistancesFile));
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

    private DistancesMatrix parseDistances(String distancesFile) {
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

    private <T> T[] parseObjects(String fileName, Class<T[]> type, Gson gson) {
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            System.err.println("Input file " + fileName + " not found.");
        }
        return gson.fromJson(reader, type);
    }

    private <T> T[] parseObjects(String fileName, Class<T[]> type) {
        return parseObjects(fileName, type, new Gson());
    }

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

    public List<Disruption> parseDisruptions(String disruptionsFile, GeneralStorage storage) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DisruptionDataModel.class, new DisruptionDeserializer());
        gsonBuilder.registerTypeAdapter(ClosureConditionDataModel.class, new ClosureConditionDeserializer());
        Gson gson = gsonBuilder.create();

        DisruptionDataModel[] disruptions = parseObjects(disruptionsFile, DisruptionDataModel[].class, gson);

        List<Disruption> disruptionList = new ArrayList<>();
        for(DisruptionDataModel model: disruptions) {
            disruptionList.add(Types.getDisruptionFromModel(model, storage));
        }

        return disruptionList;
    }
}
