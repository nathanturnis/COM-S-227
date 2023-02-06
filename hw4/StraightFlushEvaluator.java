package hw4;

import api.Card;
import api.Hand;
import api.Suit;

/**
 * Evaluator for a hand consisting of a "straight" in which the
 * card ranks are consecutive numbers AND the cards all
 * have the same suit.  The number of required 
 * cards is equal to the hand size.  An ace (card of rank 1) 
 * may be treated as the highest possible card or as the lowest
 * (not both) To evaluate a straight containing an ace it is
 * necessary to know what the highest card rank will be in a
 * given game; therefore, this value must be specified when the
 * evaluator is constructed.  In a hand created by this
 * evaluator the cards are listed in descending order with high 
 * card first, e.g. [10 9 8 7 6] or [A K Q J 10], with
 * one exception: In case of an ace-low straight, the ace
 * must appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight Flush".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class StraightFlushEvaluator extends StraightEvaluator
{
	
	/*
	 * Ranking of this evaluator.
	 */
	private int ranking;
	/*
	 * Hand size of this evaluator.
	 */
	private int handSize;
	
  /**
   * Constructs the evaluator. Note that the maximum rank of
   * the cards to be used must be specified in order to 
   * correctly evaluate a straight with ace high.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   * @param maxCardRank
   *   largest rank of any card to be used
   */
  public StraightFlushEvaluator(int ranking, int handSize, int maxCardRank)
  {  
	  
	  super(ranking, handSize, maxCardRank);
	  this.ranking = ranking;
	  this.handSize = handSize;
	  
  }

@Override
public String getName() {
	return "Straight Flush";
}

@Override
public int getRanking() {
	return ranking;
}

@Override
public int cardsRequired() {
	return handSize;
}

@Override
public int handSize() {
	return handSize;
}

@Override
public boolean canSatisfy(Card[] mainCards) {
	
	if(super.canSatisfy(mainCards)) { //if we are a straight, now check to see if we have a flush
		Suit prevSuit;
		
		prevSuit = mainCards[0].getSuit();
		
		for(int i = 1; i < mainCards.length; i++) {
			if(mainCards[i].getSuit() == prevSuit) {
				prevSuit = mainCards[i].getSuit();
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	return false;
}

@Override
public Hand createHand(Card[] allCards, int[] subset) {
	
	if(allCards.length < handSize() || subset.length != cardsRequired()) {
		return null;
	}
	
	Card[] mainCards = new Card[subset.length];;
	
	for(int i = 0; i < subset.length; i++) {
		mainCards[i] = allCards[subset[i]];
	}
	
	if(!canSatisfy(mainCards)) {
		return null;
	} else {
		
		if(mainCards[0].getRank() == 1 && mainCards[mainCards.length - 1].getRank() == 2) { //this is a ace low straight
			
			Card ace = mainCards[0];
			Card[] newMainCards = new Card[subset.length];
			
			for(int i = 1; i < mainCards.length; i++) {
				newMainCards[i - 1] = mainCards[i];
			}
			
			newMainCards[subset.length - 1] = ace;
			return new Hand(newMainCards, null, this);
		}
		
		return new Hand(mainCards, null, this);
	}
}

}
