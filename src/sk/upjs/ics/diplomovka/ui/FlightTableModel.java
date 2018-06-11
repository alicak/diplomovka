package sk.upjs.ics.diplomovka.ui;

import sk.upjs.ics.diplomovka.data.flights.FlightInfo;
import sk.upjs.ics.diplomovka.utils.Utils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FlightTableModel extends AbstractTableModel {

    private List<FlightInfo> flights;
    private static final String[] COLUMN_NAMES = {"Flight", "Time", "Destination", "Delay reg.", "Delay asg.", "Gate"};

    public FlightTableModel(List<FlightInfo> flights) {
        this.flights = flights;
    }

    @Override
    public int getRowCount() {
        return flights.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FlightInfo flight = flights.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return flight.getCode();
            case 1:
                return Utils.minutesToTime(flight.getActualStart());
            case 2:
                return flight.getDestination();
            case 3:
                return flight.getDelay();
            case 4:
                return flight.getAssignmentDelay();
            case 5:
                return flight.getGate();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void setData(List<FlightInfo> flights) {
        this.flights = flights;
        fireTableDataChanged();
    }
}
