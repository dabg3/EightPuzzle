package com.ziccolella.puzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import javax.swing.JButton;

import com.ziccolella.puzzle.restart.EightRestart;

public class EightTile extends JButton implements EightRestart.Listener, PropertyChangeListener {
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
     * Actually label is the same as JButton.text property thus it is used only for 'veto' logic,
     * while JButton.text leads the GUI.
     * getLabel() is not implemented since it is deprecated, use getText() to retrieve the value.
     */
    private int label;

    protected PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
    protected VetoableChangeSupport mVcs = new VetoableChangeSupport(this);


    public EightTile(int pos) {
        super(String.valueOf(pos)); //set JButton.text property
        this.position = pos;
    }

    public int getPosition() {
        return position;
    }

    public void setLabel(int tileNumber) throws PropertyVetoException {
        this.mVcs.fireVetoableChange("label", this.label, tileNumber);
        this.label = tileNumber;
        setText(String.valueOf(tileNumber)); //update JButton.text
        // who listens to this??
        this.mPcs.firePropertyChange("label", this.label, tileNumber);
    }

    @Override
    public void restart(EightRestart.Event e) {
        label = e.payload.get(position);
        setText(String.valueOf(label));
        // TODO: improve enable/disable
        if (label == 9) this.setEnabled(false);
        else this.setEnabled(true);
    }

    /*
     * should this method contain logic????
     * It is defined by PropertyChangeListener
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
      System.out.println(e.getNewValue());
      System.out.println(e.getOldValue());
      
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
        if (mPcs == null) {
            mPcs = new PropertyChangeSupport(this);
        }
        mPcs.addPropertyChangeListener(l);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
        if (mPcs == null) {
            return;
        }
        mPcs.removePropertyChangeListener(l);
    }

    public synchronized void addVetoableChangeListener(VetoableChangeListener l) {
        if (mVcs == null) {
            mVcs = new VetoableChangeSupport(this);
        }
        mVcs.addVetoableChangeListener(l);
    }

    public synchronized void removeVetoableChangeListener(VetoableChangeListener l) {
        if (mVcs == null) {
            return;
        }
        mVcs.removeVetoableChangeListener(l);
    }
}
