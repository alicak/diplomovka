package sk.upjs.ics.diplomovka.absolutechromosome;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.PopulationBase;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandClosure;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

import java.util.ArrayList;
import java.util.List;

public class AbsolutePositionPopulation extends PopulationBase {

    private FlightStorage flightStorage;
    private StandsStorage standsStorage;

    public AbsolutePositionPopulation(List<Chromosome> generation, FlightStorage flightStorage, StandsStorage standsStorage) {
        super(generation);
        this.flightStorage = flightStorage;
        this.standsStorage = standsStorage;
    }

    public void prepareForFitnessCalculation(List<Chromosome> chromosomes) {
        for (Chromosome ch : chromosomes) {
            AbsolutePositionChromosome absCh = (AbsolutePositionChromosome) (ch);
            absCh.calculateCurrentFlightStarts(flightStorage);
        }
        applyStandClosures(chromosomes);
    }

    private void applyStandClosures(List<Chromosome> chromosomes) {
        List<AbsolutePositionChromosome> absChromosomes = new ArrayList<>();
        for (Chromosome ch : chromosomes) {
            absChromosomes.add((AbsolutePositionChromosome) (ch)); // TODO: somehow remove casting
        }

        for (int g = 0; g < standsStorage.getNoOfStands(); g++) {
            List<StandClosure> closuresForStand = standsStorage.getClosuresForStand(g);
            for (StandClosure closure : closuresForStand) {
                for (AbsolutePositionChromosome ch : absChromosomes) {
                    ch.applyStandClosure(closure, g);
                }
            }
        }


    }

}
