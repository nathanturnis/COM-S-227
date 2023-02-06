package hw4;

import api.Card;

/**
 * Evaluator for a hand containing (at least) three cards of the same rank.
 * The number of cards required is three.
 * 
 * The name of this evaluator is "Three of a Kind".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class ThreeOfAKindEvaluator extends AbstractEvaluator
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
  public ThreeOfAKindEvaluator(int ranking, int handSize)
  {

	  this.ranking = ranking;
	  this.handSize = handSize;
	  
  }

@Override
public String getName() {
	return "Three of a Kind";
}

@Override
public int getRanking() {
	return ranking;
}

@Override
public int cardsRequired() {
	return 3;
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
	
	Card[] onePairCards = new Card[2];
	onePairCards[0] = mainCards[0];
	onePairCards[1] = mainCards[1];
	
	if(super.onePairSatisfy(onePairCards)) {
		if(mainCards[2].getRank() == mainCards[1].getRank()) {
			return true;
		}
	}
	
	return false;
}

}
