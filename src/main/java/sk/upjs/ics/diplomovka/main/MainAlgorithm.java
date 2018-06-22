package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.absolutechromosome.AssignmentCreator;
import sk.upjs.ics.diplomovka.absolutechromosome.FeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.Population;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.PopulationCreator;
import sk.upjs.ics.diplomovka.absolutechromosome.crossovers.Crossover;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.CombinedFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.ReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.StandsDistanceFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.TimeDiffFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.Mutation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.models.view.AssignmentStatistics;
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
import sk.upjs.ics.diplomovka.data.models.view.ReassignmentStatistics;
import sk.upjs.ics.diplomovka.data.parser.DataParser;
import sk.upjs.ics.diplomovka.data.parser.Files;
import sk.upjs.ics.diplomovka.disruption.*;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.storage.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.storage.GeneralStorage;
import sk.upjs.ics.diplomovka.storage.SolutionCreator;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;
import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * prepares everything for the calculation, runs the actual algorithm and provides the results
 */
public class MainAlgorithm {

    private List<FlightViewModel> flights;
    private List<Disruption> disruptions;

    private GeneralStorage storage;

    private final String disruptionFile;

    private FeasibilityChecker feasibilityChecker;
    private AssignmentCreator assignmentCreator;

    private int generationSize = 30;
    private Chromosome originalAssignment;
    private Chromosome reassignment;
    private Population population;

    private CrossoverBase crossover = new Crossover(1);
    private MutationBase mutation = new Mutation(0.1);
    private SelectionBase selection = new RankingSelection();
    private TerminationBase termination = new IterationsTermination(2000);

    private CombinedFitness fitnessFunction;

    private ReassignmentFitness reassignmentFitness;
    private TimeDiffFitness timeDiffFitness;
    private StandsDistanceFitness distanceFitness;

    private ReassignmentStatistics reassignmentStatistics;

    public MainAlgorithm(String disruptionFile) {
        this.disruptionFile = disruptionFile;
        prepareData();

        feasibilityChecker = new FeasibilityChecker(storage);
        assignmentCreator = new AssignmentCreator(storage);

        // fitness functions for reassignment statistics (there are no weights)
        reassignmentFitness = new ReassignmentFitness(storage, null);
        timeDiffFitness = new TimeDiffFitness(storage, null);
        distanceFitness = new StandsDistanceFitness(storage, null);
    }

    public void prepareData() {
        DataParser parser = new DataParser();

        storage = parser.parseDataFromJsons(Files.CATEGORIES, Files.AIRCRAFTS, Files.ENGINE_TYPES, Files.TRANSFERS,
                Files.GATES, Files.GATE_DISTANCES, Files.STAND_DISTANCES, Files.STANDS, Files.FLIGHTS);

        disruptions = parser.parseDisruptions(disruptionFile, storage);
    }

    public void applyDisruptions() {
        preparePopulation();

        // it depends on the kind of disruption if storage needs to be disrupted before or after chromosomes are disrupted

        for (Disruption disruption : disruptions) {
            Class type = disruption.getClass();
            if (type == FlightAddedDisruption.class) {
                continue;
            }

            //disruption.disruptAssignment(originalAssignment);
            for (Chromosome c : population.get()) {
                disruption.disruptAssignment(c);
            }

            disruption.disruptStorage();
        }

        for (Disruption disruption : disruptions) {
            Class type = disruption.getClass();
            if (type != FlightAddedDisruption.class) {
                continue;
            }

            disruption.disruptStorage();

            //disruption.disruptAssignment(originalAssignment);
            for (Chromosome c : population.get()) {
                disruption.disruptAssignment(c);
            }
        }



        flights = null;
    }

    private void preparePopulation() {
        originalAssignment = assignmentCreator.createOriginalAssignment(feasibilityChecker);
        population = PopulationCreator.createInitialPopulation(generationSize, originalAssignment, feasibilityChecker, storage);
        population.add(originalAssignment);
    }

    private void refreshFlights() {
        flights = new ArrayList<>();
        storage.getFlightStorage().getSortedFlights().forEach(x -> flights.add(new FlightViewModel(x, storage)));
    }

    public List<FlightViewModel> getFlights() {
        if (flights == null) {
            refreshFlights();
        }
        return flights;
    }

    public List<Disruption> getDisruptions() {
        return disruptions;
    }

    public AssignmentStatistics calculateAssignmentStatistics(List<FlightViewModel> flights) {
        int regularDelaySum = 0;
        int regularDelayCount = 0;
        int regularDelayMax = 0;

        int assignmentDelaySum = 0;
        int assignmentDelayCount = 0;
        int assignmentDelayMax = 0;

        for (FlightViewModel flight : flights) {
            int delay = flight.getDelay();
            regularDelaySum += delay;
            if (flight.isDelayed())
                regularDelayCount++;
            regularDelayMax = Math.max(regularDelayMax, delay);

            int assignmentDelay = flight.getAssignmentDelay();
            assignmentDelaySum += assignmentDelay;
            if (flight.isAssignmentDelayed())
                assignmentDelayCount++;
            assignmentDelayMax = Math.max(assignmentDelayMax, assignmentDelay);
        }

        AssignmentStatistics statistics = new AssignmentStatistics()
                .setAssignmentDelayCount(assignmentDelayCount)
                .setAssignmentDelayMax(assignmentDelayMax)
                .setRegularDelayCount(regularDelayCount)
                .setRegularDelayMax(regularDelayMax);

        if (assignmentDelayCount != 0) // to prevent division by zero
            statistics.setAssignmentDelayAverage(assignmentDelaySum / assignmentDelayCount);
        else
            statistics.setAssignmentDelayAverage(0);

        if (regularDelayCount != 0)
            statistics.setRegularDelayAverage(regularDelaySum / regularDelayCount);
        else
            statistics.setRegularDelayAverage(0);

        return statistics;
    }

    private ReassignmentStatistics calculateReassignmentStatistics(Chromosome reassignment) {
        return new ReassignmentStatistics(fitnessFunction.calculateFitness(originalAssignment),
                fitnessFunction.calculateFitness(reassignment),
                (-1) * (int) reassignmentFitness.calculateNonWeightedFitness(reassignment),
                (-1) * (int) distanceFitness.calculateNonWeightedFitness(reassignment),
                (-1) * (int) timeDiffFitness.calculateNonWeightedFitness(reassignment)); // fitness is maximized, so we multiple by -1
    }

    public List<FlightViewModel> calculateNewAssignment(ReassignmentParameters parameters) {
        fitnessFunction = createFunction(parameters);

        for (Chromosome c : population.get()) {
            fitnessFunction.calculateAndSetFitness(c);
        }

        Collections.sort(population.get());

        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination, storage);
        try {
            algorithm.evolve();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reassignment = population.bestChromosome();
        reassignmentStatistics = calculateReassignmentStatistics(reassignment);

        return SolutionCreator.createSolutionFromChromosome(reassignment, storage);
    }

    public ReassignmentStatistics getReassignmentStatistics() {
        return reassignmentStatistics;
    }

    private CombinedFitness createFunction(ReassignmentParameters parameters) {
        FitnessFunctionWeights weights = new FitnessFunctionWeights(parameters);

        List<FitnessFunctionBase> functions = new LinkedList<>();
        if (parameters.optimizeReassignments()) {
            functions.add(new ReassignmentFitness(storage, weights));
        }
        if (parameters.optimizeTime()) {
            functions.add(new TimeDiffFitness(storage, weights));
        }
        if (parameters.optimizeWalking()) {
            functions.add(new StandsDistanceFitness(storage, weights));
        }

        // we set something by default
        if (functions.isEmpty()) {
            functions.add(new TimeDiffFitness(storage, weights));
        }

        return new CombinedFitness(storage, functions);
    }

    public int getStartTime() {
        return storage.getStartTime();
    }

    public double getAssignmentFitness() {
        return fitnessFunction.calculateFitness(originalAssignment);
    }

    public double getReassignmentFitness() {
        return fitnessFunction.calculateFitness(reassignment);
    }
}
