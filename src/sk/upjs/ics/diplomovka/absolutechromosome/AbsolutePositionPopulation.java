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
        if (chromosomes.isEmpty())
            return;

        List<AbsolutePositionChromosome> absChromosomes = new ArrayList<>();
        for(Chromosome ch: chromosomes) {
            absChromosomes.add((AbsolutePositionChromosome) (ch));
        }

        for (int g = 0; g < chromosomes.get(0).getNoOfGates(); g++) {
            for (AbsolutePositionChromosome ch : absChromosomes) {
                List<StandClosure> closuresForStand = standsStorage.getClosuresForStand(g);

                for (StandClosure closure : closuresForStand) {

                    for (int f = 0; f < ch.getNoOfFlights(g); f++) {
                        int currentEnd = ch.getCurrentFlightEnd(g,f);

                        if (currentEnd >= closure.getStart()) {
                            int currentStart = ch.getCurrentFlightStart(g,f);
                            int delay = closure.getLength() + (closure.getStart() - currentStart); // we also have to consider the gap between planned start and closure

                            for(int lateFlight = f; lateFlight < ch.getNoOfFlights(g); lateFlight++){
                                ch.incrementCurrentStartAndEnd(g,lateFlight,delay);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

}
