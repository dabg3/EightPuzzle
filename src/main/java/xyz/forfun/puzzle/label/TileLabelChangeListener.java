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
            throw new IllegalStateException("[FAILURE] Hole tile is inconsistent");
        }
    }

    void tileLabelChange(LabelChangeEvent evt) throws PropertyVetoException;

}
