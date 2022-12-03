package xyz.forfun.puzzle;

import xyz.forfun.puzzle.restart.RestartEvent;
import xyz.forfun.puzzle.restart.RestartListener;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.List;

public class EightTile extends JButton implements RestartListener {
    /*
     * position indexes a tile on the board.
     * Top left is 1, bottom right is 9
     */
    private final int position;

    /*
     * label represents the number positioned on this tile at a certain point in the game.
     * It is a Bound property, listeners must be notified when its value changes.
     * It is a Constrained property also, changes may be 'vetoed' by EightController.
     * https://docs.oracle.com/javase/tutorial/javabeans/writing/properties.html
     *
     * WARNING: Actually label is the same as text in JButton sense,
     * it is a deprecated property. Here it is used as domain concept
     *
     * if tile is hole then label == 9
     */
    private int label = Options.RESTART_VALUE;

    public EightTile(int pos) {
        this.position = pos;
    }

    public int getPosition() {
        return position;
    }

    public void setLabel(int tileNumber) throws PropertyVetoException {
        this.fireVetoableChange("label", this.label, tileNumber);
        this.firePropertyChange("label", this.label, tileNumber);
        this.label = tileNumber;
    }

    @Override
    public void restart(RestartEvent evt) {
        List<Integer> restartLabels = evt.getNewValue();
        try {
            this.label = Options.RESTART_VALUE;
            setLabel(restartLabels.get(position - 1));
        } catch (PropertyVetoException e) {
            throw new IllegalStateException("Unable to restart");
        }
    }


}
