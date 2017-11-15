package sk.upjs.ics.diplomovka.base;

public class ChromosomePair<ChromosomeType extends ChromosomeBase> {

    private ChromosomeType first;
    private ChromosomeType second;

    public ChromosomePair(ChromosomeType first, ChromosomeType second) {
        this.first = first;
        this.second = second;
    }

    public ChromosomeType getFirst() {
        return first;
    }

    public ChromosomeType getSecond() {
        return second;
    }

}
