package sk.upjs.ics.diplomovka.data.stands.closures;

public class StandClosure {

    private int id;
    private int start;
    private int end;
    private int standId;

    public StandClosure(int standId, int start, int end, int id) {
        this.standId = standId;
        this.start = start;
        this.end = end;
        this.id = id;
    }

    public int getStandId() {
        return standId;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return end - start;
    }

    public int getId() {
        return id;
    }
}
