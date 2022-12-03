package xyz.forfun.puzzle.label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

public interface TileLabelChangeListener extends PropertyChangeListener {

    @Override
    default void propertyChange(PropertyChangeEvent evt) {
        try {
            tileLabelChange(LabelChangeEvent.fromPropertyChangeEvent(evt));
        } catch (PropertyVetoException ex) {
            throw new RuntimeException("change vetoed"); //TODO: improve exception
        }
    }

    void tileLabelChange(LabelChangeEvent evt) throws PropertyVetoException;

}
