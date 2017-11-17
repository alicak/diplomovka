package sk.upjs.ics.diplomovka.base;

import java.util.List;

public class ChromosomeGenesPair {

    private List<Integer> first;
    private List<Integer> second;

    public ChromosomeGenesPair(List<Integer> first, List<Integer> second) {
        this.first = first;
        this.second = second;
    }

    public List<Integer> getFirst() {
        return first;
    }

    public List<Integer> getSecond() {
        return second;
    }
}