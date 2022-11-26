package com.ziccolella.puzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;

import com.ziccolella.puzzle.Events_and_Listeners.*;

//This buddy must check if a move is legal or not
public class EightController extends JLabel implements EightRestart.Listener, VetoableChangeListener{
    private static final int ROWS = 3;
    private static final int COLS = 3;

    public class Direction {
        final int x;
        final int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<Integer> current_conf;
    Direction[] moves = { new Direction(-1, 0), new Direction(0, -1), new Direction(1, 0), new Direction(0, 1) };

    public EightController() {
        this.setText("START");
    }

    //Veto implementation
    public void vetoableChange(PropertyChangeEvent e) throws PropertyVetoException{
        System.out.println("I'm the controller, lemme check, one sec");

        int current_hole_p = current_conf.indexOf(9);
        int clicked_pos = current_conf.indexOf(e.getNewValue());
        int possible_move_p;

        //Check if hole is adacient and reachable
        for (Direction d : moves) {
            possible_move_p = clicked_pos + d.x + d.y*COLS;
            if (current_hole_p == possible_move_p) {
                System.out.println("Ok, go on");
                Collections.swap(current_conf,current_conf.indexOf(e.getOldValue()),current_conf.indexOf(e.getNewValue()));
                return;
            }
        }

        System.out.println("Nah, try again");
        throw new PropertyVetoException("YO", e);
    }


    // Restart Event implementation
    @Override
    public void restart(EightRestart.Event e) {
        this.setText("RESETTED");
        current_conf = e.payload;
    }

    
    /* DOCUMENTATION USEFUL TO UNDERSTAND WHAT HAPPENED:
     * A "PropertyChange" event gets delivered whenever a bean changes a "bound" or
     * "constrained" property.
     * A PropertyChangeEvent object is sent as an argument to the
     * PropertyChangeListener and VetoableChangeListener methods.
     * Normally PropertyChangeEvents are accompanied by the name and the old and new
     * value of the changed property.
     * If the new value is a primitive type (such as int or boolean) it must be
     * wrapped as the corresponding java.lang.* Object type (such as Integer or
     * Boolean).
     * Null values may be provided for the old and the new values if their true
     * values are not known.
     */
}
