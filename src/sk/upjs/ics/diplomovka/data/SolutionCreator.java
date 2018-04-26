package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FlightInfo;

import java.util.ArrayList;
import java.util.List;

public class SolutionCreator {

    public static List<FlightInfo> createSolutionFromChromosome(AbsolutePositionChromosome chromosome, GeneralStorage storage) {
        List<FlightInfo> flightInfos = new ArrayList<>();

        for (int g = 0; g < chromosome.getNoOfGates(); g++) {
            for (int f = 0; f < chromosome.getNoOfFlights(g); f++) {
                Flight flight = storage.getFlightStorage().getFlightByNumber(chromosome.getGene(g, f));

                FlightInfo flightInfo = new FlightInfo()
                        .setActualStart(chromosome.getCurrentFlightStart(g, f) + flight.getTurnaroundTime())
                        .setAssignmentDelay(chromosome.getCurrentFlightStart(g, f) - flight.getStart())
                        .setDelay(flight.getDelay())
                        .setDestination(flight.getDestination())
                        .setOriginalGate(flight.getOriginalGate())
                        .setOriginalStart(flight.getOriginalStart());

                String gate = flight.getOriginalGate();

                if (flight.getOriginalStandId() != storage.getStandsStorage().getStandByNumber(g).getId()) {
                    List<String> gatesForStand = storage.getStandsStorage().getStandByNumber(g).getGates();
                    gate = gatesForStand.get(0);
                }

                flightInfo.setGate(gate);
                flightInfos.add(flightInfo);
            }
        }
        return flightInfos;
    }

}
