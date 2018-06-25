package sk.upjs.ics.diplomovka.data.parser;

import java.io.File;

public final class Files {

    public static final String DATA_FOLDER = "data/";

    public static final File CATEGORIES = new File(DATA_FOLDER + "categories.json");
    public static final File AIRCRAFTS = new File(DATA_FOLDER + "aircrafts.json");
    public static final File ENGINE_TYPES = new File(DATA_FOLDER + "engineTypes.json");
    public static final File TRANSFERS = new File(DATA_FOLDER + "transfers.json");
    public static final File GATES = new File(DATA_FOLDER + "gates.json");
    public static final File GATE_DISTANCES = new File(DATA_FOLDER + "gateDistances.json");
    public static final File STAND_DISTANCES = new File(DATA_FOLDER + "standDistances.json");
    public static final File STANDS = new File(DATA_FOLDER + "stands.json");
    public static final File FLIGHTS = new File(DATA_FOLDER + "departures.json");
    public static final File DISRUPTIONS = new File(DATA_FOLDER + "disruptionsExample.json");
}
