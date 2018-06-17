package sk.upjs.ics.diplomovka.ui;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.crossovers.AbsolutePositionCrossover;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.CombinedFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.ReassignmentFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.StandsDistanceFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.basic.TimeDiffFitness;
import sk.upjs.ics.diplomovka.absolutechromosome.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.SolutionCreator;
import sk.upjs.ics.diplomovka.data.models.view.AssignmentStatistics;
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
import sk.upjs.ics.diplomovka.data.models.view.ReassignmentStatistics;
import sk.upjs.ics.diplomovka.data.parser.DataParser;
import sk.upjs.ics.diplomovka.disruption.Disruption;
import sk.upjs.ics.diplomovka.main.AssignmentCreator;
import sk.upjs.ics.diplomovka.main.PopulationCreator;
import sk.upjs.ics.diplomovka.selection.RankingSelection;
import sk.upjs.ics.diplomovka.termination.IterationsTermination;
import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private List<FlightViewModel> flights;
    private List<Disruption> disruptions;

    private GeneralStorage storage;

    private AbsolutePositionFeasibilityChecker feasibilityChecker;
    private AssignmentCreator assignmentCreator;

    private int generationSize = 30;
    private Chromosome originalAssignment;
    private Chromosome reassignment;
    private AbsolutePositionPopulation population;

    private CrossoverBase crossover = new AbsolutePositionCrossover(1);
    private MutationBase mutation = new AbsolutePositionMutation(0.1);
    private SelectionBase selection = new RankingSelection();
    private TerminationBase termination = new IterationsTermination(1000);
    CombinedFitness fitnessFunction;

    private String dataFolder = "data/";
    private String categoriesFile = dataFolder + "categories.json";
    private String aircraftsFile = dataFolder + "aircrafts.json";
    private String engineTypesFile = dataFolder + "engineTypes.json";
    private String transfersFile = dataFolder + "transfers.json";
    private String gatesFile = dataFolder + "gates.json";
    private String gateDistancesFile = dataFolder + "gateDistances.json";
    private String standDistancesFile = dataFolder + "standDistances.json";
    private String standsFile = dataFolder + "stands.json";
    private String flightsFile = dataFolder + "departures.json";
    private String disruptionsFile = dataFolder + "disruptionsExample.json";

    private ReassignmentStatistics reassignmentStatistics;

    public Main() {
        prepareData();

        feasibilityChecker = new AbsolutePositionFeasibilityChecker(storage);
        assignmentCreator = new AssignmentCreator(storage);
    }

    public void prepareData() {
        DataParser parser = new DataParser();

        storage = parser.parseDataFromJsons(categoriesFile, aircraftsFile, engineTypesFile, transfersFile,
                gatesFile, gateDistancesFile, standDistancesFile, standsFile, flightsFile);

        disruptions = parser.parseDisruptions(disruptionsFile, storage);
    }

    public void applyDisruptions() {
        preparePopulation();

        for (Disruption disruption : disruptions) {
            disruption.disruptAssignment(originalAssignment);
            for (Chromosome c : population.get()) {
                disruption.disruptAssignment(c);
            }
        }

        for (Disruption disruption : disruptions) {
            disruption.disruptStorage();
        }

        flights = null;
    }

    private void preparePopulation() {
        originalAssignment = assignmentCreator.createAbsoluteOriginalAssignment(feasibilityChecker);
        population = PopulationCreator.createAbsoluteInitialPopulation(generationSize, originalAssignment, feasibilityChecker, storage);
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

        if (assignmentDelayCount != 0)
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
        ReassignmentFitness reassignmentFitness = new ReassignmentFitness(storage, null);
        TimeDiffFitness timeDiffFitness = new TimeDiffFitness(storage, null);
        StandsDistanceFitness distanceFitness = new StandsDistanceFitness(storage, null);

        return new ReassignmentStatistics(
                (-1) * (int) reassignmentFitness.calculateNonWeightedFitness(reassignment),
                (-1) * (int) distanceFitness.calculateNonWeightedFitness(reassignment),
                (-1) * (int) timeDiffFitness.calculateNonWeightedFitness(reassignment));
    }

    public List<FlightViewModel> calculateNewAssignment(ReassignmentParameters parameters) {
        fitnessFunction = createFunction(parameters);

        for (Chromosome c : population.get()) {
            fitnessFunction.calculateAndSetFitness(c);
        }

        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination, storage);
        PopulationBase finalPopulation = null;
        try {
            finalPopulation = algorithm.evolve();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reassignment = finalPopulation.bestChromosome();
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
}
