package xyz.forfun.api.bitset;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

public class BitSetAPITest {

    @Test
    public void findBitOrder() {
        BitSet bitboard = new BitSet(9);
        bitboard.set(9);
        System.out.println(bitboard.toString());
    }
}
