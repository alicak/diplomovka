package sk.upjs.ics.diplomovka.absolutechromosome.fitness;

import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.base.FitnessFunctionBase;
import sk.upjs.ics.diplomovka.storage.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;

import java.util.List;

/**
 * sums results of multiple fitness functions
 */
public class CombinedFitness extends FitnessFunctionBase {

    private List<FitnessFunctionBase> functions;

    public CombinedFitness(GeneralStorage storage, List<FitnessFunctionBase> functions) {
        super(storage, new FitnessFunctionWeights()); // we don't need weights here, so we use class with no weights set up
        this.functions = functions;
    }

    @Override
    public double calculateFitness(Chromosome chromosome) {
        double result = 0;
        for (FitnessFunctionBase function : functions) {
            result += function.calculateFitness(chromosome);
        }
        return result;
    }

    @Override
    public double calculateNonWeightedFitness(Chromosome chromosome) {
        double result = 0;
        for (FitnessFunctionBase function : functions) {
            result += function.calculateNonWeightedFitness(chromosome);
        }
        return result;
    }
}
