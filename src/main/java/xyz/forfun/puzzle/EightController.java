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

    private BitSet hole; //init on restart

    public EightController() {
        setText("START");
    }

    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        if (!(evt.getSource() instanceof EightTile movedTile)) {
            throw new RuntimeException("");
        }
        BitSet tile = Bitboards.instance(movedTile.getPosition());
        if (!isTileMovible(tile)) {
            throw new PropertyVetoException("Clicked tile cannot be moved", evt);
        }
    }

    private boolean isTileMovible(BitSet tile) {
        if (tile == hole) {
            return false;
        }
        if (isHoleOnLeft(tile)  ||
            isHoleOnRight(tile) ||
            isHoleOnTop(tile)   ||
            isHoleOnBottom(tile))
        {
            return true;
        }
        return false;
    }

    private boolean isHoleOnLeft(BitSet tile) {
        return isHoleNear(tile, Bitboards.LEFT_COLUMN, 1);
    }

    private boolean isHoleOnRight(BitSet tile) {
        return isHoleNear(tile, Bitboards.RIGHT_COLUMN, 1);
    }

    private boolean isHoleOnTop(BitSet tile) {
        return isHoleNear(tile, Bitboards.TOP_ROW, 3);
    }

    private boolean isHoleOnBottom(BitSet tile) {
        return isHoleNear(tile, Bitboards.BOTTOM_ROW, 3);
    }

    private boolean isHoleNear(BitSet tile, BitSet edge, int expectedBitsOffset) {
        if (edge.intersects(tile)) {
            return false;
        }
        int offset = Math.abs(hole.nextSetBit(0) - tile.nextSetBit(0));
        if (offset != expectedBitsOffset) {
            return false;
        }
        return true;
    }

    //restart
    public void propertyChange(PropertyChangeEvent evt) {
        List<Integer> labels = (List<Integer>) evt.getNewValue();
        int holePosition = labels.indexOf(Options.HOLE_VALUE) + 1;
        hole = Bitboards.instance(holePosition);
    }
}
