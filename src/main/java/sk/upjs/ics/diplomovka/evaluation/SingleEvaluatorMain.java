package sk.upjs.ics.diplomovka.evaluation;

import com.google.gson.Gson;
import sk.upjs.ics.diplomovka.data.models.view.ReassignmentStatistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import static sk.upjs.ics.diplomovka.evaluation.EvaluatorMain.runReassignment;

public class SingleEvaluatorMain {

    private static final String RESULTS_FOLDER = "data/results/single/";


    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new Gson();

        List<File> scenariosFiles = new LinkedList<>();
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "easy_scenario_2.json"));
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "easy_scenario_3.json"));
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "medium_scenario_1.json"));
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "medium_scenario_3.json"));
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "hard_scenario_10.json"));
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "hard_scenario_7.json"));
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "extreme_scenario_4.json"));
        scenariosFiles.add(new File(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + "extreme_scenario_5.json"));

        for (File scenarioFile : scenariosFiles) {
            String name = scenarioFile.getName();
            List<ReassignmentStatistics> statisticsList = new LinkedList<>();

            for (int i = 0; i < 10; i++) {
                ReassignmentStatistics statistics = runReassignment(ScenarioMakerMain.SCENARIOS_DATA_FOLDER + name);
                statisticsList.add(statistics);
            }

            PrintWriter pw = new PrintWriter(new File(RESULTS_FOLDER + name));
            pw.write(gson.toJson(statisticsList));
            pw.close();
        }
    }
}
