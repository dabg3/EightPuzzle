package xyz.forfun.puzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.List;

import javax.swing.JLabel;

import com.ziccolella.puzzle.restart.*;

/*
 * EightController checks the legal move.
 * It uses bitboards to represent information (i.e a set of 9 bits, each bit represent a tile)
 * Considering how positions are laid out by assigment:
 * An hole on position 9 is represented as 000000001
 * An hole on position 1 is represented as 100000000
 * An hole on position 4 is represented as 000100000
 * and so on...
 *
 * This is a famous concept in chess engines.
 * https://www.chessprogramming.org/Bitboards
 */
public class EightController extends JLabel implements VetoableChangeListener, PropertyChangeListener {

    private int holePosition;

    public EightController() {
        setText("START");
    }

    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if (!(evt.getSource() instanceof EightTile movedTile)) {
            throw new RuntimeException("");
        }

        if (!isTileMovible(movedTile.getPosition())) {
            throw new PropertyVetoException("Clicked tile cannot be moved", evt);
        }
    }

    private boolean isTileMovible(int tilePosition) {
        int distance = Math.abs(holePosition - tilePosition);
        if (distance == 3 || distance == 1) {
            return true;
        }
        return false;
    }


    //restart
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Integer> labels = (List<Integer>) evt.getNewValue();
        holePosition = labels.indexOf(Options.HOLE_VALUE) + 1;
    }
}
