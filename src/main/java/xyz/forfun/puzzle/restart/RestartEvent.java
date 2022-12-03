package xyz.forfun.puzzle.restart;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class RestartEvent extends PropertyChangeEvent {

    //TODO: think about constructor visibility. A factory method is already provided
    /**
     * Constructs a new {@code PropertyChangeEvent}.
     *
     * @param source  the bean that fired the event
     * @param labels  randomly generated list of labels. List index matches tile position
     * @throws IllegalArgumentException if {@code source} is {@code null}
     */
    public RestartEvent(RestartAction source, List<Integer> labels) {
        super(source, "initializedLabels", new ArrayList<>(), labels);
    }

    @Override
    public List<Integer> getNewValue() {
       return retrieveTypizedValue(super::getNewValue);
    }

    static RestartEvent fromPropertyChangeEvent(PropertyChangeEvent evt) {
        if (!(evt.getSource() instanceof RestartAction action)) {
            throw new IllegalArgumentException(""); //TODO: add exception message
        }
        return new RestartEvent(action, retrieveTypizedValue(evt::getNewValue));
    }

    private static List<Integer> retrieveTypizedValue(Supplier<Object> getValueFunction) {
        if (!(getValueFunction.get() instanceof List labels)) {
            throw new IllegalStateException(""); // TODO: add exception message
        }
        return labels;
    }
}
