package xyz.forfun.puzzle;

import java.util.BitSet;
import java.util.function.Function;
import static xyz.forfun.puzzle.Options.COLUMNS;
import static xyz.forfun.puzzle.Options.ROWS;


public class Bitboards {

    private static final BitSet TOP_ROW = evaluate(Bitboards::initTopRow);
    private static final BitSet BOTTOM_ROW = evaluate(Bitboards::initBottomRow);
    private static final BitSet LEFT_COLUMN = evaluate(Bitboards::initLeftColumn);
    private static final BitSet RIGHT_COLUMN = evaluate(Bitboards::initRightColumn);

    /*
     * Instantiation helpers
     */

    public static BitSet instance() {
        return evaluate(b -> b);
    }

    public static BitSet instance(int pos) {
        return evaluate(b -> {
            b.set(pos);
            return b;
        });
    }

    private static BitSet evaluate(Function<BitSet, BitSet> fun) {
        BitSet bitboard = new BitSet(ROWS * COLUMNS);
        bitboard = fun.apply(bitboard);
        return bitboard;
    }

    /*
     * Common bitboards evaluation
     */

    private static BitSet initTopRow(BitSet b) {
        for (int i = 0; i < ROWS; i++) {
            b.set(i);
        }
        return b;
    }

    private static BitSet initBottomRow(BitSet b) {
        int bottomRowStartIndex = ROWS * (COLUMNS - 1) - 1;
        for (int i = bottomRowStartIndex; i < b.size(); i++) {
            b.set(i);
        }
        return b;
    }

    private static BitSet initLeftColumn(BitSet b) {
        for (int i = 0; i < b.size(); i += ROWS) {
            b.set(i);
        }
        return b;
    }

    private static BitSet initRightColumn(BitSet b) {
        for (int i = ROWS - 1; i < b.size(); i += ROWS) {
            b.set(i);
        }
        return b;
    }

    /*
     * API
     */

    public static boolean isTileMovible(BitSet tile, BitSet hole) {
        return isLeftMovible(tile, hole)    ||
                isRightMovible(tile, hole)  ||
                isUpMovible(tile, hole)     ||
                isDownMovible(tile, hole);
    }

    private static boolean isLeftMovible(BitSet tile, BitSet hole) {
        return isAdjacent(tile, hole, Bitboards.LEFT_COLUMN, 1);
    }

    private static boolean isRightMovible(BitSet tile, BitSet hole) {
        return isAdjacent(tile, hole, Bitboards.RIGHT_COLUMN, 1);
    }

    private static boolean isUpMovible(BitSet tile, BitSet hole) {
        return isAdjacent(tile, hole, Bitboards.TOP_ROW, 3);
    }

    private static boolean isDownMovible(BitSet tile, BitSet hole) {
        return isAdjacent(tile, hole, Bitboards.BOTTOM_ROW, 3);
    }

    private static boolean isAdjacent(BitSet tile, BitSet hole,
                                      BitSet boardEdge,
                                      int expectedBitsOffset)
    {
        if (boardEdge.intersects(tile)) {
            return false;
        }
        int offset = Math.abs(hole.nextSetBit(0) - tile.nextSetBit(0));
        if (offset != expectedBitsOffset) {
            return false;
        }
        return true;
    }

    private Bitboards() {
    }
}
