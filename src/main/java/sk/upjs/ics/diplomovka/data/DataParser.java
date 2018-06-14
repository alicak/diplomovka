package sk.upjs.ics.diplomovka.data;

import sk.upjs.ics.diplomovka.data.flights.FlightAttributes;
import sk.upjs.ics.diplomovka.data.flights.FlightStorage;
import sk.upjs.ics.diplomovka.data.stands.StandsStorage;

public class DataParser {

    private FlightAttributes attributes;
    private StandsStorage standsStorage;
    private FlightStorage flightStorage;

    public sk.upjs.ics.diplomovka.data.GeneralStorage parseDataFromJsons(String categoriesFile, String aircraftsFile, String engineTypesFile,
                                                                         String standsFile, String flightsFile) {
        parseFlightAttributes(categoriesFile, aircraftsFile, engineTypesFile);
        parseStands(standsFile);
        parseFlights(flightsFile);
        return new GeneralStorage(flightStorage, attributes, standsStorage, 0);
    }

    private void parseFlightAttributes(String categoriesFile, String aircraftsFile, String engineTypesFile) {
        attributes = new FlightAttributes()
                .setCategories(null)
                .setAircrafts(null)
                .setEngineTypes(null); // TODO
    }

    private void parseStands(String standsFile) {
        standsStorage = new StandsStorage(null, null, null); // TODO
    }

    private void parseFlights(String flightsFile) {
        flightStorage = new FlightStorage(null); // TODO
    }
}
