package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Aircraft;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FullArrival;
import sk.upjs.ics.diplomovka.data.flights.FullDeparture;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FlightCsvParser {

    private static String SEPARATOR = ";";
    private Map<String, Aircraft> aircrafts = new HashMap<>();
    private Map<String, AircraftStand> gatesToStands = new HashMap<>();
    private List<AircraftStand> stands = new ArrayList<>();

    public FlightCsvParser(File aircraftFile, File standsFile) throws IOException {
        parseAircrafts(aircraftFile);
    }

    public List<AircraftStand> parseStands(File standsFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(standsFile));
        String line = "";

        while ((line = reader.readLine()) != null) {
            AircraftStand stand = parseStand(line);
            stands.add(stand);
            for (String gate : stand.getGates()) {
                gatesToStands.put(gate, stand);
            }
        }

        return stands;
    }

    private AircraftStand parseStand(String standString) {
        String[] standArray = standString.split(SEPARATOR);
        for (String s : standArray) {
            s.trim();
        }

        int id = Integer.parseInt(standArray[0]);
        double maxWingspan = Integer.parseInt(standArray[2]);
        String[] gateArray = standArray[1].split(", ");

        return new AircraftStand(id, maxWingspan, Flight.FlightCategory.SCHENGEN, null, Arrays.asList(gateArray)); // TODO: Schengen & null
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
        for (String s : aircraftArray) {
            s.trim();
        }

        String wingspan = aircraftArray[1];
        wingspan = wingspan.replace(",", ".");
        return new Aircraft(aircraftArray[0].trim(), Double.parseDouble(wingspan));
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
        for (String s : flightArray) {
            s.trim();
        }

        FullArrival arrival = new FullArrival();

        arrival.setScheduled(parseTime(flightArray[0]));
        arrival.setActual(parseTime(flightArray[1]));
        arrival.setFrom(flightArray[2]);
        arrival.setTerminal(Integer.parseInt(flightArray[3]));
        arrival.setGate(flightArray[4]);
        arrival.setBaggageClaim(Integer.parseInt(flightArray[5]));
        arrival.setStatus(flightArray[6]);
        arrival.setFlightNo(flightArray[7]);
        arrival.setAircraft(aircrafts.get(flightArray[8]));

        return arrival;
    }

    private FullDeparture parseDeparture(String departureString) {
        String[] flightArray = departureString.split(SEPARATOR);
        for (String s : flightArray) {
            s.trim();
        }

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

    public int getNoOfStands() {
        return stands.size();
    }

    public int getStandNo(String gate) {
        return gatesToStands.get(gate).getId();
    }
}
