package com.robotsandpencils.card;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for class Deck
 *
 * @author Nan Chen
 */
public class DeckTest {

    private final PrintStream out = System.out;
    private Deck deck;

    @BeforeEach
    public void setUp() {
        deck = new Deck(); //TODO for some reason, this didn't work (NPE thrown), for now, new Deck() in each test
    }

    @AfterEach
    public void tearDown() {
        deck = null;
    }

    @Test
    public void testShuffle() {
        out.println("testShuffle");
        deck = new Deck();
        // shuffle once
        deck.shuffle();
        out.println(deck.getCards());
        assertEquals(52, deck.getCards().size());
        // shuffle multiple times => statistically correct
        Map<Integer, Integer> stats = new HashMap();
        for (int i = 0; i < 100; i++) {
            deck = new Deck();
            deck.shuffle();
            Integer hash = deck.getCards().toString().hashCode();
            Integer count = stats.get(hash);
            if (count == null) {
                count = 0;
            }
            count++;
            stats.put(hash, count);
        }
        for (Map.Entry<Integer, Integer> stat : stats.entrySet()) {
            if (stat.getValue() > 1) {
                out.println("We got " + stat.getValue() + " times of one shuffle result");
            }
        }
        // TODO may reduce deck size and do many times of shuffle to see if all results occur approximately equally
    }

    @Test
    public void testDealOneCard() {
        out.println("testDealOneCard");
        deck = new Deck();
        Player p = new Player();
        // deal 1st card
        deck.dealOneCard(p);
        assertEquals(new Card(Rank.Ace, Suit.Clubs), p.getCards().get(0));
        // deal 2nd card
        deck.dealOneCard(p);
        assertEquals(new Card(Rank.Two, Suit.Clubs), p.getCards().get(1));
        // deal the rest
        for (int i = 0; i < 50; i++) {
            deck.dealOneCard(p);
        }
        assertEquals(new Card(Rank.King, Suit.Spades), p.getCards().get(51));
        assertTrue(deck.getCards().isEmpty());
        // try to deal one more => no effect
        deck.dealOneCard(p);
        assertEquals(52, p.getCards().size());
    }

    @Test
    public void testDealCards() {
        out.println("testDealCards");
        deck = new Deck();
        // invalid players => exception
        Exception e = Assertions.assertThrows(RuntimeException.class, () -> {
            deck.dealCards(null, 0);
        });
        assertEquals("invalid players", e.getMessage());
        // invalid cardsNum => exception
        e = Assertions.assertThrows(RuntimeException.class, () -> {
            deck.dealCards(Arrays.asList(new Player()), 0);
        });
        assertEquals("invalid cardsNum: 0, it has to be from 1 to 52", e.getMessage());
        // Deal all 52 cards to 4 players
        Player p0 = new Player();
        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();
        List<Player> ps = Arrays.asList(p0, p1, p2, p3);
        deck.dealCards(ps, 52);
        out.println(p0);
        out.println(p1);
        out.println(p2);
        out.println(p3);
        assertEquals(13, p0.getCards().size());
        assertEquals(13, p1.getCards().size());
        assertEquals(13, p2.getCards().size());
        assertEquals(13, p3.getCards().size());
        assertTrue(deck.getCards().isEmpty());
        // deal 10 cards to first 10 players
        deck = new Deck();
        ps = new ArrayList();
        for (int i = 0; i < 30; i++) {
            ps.add(new Player());
        }
        deck.dealCards(ps, 10);
        for (int i = 0; i < 10; i++) {
            Player p = ps.get(i);
            assertEquals(1, p.getCards().size());
            out.println((p));
        }
        for (int i = 10; i < 30; i++) {
            Player p = ps.get(i);
            assertTrue(p.getCards().isEmpty());
            out.println((p));
        }
        assertEquals(42, deck.getCards().size());
    }

    //TODO may execute in concurrency to verify thread safety
}
