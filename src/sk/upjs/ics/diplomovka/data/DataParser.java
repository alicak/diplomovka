package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.FlightAttributes;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DataParser {

    public GeneralStorage parseDataFromJsons(String categoriesFile, String aircraftsFile, String engineTypesFile,
                                             String standsFile, String flightsFile) {
        throw new NotImplementedException();
    }

    private FlightAttributes parseFlightAttributes(String categoriesFile, String aircraftsFile, String engineTypesFile) {
        throw new NotImplementedException();
    }

    private StandsStorage parseStands(String standsFile) {
        throw new NotImplementedException();
    }

    private FlightStorage parseFlights(String flightsFile) {
        throw new NotImplementedException();
    }
}
