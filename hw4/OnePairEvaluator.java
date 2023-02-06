package hw4;

import api.Card;

/**
 * Evaluator for a hand containing (at least) two cards of the same rank.
 * The number of cards required is two.
 * 
 * The name of this evaluator is "One Pair".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class OnePairEvaluator extends AbstractEvaluator
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
  public OnePairEvaluator(int ranking, int handSize)
  {
 
	  this.ranking = ranking;
	  this.handSize = handSize;
  }

@Override
public String getName() {
	return "One Pair";
}

@Override
public int getRanking() {
	return ranking;
}

@Override
public int cardsRequired() {
	return 2;
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
	
	return super.onePairSatisfy(mainCards);

}

}
