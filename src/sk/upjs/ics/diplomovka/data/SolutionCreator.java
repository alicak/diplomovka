package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightInfo;
import sk.upjs.ics.diplomovka.data.stands.StandToGateMapper;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SolutionCreator {

    public static List<FlightInfo> createSolutionFromChromosome(Chromosome chromosome, GeneralStorage storage) {
        List<FlightInfo> flightInfos = new ArrayList<>();

        StandsStorage standsStorage = storage.getStandsStorage();

        StandToGateMapper standToGateMapper = new StandToGateMapper(storage);
        Map<Integer, Integer> flightsToGates = standToGateMapper.mapFlightsToGates(chromosome);

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                Flight flight = storage.getFlightStorage().getFlight(chromosome.getGene(g, f));

                FlightInfo flightInfo = new FlightInfo()
                        .setActualStart(chromosome.getCurrentFlightStart(g, f) + flight.getTurnaroundTime())
                        .setAssignmentDelay(chromosome.getCurrentFlightStart(g, f) - flight.getStart())
                        .setDelay(flight.getDelay())
                        .setDestination(flight.getDestination())
                        .setOriginalGate(standsStorage.getGateById(flight.getOriginalGateId()))
                        .setOriginalStart(flight.getOriginalStart())
                        .setGate(standsStorage.getGateById(flightsToGates.get(flight.getId())));

                flightInfos.add(flightInfo);
            }
        }
        return flightInfos;
    }

}
