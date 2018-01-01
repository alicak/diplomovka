package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Aircraft;
import sk.upjs.ics.diplomovka.data.flights.Flight;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightCsvParser {

    private static String SEPARATOR = ";";
    private Map<String, Aircraft> aircrafts = new HashMap<>();
    private int id = 0;
    private static int BEFORE_ARRIVAL_SLOT = 30;
    private static int AFTER_ARRIVAL_SLOT = 30;
    private static int BEFORE_DEPARTURE_SLOT = 60;
    private static int AFTER_DEPARTURE_SLOT = 0;


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
        reader.readLine(); // first line are column headers
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
        String[] aircraftArray = aircraftString.split(SEPARATOR);
        String wingspan = aircraftArray[1];
        wingspan.replace(",", ".");
        return new Aircraft(aircraftArray[0], Double.parseDouble(wingspan));
    }

    // column structure: Scheduled, Actual, From, Terminal, Baggage claim, Status, Flight No, Aircraft, Aircraft full
    private Flight parseArrival(String arrivalString) {
        String[] flightArray = arrivalString.split(SEPARATOR);

        Flight flight = new Flight();
        flight.setId(++id);
        flight.setStart(Integer.parseInt(flightArray[ArrivalArray.SCHEDULED.ordinal()]) - BEFORE_ARRIVAL_SLOT);
        flight.setEnd(Integer.parseInt(flightArray[ArrivalArray.SCHEDULED.ordinal()]) + AFTER_ARRIVAL_SLOT);
        flight.setType(Flight.FlightType.DEPARTURE);

        // TODO add category to data
//        if(flightArray[ArrivalArray.TERMINAL.ordinal()].equals("1")) {
//            flight.setCategory(Flight.FlightCategory.NON_SCHENGEN);
//        }
//        else{
//            flight.setCategory(Flight.FlightCategory.SCHENGEN);
//        }

        flight.setAircraft(aircrafts.get(flightArray[ArrivalArray.AIRCRAFT.ordinal()]));
        flight.setTurnaroundTime(0); // TODO

        return flight;
    }

    // column structure: Scheduled, Actual, To, Terminal, Gate, Status, Flight No, Aircraft, Aircraft full
    private Flight parseDeparture(String departureString) {
        String[] flightArray = departureString.split(SEPARATOR);

        Flight flight = new Flight();
        flight.setId(++id);
        flight.setStart(Integer.parseInt(flightArray[DepartureArray.SCHEDULED.ordinal()]) - BEFORE_DEPARTURE_SLOT);
        flight.setEnd(Integer.parseInt(flightArray[DepartureArray.SCHEDULED.ordinal()]) + AFTER_DEPARTURE_SLOT);
        flight.setType(Flight.FlightType.DEPARTURE);

        // TODO add category to data
//        if(flightArray[DepartureArray.TERMINAL.ordinal()].equals("1")) {
//            flight.setCategory(Flight.FlightCategory.NON_SCHENGEN);
//        }
//        else{
//            flight.setCategory(Flight.FlightCategory.SCHENGEN);
//        }

        flight.setAircraft(aircrafts.get(flightArray[DepartureArray.AIRCRAFT.ordinal()]));
        flight.setTurnaroundTime(0); // TODO

        return flight;
    }

    private enum DepartureArray {
        SCHEDULED,
        ACTUAL,
        TO,
        TERMINAL,
        GATE,
        STATUS,
        FLIGHT_NO,
        AIRCRAFT,
        AIRCRAFT_FULL
    }

    private enum ArrivalArray {
        SCHEDULED,
        ACTUAL,
        FROM,
        TERMINAL,
        BAGGAGE_CLAIM,
        STATUS,
        FLIGHT_NO,
        AIRCRAFT,
        AIRCRAFT_FULL
    }


}
