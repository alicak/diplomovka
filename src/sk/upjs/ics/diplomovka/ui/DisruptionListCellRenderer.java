package sk.upjs.ics.diplomovka.ui;

import sk.upjs.ics.diplomovka.disruption.Disruption;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;

public class DisruptionListCellRenderer extends JLabel implements ListCellRenderer<Disruption> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Disruption> list, Disruption value, int index, boolean isSelected, boolean cellHasFocus) {
        throw new NotImplementedException();
    }
}
