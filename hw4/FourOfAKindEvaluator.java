package hw4;

import api.Card;

/**
 * Evaluator for a hand containing (at least) four cards of the same rank.
 * The number of cards required is four.
 * 
 * The name of this evaluator is "Four of a Kind".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class FourOfAKindEvaluator extends ThreeOfAKindEvaluator
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
  public FourOfAKindEvaluator(int ranking, int handSize)
  {

	  super(ranking, handSize);
	  this.ranking = ranking;
	  this.handSize = handSize;
	  
  }

@Override
public String getName() {
	return "Four of a Kind";
}

@Override
public int getRanking() {
	return ranking;
}

@Override
public int cardsRequired() {
	return 4;
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
	
	Card[] mainCards1 = new Card[2];
	Card[] mainCards2 = new Card[2];
	
	mainCards1[0] = mainCards[0];
	mainCards1[1] = mainCards[1];
	mainCards2[0] = mainCards[2];
	mainCards2[1] = mainCards[3];
	
	return (super.onePairSatisfy(mainCards1) && super.onePairSatisfy(mainCards2) && mainCards[0].getRank() == mainCards[3].getRank());
}

}
