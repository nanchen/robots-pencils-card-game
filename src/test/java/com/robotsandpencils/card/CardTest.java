package com.robotsandpencils.card;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for class Card
 *
 * @author Nan
 */
public class CardTest {

    /**
     * all tests
     */
    @Test
    public void testAll() {
        System.out.println("testAll");
        // not equal
        Card aceOfClubs = new Card(Rank.Ace, Suit.Clubs);
        Card twoOfClubs = new Card(Rank.Two, Suit.Clubs);
        assertFalse(aceOfClubs.equals(twoOfClubs));
        assertNotEquals(aceOfClubs.hashCode(), twoOfClubs.hashCode());
        // equal
        Card twoOfClubs1 = new Card(Rank.Two, Suit.Clubs);
        assertTrue(twoOfClubs.equals(twoOfClubs1));
        assertEquals(twoOfClubs1.hashCode(), twoOfClubs.hashCode());
        // not a Card => not equal
        assertFalse(twoOfClubs.equals(new Object()));
        // toString
        assertEquals("Ace of Clubs", aceOfClubs.toString());
    }
}
