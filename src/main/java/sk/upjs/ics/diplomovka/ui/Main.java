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
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
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
    private AbsolutePositionPopulation population;

    public Main() {
        prepareData();

        feasibilityChecker = new AbsolutePositionFeasibilityChecker(storage);
        assignmentCreator = new AssignmentCreator(storage);
    }

    public void prepareData() {
        DataParser parser = new DataParser();

        storage = parser.parseDataFromJsons("categories.json", "aircrafts.json",
                "engineTypes.json", "transfers.json", "gates.json", "gateDistances.json",
                "standDistances.json", "stands.json", "departures.json"); // TODO

        disruptions = parser.parseDisruptions("disruptionsExample.json", storage);
    }

    public void applyDisruptions() {
        preparePopulation();

        for (Disruption disruption : disruptions) {
            //disruption.disruptAssignment(originalAssignment);
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

    public List<FlightViewModel> calculateNewAssignment(ReassignmentParameters parameters) {
        CrossoverBase crossover = new AbsolutePositionCrossover(1);
        MutationBase mutation = new AbsolutePositionMutation(0.1);
        SelectionBase selection = new RankingSelection();
        TerminationBase termination = new IterationsTermination(1000);
        CombinedFitness fitnessFunction = createFunction(parameters);

        for (Chromosome c : population.get()) {
            fitnessFunction.calculateAndSetFitness(c);
        }

        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination, storage);
        PopulationBase finalPopulation = null;
        try {
            finalPopulation = algorithm.evolve();
        } catch (InterruptedException e) {
            e.printStackTrace(); // TODO
        }

        return SolutionCreator.createSolutionFromChromosome(finalPopulation.bestChromosome(), storage);
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
