package sk.upjs.ics.diplomovka.storage;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
import sk.upjs.ics.diplomovka.storage.flights.Flight;
import sk.upjs.ics.diplomovka.storage.stands.StandToGateMapper;
import sk.upjs.ics.diplomovka.storage.stands.StandsStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * creates sorted list of FlightViewModels from Chromosome
 */
public class SolutionCreator {

    public static List<FlightViewModel> createSolutionFromChromosome(Chromosome chromosome, GeneralStorage storage) {
        List<FlightViewModel> flightViewModels = new ArrayList<>();

        StandsStorage standsStorage = storage.getStandsStorage();

        StandToGateMapper standToGateMapper = new StandToGateMapper(storage);
        Map<Integer, Integer> flightsToGates = standToGateMapper.mapFlightsToGates(chromosome);

        for (int s = 0; s < chromosome.getNoOfStands(); s++) {
            for (int f = 0; f < chromosome.getNoOfFlights(s); f++) {
                Flight flight = storage.getFlightStorage().getFlight(chromosome.getGene(s, f));

                FlightViewModel flightViewModel = new FlightViewModel()
                        .setActualStart(chromosome.getCurrentFlightStart(s, f) + flight.getLength())
                        .setAssignmentDelay(chromosome.getCurrentFlightStart(s, f) - flight.getStart())
                        .setDelay(flight.getDelay())
                        .setCode(flight.getCode())
                        .setDestination(flight.getDestination())
                        .setOriginalGate(standsStorage.getGateById(flight.getOriginalGateId()))
                        .setStandId(standsStorage.getStandByNumber(s).getId())
                        .setOriginalStart(flight.getOriginalStart())
                        .setGate(standsStorage.getGateById(flightsToGates.get(flight.getId())));

                flightViewModels.add(flightViewModel);
            }
        }

        Collections.sort(flightViewModels);
        return flightViewModels;
    }

}
