package sk.upjs.ics.diplomovka.evaluation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import sk.upjs.ics.diplomovka.data.models.view.ReassignmentStatistics;
import sk.upjs.ics.diplomovka.main.MainAlgorithm;
import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * runs reassignment calculation for all test scenarios and saves the results
 */
public class EvaluatorMain {
    public static final File SCENARIOS_FOLDER = new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER);
    private static final String RESULTS_FOLDER = "data/results/";

    public static void main(String[] args) throws FileNotFoundException {
        File[] scenariosFiles = SCENARIOS_FOLDER.listFiles();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<ReassignmentInfo> reassignmentInfoList = new LinkedList<>();

        for (File scenarioFile : scenariosFiles) {
            String name = scenarioFile.getName();
            if (!name.contains("_scenario_")) // in case there is some other file in the directory
                continue;
            ReassignmentStatistics statistics = runReassignment(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + name);

            reassignmentInfoList.add(new ReassignmentInfo(statistics, name));

            PrintWriter pw = new PrintWriter(new File(RESULTS_FOLDER + name));
            pw.write(gson.toJson(statistics));
            pw.close();
        }

        PrintWriter pw = new PrintWriter(new File(RESULTS_FOLDER + "results.json"));
        pw.write(gson.toJson(reassignmentInfoList));
        pw.close();
    }

    public static ReassignmentStatistics runReassignment(String disruptionsFile) {
        MainAlgorithm algorithm = new MainAlgorithm(disruptionsFile);

        // all reassignments will use the same parameters
        ReassignmentParameters parameters = new ReassignmentParameters(true, true, true, true, true)
                .setStartTime(algorithm.getStartTime())
                .setReassignmentsWeight(60)
                .setPassengersWeight(0.1)
                .setPriorityWeight(1)
                .setTimeWeight(1)
                .setWalkingWeight(10);

        algorithm.applyDisruptions();
        algorithm.calculateNewAssignment(parameters);
        return algorithm.getReassignmentStatistics();
    }
}
