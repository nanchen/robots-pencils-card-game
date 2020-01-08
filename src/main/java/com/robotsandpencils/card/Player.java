package com.robotsandpencils.card;

import java.util.ArrayList;
import java.util.List;

/**
 * Player of card game
 *
 * @author Nan Chen
 */
public class Player {

    /**
     * The cards at player's hand
     */
    private final List<Card> cards = new ArrayList();

    public List<Card> getCards() {
        return cards;
    }

    /**
     * Add a card to cards
     *
     * @param card
     */
    public void addCard(Card card) {
        synchronized (cards) {
            cards.add(card);
        }
    }

    @Override
    public String toString() {
        return "Player with cards: " + cards;
    }
}
