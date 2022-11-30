package xyz.forfun.puzzle;

import java.awt.event.ActionEvent;
import java.beans.*;
import java.util.List;

import javax.swing.JButton;

public class EightTile extends JButton implements PropertyChangeListener {
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
    private int label;

    public EightTile(int pos) {
        super(String.valueOf(pos)); //set JButton.text property
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

    //restart
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.label = ((List<Integer>) evt.getNewValue()).get(position - 1);
        this.firePropertyChange("label", Options.RESTART_VALUE, this.label);
    }
}
