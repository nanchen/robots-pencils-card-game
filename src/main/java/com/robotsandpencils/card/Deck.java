package com.robotsandpencils.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Deck of card game
 *
 * @author Nan Chen
 */
public class Deck {

    private final List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    public Deck() {
        // init a standard deck  https://en.wikipedia.org/wiki/Standard_52-card_deck
        cards = new ArrayList();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card card = new Card(rank, suit);
                cards.add(card);
            }
        }
    }

    /**
     * Shuffle the deck
     */
    public void shuffle() {
        Random rand = new Random();
        final int cardsSize = cards.size();
        synchronized (cards) { //TODO we may use some newer concurrency infrastructure to improve performance
            // switch each card with a random card, we may use the blackbox Collections.shuffle method too, just need to verify it's statistically right
            for (int i = 0; i < cardsSize; i++) {
                int randomIdx = rand.nextInt(cardsSize);
                if (randomIdx != i) {
                    Card randomCard = cards.get(randomIdx);
                    cards.set(randomIdx, cards.get(i));
                    cards.set(i, randomCard);
                }
            }
        }
    }

    /**
     * Deal one card to a player
     *
     * @param player target player
     */
    public void dealOneCard(Player player) {
        if (cards.isEmpty()) {
            System.out.println("cards is empty now, cannot deal");
            return;
        }
        // remove first card from deck
        Card card;
        synchronized (cards) {
            card = cards.remove(0);
        }
        // add the card to player
        if (card != null) {
            player.addCard(card);
        }
    }
    
    /**
     * Deal cards to players
     *
     * @param players target players
     * @param cardsNum number of cards to deal [1, deck size]
     */
    public void dealCards(List<Player> players, int cardsNum) {
        // pre-check
        if (players == null || players.size() < 1) {
            throw new RuntimeException("invalid players");
        }
        final int cardsSize = cards.size();
        if (cardsNum < 1 || cardsNum > cardsSize) {
            throw new RuntimeException("invalid cardsNum: " + cardsNum + ", it has to be from 1 to " + cardsSize);
        }
        // deal cards
        int playersSize = players.size();
        synchronized (cards) {
            for (int i = 0; i < cardsNum; i++) {
                Player p = players.get(i % playersSize);
                dealOneCard(p);
            }
        }
    }
}
