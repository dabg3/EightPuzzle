package xyz.forfun.puzzle;

import java.util.BitSet;
import java.util.function.Function;
import static xyz.forfun.puzzle.Options.COLUMNS;
import static xyz.forfun.puzzle.Options.ROWS;


public class Bitboards {

    static final BitSet TOP_ROW = evaluate(Bitboards::initTopRow);
    static final BitSet BOTTOM_ROW = evaluate(Bitboards::initBottomRow);
    static final BitSet LEFT_COLUMN = evaluate(Bitboards::initLeftColumn);
    static final BitSet RIGHT_COLUMN = evaluate(Bitboards::initRightColumn);

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

    private static BitSet evaluate(Function<BitSet, BitSet> fun) {
        BitSet bitboard = new BitSet(ROWS * COLUMNS);
        bitboard = fun.apply(bitboard);
        return bitboard;
    }

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

    private Bitboards() {
    }
}
