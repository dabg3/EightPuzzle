package xyz.forfun.puzzle;

import xyz.forfun.puzzle.label.LabelChangeEvent;
import xyz.forfun.puzzle.label.TileLabelVetoableChangeListener;
import xyz.forfun.puzzle.restart.RestartEvent;
import xyz.forfun.puzzle.restart.RestartListener;

import java.beans.PropertyVetoException;
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
public class EightController extends JLabel implements TileLabelVetoableChangeListener, RestartListener {

    private BitSet currentHole;
    private BitSet lastHole;

    public EightController() {
        setText("START");
    }

    @Override
    public void vetoableChange(LabelChangeEvent evt) throws PropertyVetoException {
        if (evt.isRestartChange()) {
            return;
        }
        EightTile movedTile = evt.getSource();
        BitSet tile = Bitboards.instance(movedTile.getPosition() - 1);
        if (tile.intersects(lastHole)) {
            return;
        }
        System.out.println("received move from tile pos " + movedTile.getPosition());
        if (!Bitboards.isTileMovible(tile, currentHole)) {
            throw new PropertyVetoException("Clicked tile cannot be moved", evt);
        }
        lastHole = currentHole;
        currentHole = tile;
    }

    @Override
    public void restart(RestartEvent evt) {
        List<Integer> labels = evt.getNewValue();
        int holePosition = labels.indexOf(Options.HOLE_VALUE);
        currentHole = Bitboards.instance(holePosition);
        lastHole = Bitboards.instance();
    }

}
