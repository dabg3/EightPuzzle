package xyz.forfun.puzzle.label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public interface TileLabelVetoableChangeListener extends VetoableChangeListener {

    @Override
    default void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        vetoableChange(LabelChangeEvent.fromPropertyChangeEvent(evt));
    }

    void vetoableChange(LabelChangeEvent evt) throws PropertyVetoException;
}
