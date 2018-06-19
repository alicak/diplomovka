package sk.upjs.ics.diplomovka.evaluation;

import com.google.gson.Gson;
import sk.upjs.ics.diplomovka.data.models.view.ReassignmentStatistics;
import sk.upjs.ics.diplomovka.main.MainAlgorithm;
import sk.upjs.ics.diplomovka.ui.models.ReassignmentParameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class EvaluatorMain {
    private static final File SCENARIOS_FOLDER = new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER);
    private static final String RESULTS_FOLDER = "data/results/";

    public static void main(String[] args) throws FileNotFoundException {
        File[] scenariosFiles = SCENARIOS_FOLDER.listFiles();
        Gson gson = new Gson();

        for (File scenarioFile : scenariosFiles) {
            String name = scenarioFile.getName();
            if (!name.contains("_scenario_")) // in case there is some other file in the directory
                break;
            ReassignmentStatistics statistics = runReassignment(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + name);

            PrintWriter pw = new PrintWriter(new File(RESULTS_FOLDER + name));
            pw.write(gson.toJson(statistics));
            pw.close();
        }
    }

    private static ReassignmentStatistics runReassignment(String disruptionsFile) {
        MainAlgorithm algorithm = new MainAlgorithm(disruptionsFile);

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
