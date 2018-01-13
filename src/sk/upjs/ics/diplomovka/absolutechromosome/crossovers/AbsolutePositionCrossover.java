package sk.upjs.ics.diplomovka.absolutechromosome.crossovers;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.FlightPosition;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome.EMPTY_GENE;

public class AbsolutePositionCrossover extends CrossoverBase {
    public AbsolutePositionCrossover(double probability) {
        super(probability);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        if (Math.random() > probability)
            return Collections.emptyList();

        AbsolutePositionChromosome c1 = (AbsolutePositionChromosome) chromosome1;
        AbsolutePositionChromosome c2 = (AbsolutePositionChromosome) chromosome2;
        return doAbsoluteCrossover(c1, c2);
    }

    private List<Chromosome> doAbsoluteCrossover(AbsolutePositionChromosome chromosome1, AbsolutePositionChromosome chromosome2) {
        int gate = Utils.randomInt(chromosome1.getNoOfGates());
        int maxLength = Math.max(chromosome1.getNoOfFlights(gate), chromosome2.getNoOfFlights(gate));
        int queueStart = Utils.randomInt(maxLength - 1); // -1 so there is at least one flight after the start
        int queueLength = Utils.randomInt(1, maxLength - queueStart); // 1 would mean SwapBetweenGates mutations

        AbsolutePositionChromosome c1 = chromosome1.copy();
        AbsolutePositionChromosome c2 = chromosome2.copy();

        List<Chromosome> result = new ArrayList<>();
        if (updateChromosome(c1, chromosome2, queueStart, queueLength, gate))
            result.add(c1);
        if (updateChromosome(c2, chromosome1, queueStart, queueLength, gate))
            result.add(c2);

        return result;
    }

    private boolean updateChromosome(AbsolutePositionChromosome updatedChromosome, AbsolutePositionChromosome secondChromosome, int queueStart, int queueLength, int gate) {
        for (int f = queueStart + queueLength - 1; f >= queueStart; f--) {
            int flightU = updatedChromosome.getGene(gate, f);
            int flightS = secondChromosome.getGene(gate, f);

            if (flightU == EMPTY_GENE && flightS == EMPTY_GENE) {
                throw new IllegalStateException("at least one flight must be 0 or positive");
            }

            if (flightU == EMPTY_GENE) { // we'll be getting new flight for empty position
                FlightPosition flightSPosition = updatedChromosome.findPosition(flightS);

                if (!updatedChromosome.checkFlightFeasibility(flightS, gate))
                    return false;

                updatedChromosome.removeFlightFromGenes(flightSPosition.getGate(), flightSPosition.getFlight()); // we remove new flight from where it is now
                updatedChromosome.addNextFlight(gate, flightS); // we add new flight to the position that is being updated

            } else if (flightS == EMPTY_GENE) { // we'll be removing flight on the position and must add it somewhere else - to the next gate
                int newGate = (gate + 1) % updatedChromosome.getNoOfGates();

                if (!updatedChromosome.checkFlightFeasibility(flightU, newGate))
                    return false;

                updatedChromosome.removeFlightFromGenes(gate, f); // we remove the flight
                updatedChromosome.addNextFlight(newGate, flightU); // we add removed flight to the next gate

            } else { // we'll be getting new flight for the position, so we must put old flight to the old position of new flight
                FlightPosition flightSPosition = updatedChromosome.findPosition(flightS);

                if (!updatedChromosome.checkFlightFeasibility(flightS, gate) || !updatedChromosome.checkFlightFeasibility(flightU, flightSPosition.getGate()))
                    return false;

                updatedChromosome.setGene(gate, f, flightS);
                updatedChromosome.setGene(flightSPosition.getGate(), flightSPosition.getFlight(), flightU); // TODO replace chronologically
            }
        }
        return true;
    }
}
