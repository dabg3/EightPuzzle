package xyz.forfun.puzzle.label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface TileLabelChangeListener extends PropertyChangeListener {

    @Override
    default void propertyChange(PropertyChangeEvent evt) {
        tileLabelChange(LabelChangeEvent.fromPropertyChangeEvent(evt));
    }

    void tileLabelChange(LabelChangeEvent evt);

}
