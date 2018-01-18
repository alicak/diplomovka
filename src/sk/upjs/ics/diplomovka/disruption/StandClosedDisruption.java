package sk.upjs.ics.diplomovka.disruption;

import sk.upjs.ics.diplomovka.base.Chromosome;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class StandClosedDisruption implements Disruption {
    private int stand;
    private StandsStorage standsStorage;

    public StandClosedDisruption(int stand, StandsStorage standsStorage) {
        this.stand = stand;
        this.standsStorage = standsStorage;
    }

    @Override
    public void disruptAssignment(Chromosome chromosome) {
        int standNo = standsStorage.getNumberById(stand);

        if(standNo == StandsStorage.STAND_NOT_FOUND)
            return;

        chromosome.removeGate(standNo);
        standsStorage.removeStand(stand);
    }
}
