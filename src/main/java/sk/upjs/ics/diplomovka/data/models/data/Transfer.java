package sk.upjs.ics.diplomovka.data.models.data;

public class Transfer extends Attribute {

    private int flightId; // id of departure that was arrival before and passengers transfer from that arrival
    private int noOfPassengers;

    public Transfer(int flightId, int noOfPassengers) {
        this.flightId = flightId;
        this.noOfPassengers = noOfPassengers;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }
}
