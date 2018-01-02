package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightCsvParser {

    private static String SEPARATOR = ";";
    private Map<String, Aircraft> aircrafts = new HashMap<>();

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

    private Aircraft parseAircraft(String aircraftString) {
        String[] aircraftArray = aircraftString.split(SEPARATOR);
        String wingspan = aircraftArray[1];
        wingspan.replace(",", ".");
        return new Aircraft(aircraftArray[0], Double.parseDouble(wingspan));
    }

    public List<FullArrival> parseArrivals(File arrivalsFile) throws IOException {
        List<FullArrival> flights = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(arrivalsFile));
        reader.readLine(); // first line are column headers
        String line = "";

        while ((line = reader.readLine()) != null) {
            flights.add(parseArrival(line));
        }

        return flights;
    }

    public List<FullDeparture> parseDepartures(File departuresFile) throws IOException {
        List<FullDeparture> flights = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(departuresFile));
        reader.readLine(); // first line are column headers
        String line = "";

        while ((line = reader.readLine()) != null) {
            flights.add(parseDeparture(line));
        }

        return flights;
    }


    private FullArrival parseArrival(String arrivalString) {
        String[] flightArray = arrivalString.split(SEPARATOR);

        FullArrival arrival = new FullArrival();

        arrival.setScheduled(parseTime(flightArray[0]));
        arrival.setActual(parseTime(flightArray[1]));
        arrival.setFrom(flightArray[2]);
        arrival.setTerminal(Integer.parseInt(flightArray[3]));
        arrival.setBaggageClaim(Integer.parseInt(flightArray[4]));
        arrival.setStatus(flightArray[5]);
        arrival.setFlightNo(flightArray[6]);
        arrival.setAircraft(aircrafts.get(flightArray[7]));

        return arrival;
    }

    private FullDeparture parseDeparture(String departureString) {
        String[] flightArray = departureString.split(SEPARATOR);

        FullDeparture departure = new FullDeparture();

        departure.setScheduled(parseTime(flightArray[0]));
        departure.setActual(parseTime(flightArray[1]));
        departure.setTo(flightArray[2]);
        departure.setTerminal(Integer.parseInt(flightArray[3]));
        departure.setGate(flightArray[4]);
        departure.setStatus(flightArray[5]);
        departure.setFlightNo(flightArray[6]);
        departure.setAircraft(aircrafts.get(flightArray[7]));

        return departure;
    }

    private int parseTime(String time) {
        String[] timeArray = time.split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);
        return hours * 60 + minutes;
    }

}
