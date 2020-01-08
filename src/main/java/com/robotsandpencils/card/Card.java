package com.robotsandpencils.card;

import java.util.Objects;

/**
 * Card Model Class
 *
 * @author Nan Chen
 */
public class Card {

    public Card(Rank r, Suit s) {
        this.rank = r;
        this.suit = s;
    }

    /**
     * rank of the card
     */
    private final Rank rank;

    public Rank getRank() {
        return rank;
    }

    /**
     * suit of the card
     */
    private final Suit suit;

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card) {
            Card c = (Card) o;
            return c.getRank() == this.getRank() && c.getSuit() == this.getSuit();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.rank);
        hash = 83 * hash + Objects.hashCode(this.suit);
        return hash;
    }

}
