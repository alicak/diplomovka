package sk.upjs.ics.diplomovka.absolutechromosome.crossovers;

import sk.upjs.ics.diplomovka.absolutechromosome.FlightPosition;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sk.upjs.ics.diplomovka.absolutechromosome.Chromosome.EMPTY_GENE;

public class AbsolutePositionCrossover extends CrossoverBase {
    public static final int MIN_QUEUE_LENGTH = 2;

    public AbsolutePositionCrossover(double probability) {
        super(probability);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        if (Math.random() > probability)
            return Collections.emptyList();

        return doAbsoluteCrossover(chromosome1, chromosome2);
    }

    private List<Chromosome> doAbsoluteCrossover(Chromosome chromosome1, Chromosome chromosome2) {
        if (Math.random() > 0.5)
            return crossWithSameStart(chromosome1, chromosome2);
        else
            return crossWithDifferentStart(chromosome1, chromosome2);
    }

    private List<Chromosome> crossWithSameStart(Chromosome chromosome1, Chromosome chromosome2) {
        int stand = Utils.randomInt(chromosome1.getNoOfStands());
        int maxLength = Math.max(chromosome1.getNoOfFlights(stand), chromosome2.getNoOfFlights(stand));

        if (maxLength < MIN_QUEUE_LENGTH) // there aren't enough flights at the stand
            return Collections.emptyList();

        int queueStart = Utils.randomInt(maxLength - 1); // -1 so there is at least one flight after the start
        int queueLength = Utils.randomInt(1, maxLength - queueStart); // 1 would mean SwapBetweenGates mutations

        Chromosome c1 = chromosome1.copy();
        Chromosome c2 = chromosome2.copy();

        List<Chromosome> result = new ArrayList<>();
        if (updateChromosome(c1, chromosome2, queueStart, queueLength, stand))
            result.add(c1);
        if (updateChromosome(c2, chromosome1, queueStart, queueLength, stand))
            result.add(c2);

        return result;
    }

    private List<Chromosome> crossWithDifferentStart(Chromosome chromosome1, Chromosome chromosome2) {
        int gate = Utils.randomInt(chromosome1.getNoOfStands());
        int length1 = chromosome1.getNoOfFlights(gate);
        int length2 = chromosome2.getNoOfFlights(gate);

        if (length1 < MIN_QUEUE_LENGTH || length2 < MIN_QUEUE_LENGTH) // there aren't enough flights at the gate
            return Collections.emptyList();

        int start1 = Utils.randomInt(length1 - MIN_QUEUE_LENGTH);
        int start2 = Utils.randomInt(length2 - MIN_QUEUE_LENGTH);
        int maxLength = Math.min(length1 - start1, length2 - start2);

        int queueLength = Utils.randomInt(1, maxLength); // 1 would mean SwapBetweenGates mutations

        Chromosome c1 = chromosome1.copy();
        Chromosome c2 = chromosome2.copy();

        List<Chromosome> result = new ArrayList<>();
        if (updateChromosome(c1, chromosome2, start1, queueLength, gate))
            result.add(c1);
        if (updateChromosome(c2, chromosome1, start2, queueLength, gate))
            result.add(c2);

        return result;
    }

    private boolean updateChromosome(Chromosome updatedChromosome, Chromosome secondChromosome, int queueStart, int queueLength, int gate) {
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

                updatedChromosome.removeFlightFromGenes(flightSPosition.getStand(), flightSPosition.getFlight()); // we remove new flight from where it is now
                updatedChromosome.addNextFlight(gate, flightS); // we add new flight to the position that is being updated

            } else if (flightS == EMPTY_GENE) { // we'll be removing flight on the position and must add it somewhere else - to the next gate
                int newGate = (gate + 1) % updatedChromosome.getNoOfStands();

                if (!updatedChromosome.checkFlightFeasibility(flightU, newGate))
                    return false;

                updatedChromosome.removeFlightFromGenes(gate, f); // we remove the flight
                updatedChromosome.addNextFlight(newGate, flightU); // we add removed flight to the next gate

            } else { // we'll be getting new flight for the position, so we must put old flight to the old position of new flight
                FlightPosition flightSPosition = updatedChromosome.findPosition(flightS);

                if (!updatedChromosome.checkFlightFeasibility(flightS, gate) || !updatedChromosome.checkFlightFeasibility(flightU, flightSPosition.getStand()))
                    return false;

                updatedChromosome.setGene(gate, f, flightS);
                updatedChromosome.setGene(flightSPosition.getStand(), flightSPosition.getFlight(), flightU); // TODO replace chronologically
            }
        }
        return true;
    }
}
