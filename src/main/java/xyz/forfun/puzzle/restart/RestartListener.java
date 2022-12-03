package xyz.forfun.puzzle.restart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface RestartListener extends PropertyChangeListener {

    @Override
    default void propertyChange(PropertyChangeEvent evt) {
        restart(RestartEvent.fromPropertyChangeEvent(evt));
        /*
         * by overriding firePropertyChange() and instancing RestartEvent there,
         * this may become restart(this);
         *
         * The problem is PropertyChangeSupport.firePropertyChange has to be overridden,
         * so a custom PropertyChangeSupport class should be written. Not worth the hassle!
         */
    }

    void restart(RestartEvent evt);

}
