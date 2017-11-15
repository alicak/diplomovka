package sk.upjs.ics.diplomovka.base;

import java.util.List;

public class ChromosomeGenesPair<GeneType> {

    private List<GeneType> first;
    private List<GeneType> second;

    public ChromosomeGenesPair(List<GeneType> first, List<GeneType> second) {
        this.first = first;
        this.second = second;
    }

    public List<GeneType> getFirst() {
        return first;
    }

    public List<GeneType> getSecond() {
        return second;
    }
}
