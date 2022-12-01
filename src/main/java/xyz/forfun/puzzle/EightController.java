package xyz.forfun.puzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.BitSet;
import java.util.List;

import javax.swing.JLabel;

/*
 * EightController checks the legal move.
 * It uses bitboards to represent information (i.e a set of 9 bits, each bit represent a tile)
 * Considering how positions are laid out by assigment:
 * An hole on position 9 is represented as 000000001
 * An hole on position 1 is represented as 100000000
 * An hole on position 4 is represented as 000100000
 * and so on...
 *
 * I consider bit at index 0 as the "leftmost" bit:
 * BitSet bitboard = new BitSet(9);
 * bitboard.set(0) -> 100000000
 * bitboard.set(9) -> 100000001
 *
 * This is a famous concept in chess engines.
 * https://www.chessprogramming.org/Bitboards
 */
public class EightController extends JLabel implements VetoableChangeListener, PropertyChangeListener {

    private BitSet hole;

    public EightController() {
        setText("START");
    }

    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if (!(evt.getSource() instanceof EightTile movedTile)) {
            throw new RuntimeException("");
        }
        if (((int) evt.getOldValue()) == Options.RESTART_VALUE) {
            return;
        }
        BitSet tile = Bitboards.instance(movedTile.getPosition() - 1);
        if (tile == hole) {
            return;
        }
        if (!Bitboards.isTileMovible(tile, hole)) {
            throw new PropertyVetoException("Clicked tile cannot be moved", evt);
        }
        hole = tile;
    }

    //restart
    public void propertyChange(PropertyChangeEvent evt) {
        List<Integer> labels = (List<Integer>) evt.getNewValue();
        int holePosition = labels.indexOf(Options.HOLE_VALUE);
        hole = Bitboards.instance(holePosition);
    }
}
