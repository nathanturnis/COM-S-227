package hw4;
import java.util.ArrayList;

import api.Card;
import api.Hand;
import api.IEvaluator;
import util.SubsetFinder;

/**
 * The class AbstractEvaluator includes common code for all evaluator types.
 * 
 * Every class extends this class, exepct for FourOfAKind, which extends ThreeOfAKind, and StraightFlush, which extends Straight.
 * Some common methods found with similar code were createHand(used by 3 classes, see below), gestBestHand(used by all classes), and 
 * canSubsetSatisfy(used by all classes except CatchAll). I added a protected method called onePairSatisfy that simiply chceks to see
 * if a pair of cards can satisfy. This method is used by OnePair, ThreeOfAKind, and FourOfAKind. ThreeKind uses this then checks the last card,
 * and FourKind uses this twice, and make sures ranks are same. All other classes with unique code Override the methods in this class and
 * the IEvaluator interface.
 * 
 * @author Nathan Turnis
 */
public abstract class AbstractEvaluator implements IEvaluator
{
 
	@Override
	public Hand createHand(Card[] allCards, int[] subset) { //used for OnePair, ThreeOfAKind, and FourOfAKind
		
		if(allCards.length < handSize() || subset.length != cardsRequired()) {
			return null;
		}
		
		Card[] mainCards = new Card[subset.length];
		Card[] sideCards = new Card[handSize() - cardsRequired()];
		
		for(int i = 0; i < subset.length; i++) {
			mainCards[i] = allCards[subset[i]];
		}
		
		if(!canSatisfy(mainCards)) {
			return null;		
		} else if(handSize() - cardsRequired() <= 0){
			return new Hand(mainCards, null, this);
		} else {
			int k = 0;
			int sideCardsIndex = 0;
			for(int i = 0; i < allCards.length; i++) {
				if(allCards[i].equals(mainCards[k])) {
					k++;
					k = Math.min(k, mainCards.length - 1);
					continue;
				} else {
					sideCards[sideCardsIndex] = allCards[i];
					sideCardsIndex++;
				}
				
				if(sideCards[sideCards.length - 1] != null) {
					break;
				}
			}
			
			return new Hand(mainCards, sideCards, this);
			
		}
	}
	
	@Override
	public Hand getBestHand(Card[] allCards) { //used for all evaluators
		
		if(allCards.length < handSize()) {
			return null;
		}
		
		ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		Hand[] hands = new Hand[subsets.size()];
		
		for(int i = 0; i < hands.length; i++) {
			hands[i] = createHand(allCards, subsets.get(i));
		
			if(!(hands[i] == null)) {
				return hands[i]; //assuming the first hand found is the best because of sorting
			}
		}
		
		return null;
	}
	
	@Override
	public boolean canSubsetSatisfy(Card[] allCards) { //used for all evaluators but CatchAll
		
		if(allCards.length < cardsRequired()) {
			return false;
		}
		
		ArrayList<int[]> subsets = SubsetFinder.findSubsets(allCards.length, cardsRequired());
		for(int i = 0; i < subsets.size(); i++) {
			int[] currentSubset = subsets.get(i);
			Card[] cards = new Card[currentSubset.length];
			
			for(int j = 0; j < currentSubset.length; j++) {
				cards[j] = allCards[currentSubset[j]];
			}
			
			if(canSatisfy(cards)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks to see if a pair of cards are the same rank.
	 * 
	 * @param mainCards
	 * @return
	 * 	true if it is a onepair
	 */
	protected boolean onePairSatisfy(Card[] mainCards) { //used for OnePair, ThreeOfAKind, and FourOfAKind
		
		if(mainCards.length != 2) {
			return false;
		}
		
		if(mainCards[0].getRank() == mainCards[1].getRank()) {
			return true;
		} else {
			return false;
		}
	}
	
}
