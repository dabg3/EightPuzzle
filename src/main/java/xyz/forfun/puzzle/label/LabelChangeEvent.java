package xyz.forfun.puzzle.label;

import xyz.forfun.puzzle.EightTile;
import xyz.forfun.puzzle.Options;

import java.beans.PropertyChangeEvent;

public class LabelChangeEvent extends PropertyChangeEvent {
    /**
     * Constructs a new {@code PropertyChangeEvent}.
     *
     * @param source       the bean that fired the event
     * @param oldValue     the old value of the property
     * @param newValue     the new value of the property
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    public LabelChangeEvent(EightTile source, Object oldValue, Object newValue) {
        super(source, "label", oldValue, newValue);
    }

    @Override
    public EightTile getSource() {
        if (!(source instanceof EightTile tile)) {
            throw new IllegalStateException(""); //TODO: add exception message
        }
        return tile;
    }

    public static LabelChangeEvent fromPropertyChangeEvent(PropertyChangeEvent evt) {
        if (!(evt.getSource() instanceof EightTile tile)) {
            throw new IllegalArgumentException(""); //TODO: add exception message
        }
        return new LabelChangeEvent(tile, evt.getOldValue(), evt.getNewValue());
    }

    //TODO: typize getters
    public boolean isRestartChange() {
        return (int) getOldValue() == Options.RESTART_VALUE;
    }
}
