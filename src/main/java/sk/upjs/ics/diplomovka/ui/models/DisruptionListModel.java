package sk.upjs.ics.diplomovka.ui.models;

import sk.upjs.ics.diplomovka.disruption.Disruption;

import javax.swing.*;
import java.util.List;

public class DisruptionListModel extends AbstractListModel {
    private List<Disruption> disruptions;

    public DisruptionListModel(List<Disruption> disruptions) {
        this.disruptions = disruptions;
    }

    @Override
    public int getSize() {
        return disruptions.size();
    }

    @Override
    public Object getElementAt(int index) {
        return disruptions.get(index);
    }

    public void setData(List<Disruption> disruptions) {
        this.disruptions = disruptions;
        fireContentsChanged(this, 0, disruptions.size() - 1);
    }
}

