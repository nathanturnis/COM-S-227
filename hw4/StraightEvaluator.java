package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator for a hand consisting of a "straight" in which the
 * card ranks are consecutive numbers.  The number of required 
 * cards is equal to the hand size.  An ace (card of rank 1) 
 * may be treated as the highest possible card or as the lowest
 * (not both).  To evaluate a straight containing an ace it is
 * necessary to know what the highest card rank will be in a
 * given game; therefore, this value must be specified when the
 * evaluator is constructed.  In a hand created by this
 * evaluator the cards are listed in descending order with high 
 * card first, e.g. [10 9 8 7 6] or [A K Q J 10], with
 * one exception: In case of an ace-low straight, the ace
 * must appear last, as in [5 4 3 2 A]
 * 
 * The name of this evaluator is "Straight".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class StraightEvaluator extends AbstractEvaluator
{  
	
	/*
	 * Ranking of this evaluator.
	 */
	private int ranking;
	/*
	 * Hand size of this evaluator.
	 */
	private int handSize;
	/*
	 * Max card rank, used for determining an ace high.
	 */
	private int maxCardRank;
	
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
  public StraightEvaluator(int ranking, int handSize, int maxCardRank) 
  {

	  this.ranking = ranking;
	  this.handSize = handSize;
	  this.maxCardRank = maxCardRank;
	  
  }

@Override
public String getName() {
	return "Straight";
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
	
	if(mainCards.length != cardsRequired()) {
		return false;
	}
	
	boolean hasAce = false;
	int numAces = 0;
	
	if(mainCards.length != cardsRequired()) {
		return false;
	}
	
	for(int i = 0; i < mainCards.length; i++) {
		if(mainCards[i].getRank() == 1) {
			hasAce = true;
			numAces++;
		}
	}
	
	if(numAces > 1) {
		return false;
	}
	
	return isStraight(hasAce, mainCards);
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

/**
 * Helper method to calculate whether or it is a straight.
 * 
 * @param hasAce
 * 	 whether not there is an ace in the hand
 * @param mainCards
 * 	 list of main cards
 * @return
 *   true if it is a straight
 */
private boolean isStraight(boolean hasAce, Card[] mainCards) {
	
	int prevRank; //rank to check to make sure it still is a straight
	
	if(hasAce && mainCards[1].getRank() == maxCardRank) { //check for high ace
		prevRank = maxCardRank;
		for(int i = 2; i < mainCards.length; i++) {
			if(mainCards[i].getRank() + 1 == prevRank) {
				prevRank = mainCards[i].getRank();
				continue;
			} else {
				return false;
			}
		}
		return true;
	} else if(hasAce) { //check for low ace
		prevRank = mainCards[1].getRank();
		for(int i = 2; i < mainCards.length; i++) {
			if(mainCards[i].getRank() + 1 == prevRank) {
				prevRank = mainCards[i].getRank();
				continue;
			} else {
				return false;
			}
		}
		if(mainCards[mainCards.length - 1].getRank() == 2) {
			return true;
		}
	} else {
		prevRank = mainCards[0].getRank();
		for(int i = 1; i < mainCards.length; i++) {
			if(mainCards[i].getRank() + 1 == prevRank) {
				prevRank = mainCards[i].getRank();
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	return false;
  }

}
