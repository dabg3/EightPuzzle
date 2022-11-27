package com.ziccolella.puzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import javax.swing.JButton;
import com.ziccolella.puzzle.Events_and_Listeners.EightRestart;

public class EightTile extends JButton implements EightRestart.Listener,PropertyChangeListener{
    private final int position;

    /*
     * label represents the number positioned on this tile at a certain point in the game.
     * It is a Bound property, listeners must be notified when its value changes.
     * It is a Constrained property also, changes must be 'vetoed' by EightController.
     * https://docs.oracle.com/javase/tutorial/javabeans/writing/properties.html
     */
    private int label;

    protected PropertyChangeSupport mPcs = new PropertyChangeSupport(this);
    protected VetoableChangeSupport mVcs = new VetoableChangeSupport(this);


    public EightTile(int pos){
        super();
        this.position = pos;
    }

    public int getPosition(){ // getter
		return position;
    }      

    public String getLabel(){ // getter
		return Integer.toString(label);
    }

    public void setLabel(int new_label) throws PropertyVetoException{  // setter
        System.out.println("Hum, Lemme ask to controller");
        this.mVcs.fireVetoableChange("label",this.label,new_label);
        System.out.println(new_label + " -> " + this.label );
        this.label = new_label;
        this.mPcs.firePropertyChange("label",this.label,new_label);
    }

    @Override
    public void restart(EightRestart.Event e) {
        label = e.payload.get(position);
        this.setText(Integer.toString(label));
        if(label==9) this.setEnabled(false);
        else this.setEnabled(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
      System.out.println(e.getNewValue());
      System.out.println(e.getOldValue());
      
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
      if (mPcs==null) mPcs = new PropertyChangeSupport(this);
      mPcs.addPropertyChangeListener(l);
    }
    
    public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
      mPcs.removePropertyChangeListener(l);
    }

    public synchronized void addVetoableChangeListener(VetoableChangeListener l) {
      if (mVcs==null) mVcs = new VetoableChangeSupport(this);
      mVcs.addVetoableChangeListener(l);
    }

    public synchronized void removeVetoableChangeListener(VetoableChangeListener l) {
      mVcs.removeVetoableChangeListener(l);
    }
}
