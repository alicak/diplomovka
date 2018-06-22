package sk.upjs.ics.diplomovka.evaluation;

import sk.upjs.ics.diplomovka.data.models.view.ReassignmentStatistics;

public class ReassignmentInfo {
    private ReassignmentStatistics statistics;
    private String scenario;

    public ReassignmentInfo(ReassignmentStatistics statistics, String scenario) {
        this.statistics = statistics;
        this.scenario = scenario;
    }
}