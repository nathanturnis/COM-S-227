package hw4;

import api.Card;
import api.Hand;

/**
 * Evaluator for a generalized full house.  The number of required
 * cards is equal to the hand size.  If the hand size is an odd number
 * n, then there must be (n / 2) + 1 cards of the matching rank and the
 * remaining (n / 2) cards must be of matching rank. In this case, when constructing
 * a hand, the larger group must be listed first even if of lower rank
 * than the smaller group</strong> (e.g. as [3 3 3 5 5] rather than [5 5 3 3 3]).
 * If the hand size is even, then half the cards must be of matching 
 * rank and the remaining half of matching rank.  Any group of cards,
 * all of which are the same rank, always satisfies this
 * evaluator.
 * 
 * The name of this evaluator is "Full House".
 * 
 * @author Nathan Turnis
 */
//Note: You must edit this declaration to extend AbstractEvaluator
//or to extend some other class that extends AbstractEvaluator
public class FullHouseEvaluator extends AbstractEvaluator
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
  public FullHouseEvaluator(int ranking, int handSize)
  {
  
	  this.ranking = ranking;
	  this.handSize = handSize;
	  
  }

@Override
public String getName() {
	return "Full House";
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
	
	int rankToCheck = mainCards[0].getRank();
	boolean allCardsSameRank = true;
	
	for(int i = 1; i < mainCards.length; i++) { //check to see if all cards are same rank, as mentioned in evaluator criteria
		if(mainCards[i].getRank() == rankToCheck) {
			continue;
		} else {
			allCardsSameRank = false;
			break;
		}
	}
	
	if(allCardsSameRank) {
		return true;
	}
	
	if(handSize() % 2 == 0) { //if we have even num cards
		
		rankToCheck = mainCards[0].getRank();
		for(int i = 1; i < mainCards.length / 2; i++) {
			if(mainCards[i].getRank() == rankToCheck) {
				continue;
			} else {
				return false;
			}
		}
		
		rankToCheck = mainCards[mainCards.length / 2].getRank();
		for(int i = mainCards.length / 2; i < mainCards.length; i++) {
			if(mainCards[i].getRank() == rankToCheck) {
				continue;
			} else {
				return false;
			}
		}
		
		return true;
		
	} else { //if we have odd num cards
		
		rankToCheck = mainCards[0].getRank();
		int n = 1;
		for(int i = 1; i < mainCards.length; i++) {
			if(mainCards[i].getRank() == rankToCheck) {
				n++;
			}
		}
		
		if(n == ((handSize() / 2) + 1)) {
			rankToCheck = mainCards[n].getRank();
			for(int i = n + 1; i < mainCards.length; i++) {
				if(mainCards[i].getRank() == rankToCheck) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		} else if(n == (handSize() / 2)) {
			rankToCheck = mainCards[n].getRank();
			for(int i = n + 1; i < mainCards.length; i++) {
				if(mainCards[i].getRank() == rankToCheck) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
		
	}

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
	} else if(mainCards[0].getRank() == mainCards[mainCards.length - 1].getRank()) {
		return new Hand(mainCards, null, this);
	} else {
		//organize array so rank with more cards is listed first
		if(handSize() % 2 != 0) {
			Card[] newMainCards = new Card[subset.length];
			int rankWithMoreCards = mainCards[handSize() / 2].getRank();
			
			if(mainCards[0].getRank() == rankWithMoreCards) {
				return new Hand(mainCards, null, this);
			}
			
			int k = 0;
			for(int i = 0; i < mainCards.length; i++) {
				if(mainCards[i].getRank() == rankWithMoreCards) {
					newMainCards[k] = mainCards[i];
					k++;
				}
			}
			for(int i = 0; i < mainCards.length / 2; i++) {
				newMainCards[k] = mainCards[i];
				k++;
			}
			return new Hand(newMainCards, null, this);
		}
		
		return new Hand(mainCards, null, this);
	}
}
}
