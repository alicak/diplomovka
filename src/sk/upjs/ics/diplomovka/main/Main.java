package sk.upjs.ics.diplomovka.main;

import sk.upjs.ics.diplomovka.algorithm.Algorithm;
import sk.upjs.ics.diplomovka.base.*;
import sk.upjs.ics.diplomovka.data.FlightCsvParser;
import sk.upjs.ics.diplomovka.data.flights.Flight;
import sk.upjs.ics.diplomovka.data.flights.FullArrival;
import sk.upjs.ics.diplomovka.data.flights.FullDeparture;
import sk.upjs.ics.diplomovka.model1.crossovers.RelativePositionCrossover;
import sk.upjs.ics.diplomovka.model1.mutations.AbsolutePositionMutation;
import sk.upjs.ics.diplomovka.model1.fitness.TimeDiffFitness;
import sk.upjs.ics.diplomovka.operators.selection.RouletteWheelSelection;
import sk.upjs.ics.diplomovka.operators.termination.IterationsTermination;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        PopulationBase population = null; // TODO
        List<Flight> flights = processFlights(
                new File("aircrafts.csv"),
                new File("arrivals.csv"),
                new File("departures.csv")); // TODO: add files to folder
        TimeDiffFitness fitnessFunction = new TimeDiffFitness(flights);
        CrossoverBase crossover = new RelativePositionCrossover(0.8);
        MutationBase mutation = new AbsolutePositionMutation(0.05);
        SelectionBase selection = new RouletteWheelSelection();
        TerminationBase termination = new IterationsTermination(100);

        AlgorithmBase algorithm = new Algorithm(population, fitnessFunction, crossover, mutation, selection, termination);
        PopulationBase finalPopulation = algorithm.evolve();
        Chromosome result = finalPopulation.bestChromosome();

        System.out.println(result.toString());
        PrintWriter writer = new PrintWriter(new File("results.txt"));
        writer.append(result.toString());
        writer.append("No of iterations: " + Integer.toString(termination.getNoOfIterations()) + "\n\n");
        writer.close();
    }

    private static List<Flight> processFlights(File aircraftFile, File arrivalsFile, File departuresFile) throws IOException {
        FlightCsvParser parser = new FlightCsvParser(aircraftFile);
        List<FullArrival> arrivalsFull = parser.parseArrivals(arrivalsFile);
        List<FullDeparture> departuresFull = parser.parseDepartures(departuresFile);

        List<Flight> flights = new ArrayList<>();

        for (FullArrival a : arrivalsFull) {
            flights.add(FullArrival.toFlight(a));
        }

        for (FullDeparture d: departuresFull) {
            flights.add(FullDeparture.toFlight(d));
        }

        Collections.sort(flights);

        return flights;
    }
}
