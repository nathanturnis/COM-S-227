package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator satisfied by any set of cards.  The number of
 * required cards is equal to the hand size.
 * 
 * The name of this evaluator is "Catch All".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class CatchAllEvaluator extends AbstractEvaluator
{
	
	/*
	 * Ranking of this evaluator.
	 */
	private int ranking;
	/*
	 * Hand size of this evaluator
	 */
	private int handSize;
	
  /**
   * Constructs the evaluator.
   * @param ranking
   *   ranking of this hand
   * @param handSize
   *   number of cards in a hand
   */
  public CatchAllEvaluator(int ranking, int handSize)
  {
	  
	  this.ranking = ranking;
	  this.handSize = handSize;
	  
  }

@Override
public String getName() {
	return "Catch All";
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
	return mainCards.length == cardsRequired();
}

@Override
public boolean canSubsetSatisfy(Card[] allCards) {
	return allCards.length >= cardsRequired();
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
	
	return new Hand(mainCards, null, this);
}

}
