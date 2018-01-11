package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.base.Chromosome;

import java.util.List;

public class GateClosedDisruption implements Disruption {
    private int gate;
    private int[] standIds;
    private static final int FORBIDDEN_STAND = -1;

    public GateClosedDisruption(int gate, int[] standIds) {
        this.gate = gate;
        this.standIds = standIds;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        chromosome.removeGate(gate);
        for (int i = gate; i < standIds.length; i++) {
            standIds[i] = standIds[i+1];
        }
        standIds[standIds.length - 1] = FORBIDDEN_STAND;
    }
}
