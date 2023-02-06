package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator for a hand in which the rank of each card is a prime number.
 * The number of cards required is equal to the hand size. 
 * 
 * The name of this evaluator is "All Primes".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class AllPrimesEvaluator extends AbstractEvaluator
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
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public AllPrimesEvaluator(int ranking, int handSize)
  {

	  this.ranking = ranking;
	  this.handSize = handSize;
	  
  }

@Override
public String getName() {
	return "All Primes";
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
	
	for(int i = 0; i < mainCards.length; i++) {
		int num = mainCards[i].getRank();
		
		if(num <= 1) {
			return false;
		}
		
		for(int j = 2; j <= num / 2; j++) {
			if(num % j == 0) {
				return false;
			}
		}
		
	}
	
	return true;
}

@Override
public Hand createHand(Card[] allCards, int[] subset) {
	
	if(allCards.length < handSize() || subset.length != cardsRequired()) {
		return null;
	}
	
	Card[] mainCards = new Card[subset.length];

	for(int i = 0; i < subset.length; i++) {
		mainCards[i] = allCards[subset[i]];
	}
	
	if(canSatisfy(mainCards)) {
		return new Hand(mainCards, null, this);
	} else {
		return null;
	}

}
  
}
