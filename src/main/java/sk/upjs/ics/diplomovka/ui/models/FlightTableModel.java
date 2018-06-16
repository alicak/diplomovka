package sk.upjs.ics.diplomovka.ui.models;

import sk.upjs.ics.diplomovka.data.models.data.FlightDataModel;
import sk.upjs.ics.diplomovka.data.models.view.FlightViewModel;
import sk.upjs.ics.diplomovka.utils.Utils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FlightTableModel extends AbstractTableModel {

    private List<FlightViewModel> flights;
    private static final String[] COLUMN_NAMES = {"Flight", "Time", "Destination", "Delay reg.", "Delay asg.", "Gate", "Stand"};

    public FlightTableModel(List<FlightViewModel> flights) {
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
        FlightViewModel flight = flights.get(rowIndex);
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
            case 6:
                return flight.getStandId();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    public void setData(List<FlightViewModel> flights) {
        this.flights = flights;
        fireTableDataChanged();
    }
}
