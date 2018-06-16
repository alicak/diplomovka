package sk.upjs.ics.diplomovka.ui;

import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionFeasibilityChecker;
import sk.upjs.ics.diplomovka.absolutechromosome.AbsolutePositionPopulation;
import sk.upjs.ics.diplomovka.absolutechromosome.Chromosome;
import sk.upjs.ics.diplomovka.absolutechromosome.fitness.combined.AbsoluteTimeDiffAndReassignmentFitness;
import sk.upjs.ics.diplomovka.data.FitnessFunctionWeights;
import sk.upjs.ics.diplomovka.data.GeneralStorage;
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
import sk.upjs.ics.diplomovka.data.parser.DataParser;
import sk.upjs.ics.diplomovka.disruption.Disruption;
import sk.upjs.ics.diplomovka.main.AssignmentCreator;
import sk.upjs.ics.diplomovka.main.PopulationCreator;
import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
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
        FitnessFunctionWeights weights = new FitnessFunctionWeights(parameters);

        // TODO

        return Collections.emptyList(); // TODO
    }
}
