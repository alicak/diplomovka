package sk.upjs.ics.diplomovka.model2.crossovers;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.FlightPosition;
import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.base.CrossoverBase;
import sk.upjs.ics.diplomovka.utils.Utils;

import java.util.Arrays;
import java.util.List;

import static sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionChromosome.EMPTY_GENE;

public class AbsolutePositionCrossover extends CrossoverBase {
    public AbsolutePositionCrossover(double probability) {
        super(probability);
    }

    @Override
    public List<Chromosome> doCrossover(Chromosome chromosome1, Chromosome chromosome2) {
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

        updateChromosome(c1, c2, queueStart, queueLength, gate);
        updateChromosome(c2, c1, queueStart, queueLength, gate);

        return Arrays.asList(c1, c2);
    }

    private void updateChromosome(AbsolutePositionChromosome updatedChromosome, AbsolutePositionChromosome secondChromosome, int queueStart, int queueLength, int gate) {
        for (int f = queueStart; f < queueStart + queueLength; f++) {
            int comingFlight = updatedChromosome.getGene(gate, f);
            int leavingFlight = secondChromosome.getGene(gate, f);

            if (comingFlight == EMPTY_GENE && leavingFlight == EMPTY_GENE) {
                throw new IllegalStateException("at least one flight must be 0 or positive");
            }

            if (comingFlight == EMPTY_GENE) { // we are removing flight and must add it somewhere else - to the next gate
                updatedChromosome.removeFlightFromGenes(gate, f);
                updatedChromosome.addNextFlight((gate + 1) % updatedChromosome.getNoOfGates(), leavingFlight);
            } else if (leavingFlight == EMPTY_GENE) { // we are getting new flight
                updatedChromosome.addNextFlight(gate, comingFlight);
                FlightPosition comingFlightPosition = updatedChromosome.findPosition(comingFlight);
                updatedChromosome.removeFlightFromGenes(comingFlightPosition.getGate(), comingFlightPosition.getFlight());
            } else { // we are exchanging coming and leaving flight
                FlightPosition comingFlightPosition = updatedChromosome.findPosition(comingFlight);
                updatedChromosome.setGene(comingFlightPosition.getGate(), comingFlightPosition.getFlight(), leavingFlight); // TODO replace chronologically
            }
        }
    }
}
