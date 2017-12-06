package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Aircraft;
import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightCsvParser {

    Map<String, Aircraft> aircrafts = new HashMap<>();

    public FlightCsvParser(File aircraftFile) throws IOException {
        parseAircrafts(aircraftFile);
    }

    private void parseAircrafts(File aircraftFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(aircraftFile));
        String line = "";

        while ((line = reader.readLine()) != null) {
            Aircraft aircraft = parseAircraft(line);
            aircrafts.put(aircraft.getName(), aircraft);
        }
    }

    public List<Flight> parseFlights(File flightsFile, Flight.FlightType flightType) throws IOException {
        List<Flight> flights = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(flightsFile));
        String line = "";

        while ((line = reader.readLine()) != null) {
            if (flightType == Flight.FlightType.ARRIVAL) {
                flights.add(parseArrival(line));
            } else if (flightType == Flight.FlightType.DEPARTURE) {
                flights.add(parseDeparture(line));
            }
        }

        return flights;
    }


    private Aircraft parseAircraft(String aircraftString) {
        return null;
    }

    private Flight parseArrival(String arrivalString) {
        return null;
    }

    private Flight parseDeparture(String departureString) {
        return null;
    }

    // column structure: Scheduled, Actual, To, Terminal, Gate, Status, Flight No, Aircraft, Aircraft full
    private Flight parseFlight(String flightString) {
        String[] flightArray = flightString.split(";");
        Flight flight = new Flight();
        flight.setId();

        // TODO add category to data
        if(flightArray[3].equals("1")) {
            flight.setCategory(Flight.FlightCategory.NON_SCHENGEN);
        }
        else if(flightArray[3].equals("2")) {
            flight.setCategory(Flight.FlightCategory.SCHENGEN);

        }

        flight.setAircraft(aircrafts.get(flightArray[8]));
        return null;
    }
}
