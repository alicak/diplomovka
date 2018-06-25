package sk.upjs.ics.diplomovka.evaluation;

import com.google.gson.Gson;
import sk.upjs.ics.diplomovka.data.models.view.ReassignmentStatistics;
import sk.upjs.ics.diplomovka.main.MainAlgorithm;
import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * runs reassignment calculation for all test scenarios and saves the results
 */
public class BenchmarkMain {
    private static final File SCENARIOS_FOLDER = new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER);
    private static final String RESULTS_FOLDER = "data/results/";

    public static void main(String[] args) throws FileNotFoundException {
        File[] scenariosFiles = SCENARIOS_FOLDER.listFiles();
        Gson gson = new Gson();
        List<Result> averageResults = new ArrayList<>();

        for (File scenarioFile : scenariosFiles) {

            String name = scenarioFile.getName();
            if (!name.contains("_scenario_")) // in case there is some other file in the directory
                break;

            for (int i = 0; i < 5; i++) {
                runReassignment(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + name));
            }

            int noOfRuns = 10;
            long time1 = 0;
            long time2 = 0;
            for (int i = 0; i < noOfRuns; i++) {
                Result result = runReassignment(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + name));
                time1 += result.runWithPopulationCreation;
                time2 += result.runWithoutPopulationCreation;
            }

            averageResults.add(new Result(name, time1/noOfRuns, time2/noOfRuns));
        }

        PrintWriter pw = new PrintWriter(new File(RESULTS_FOLDER + "benchmark.json"));
        pw.write(gson.toJson(averageResults));
        pw.close();
    }

    private static Result runReassignment(File disruptionsFile) {
        MainAlgorithm algorithm = new MainAlgorithm(disruptionsFile);

        // all reassignments will use the same parameters
        ReassignmentParameters parameters = new ReassignmentParameters(true, true, true, true, true)
                .setStartTime(algorithm.getStartTime())
                .setReassignmentsWeight(60)
                .setPassengersWeight(0.1)
                .setPriorityWeight(1)
                .setTimeWeight(1)
                .setWalkingWeight(10);

        long startTime1 = System.nanoTime();

        algorithm.applyDisruptions();

        long startTime2 = System.nanoTime();

        algorithm.calculateNewAssignment(parameters);

        long endTime = System.nanoTime();

        return new Result(startTime1, startTime2, endTime);
    }

}

class Result {
    public String disruptionsFile;
    public long runWithPopulationCreation;
    public long runWithoutPopulationCreation;

    public Result(long startTime1, long startTime2, long endTime) {
        this.runWithPopulationCreation = (endTime - startTime1) / 1000000;
        this.runWithoutPopulationCreation = (endTime - startTime2) / 1000000;
    }

    public Result(String disruptionsFile, long runWithPopulationCreation, long runWithoutPopulationCreation) {
        this.disruptionsFile = disruptionsFile;
        this.runWithPopulationCreation = runWithPopulationCreation;
        this.runWithoutPopulationCreation = runWithoutPopulationCreation;
    }
}