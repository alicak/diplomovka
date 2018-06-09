package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.Aircraft;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FullFlight;
import sk.upjs.ics.diplomovka.data.stands.AircraftStand;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FlightCsvParser {

    private static String SEPARATOR = ";";
    private Map<String, Aircraft> aircrafts = new HashMap<>();
    private int gateId = 0;

    public FlightCsvParser(File aircraftFile) throws IOException {
        parseAircrafts(aircraftFile);
    }

    public StandsStorage parseStands(File standsFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(standsFile));
        String line = "";

        Map<Integer, AircraftStand> stands = new HashMap<>();
        Map<String, AircraftStand> gatesToStands = new HashMap<>();
        List<String> gates = new ArrayList<>();
        int id = 0;

        while ((line = reader.readLine()) != null) {
            AircraftStand stand = parseStand(line);
            stands.put(stand.getId(), stand);

            List<String> gatesForStand = parseGates(line);
            List<Integer> gateIdsForStand = new ArrayList<>();

            for (String gate : gatesForStand) {
                gatesToStands.put(gate, stand);
                gates.add(gate);

                gateIdsForStand.add(id);
                id++;
            }

            stand.setGates(gateIdsForStand);
        }

        return new StandsStorage(stands, gatesToStands, gates);
    }

    private AircraftStand parseStand(String standString) {
        String[] standArray = standString.split(SEPARATOR);
        for (String s : standArray) {
            s.trim();
        }

        int id = Integer.parseInt(standArray[0]);
        String wingspan = standArray[2];
        wingspan = wingspan.replace(",", ".");
        double maxWingspan = Double.parseDouble(wingspan);
        String[] gateArray = standArray[1].split(", ");

        return new AircraftStand(id, maxWingspan,
                Arrays.asList(Flight.FlightCategory.SCHENGEN),
                Arrays.asList(Aircraft.EngineType.JET, Aircraft.EngineType.TURBOPROP)); // TODO: Schengen & null
    }

    private List<String> parseGates(String standString) {
        String[] standArray = standString.split(SEPARATOR);
        for (String s : standArray) {
            s.trim();
        }

        return Arrays.asList(standArray[1].split(", "));
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
        Aircraft.EngineType engineType = Aircraft.EngineType.JET; // we set JET as default
        if (aircraftArray[2].equals("jet")) {
            engineType = Aircraft.EngineType.JET;
        } else if (aircraftArray[2].equals("turboprop")) {
            engineType = Aircraft.EngineType.TURBOPROP;
        }
        return new Aircraft(aircraftArray[0].trim(), Double.parseDouble(wingspan), engineType);
    }

    public List<FullFlight> parseDepartures(File departuresFile) throws IOException {
        List<FullFlight> flights = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(departuresFile));
        reader.readLine(); // first line are column headers
        String line = "";

        while ((line = reader.readLine()) != null) {
            flights.add(parseDeparture(line));
        }

        return flights;
    }

    private FullFlight parseDeparture(String departureString) {
        String[] flightArray = departureString.split(SEPARATOR);
        for (String s : flightArray) {
            s.trim();
        }

        FullFlight departure = new FullFlight();

        departure.setScheduled(parseTime(flightArray[0]));
        departure.setActual(parseTime(flightArray[1]));
        departure.setTo(flightArray[2]);
        departure.setTerminal(Integer.parseInt(flightArray[3]));
        departure.setGate(flightArray[4]);
        departure.setStatus(flightArray[5]);
        departure.setFlightNo(flightArray[6]);
        departure.setAircraft(aircrafts.get(flightArray[7]));
        departure.setTurnaroundTime(Integer.parseInt(flightArray[8]));
        departure.setNoOfPassengers(Integer.parseInt(flightArray[9]));
        // TODO: priority

        return departure;
    }

    private int parseTime(String time) {
        String[] timeArray = time.split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);
        return hours * 60 + minutes;
    }
}
