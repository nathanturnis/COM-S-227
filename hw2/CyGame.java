package hw2;

 /**
 * Model of a Monopoly-like game. Two players take turns rolling dice to move
 * around a board. The game ends when one of the players has at least
 * MONEY_TO_WIN money or one of the players goes bankrupt (has negative money).
 * 
 * @author Nathan Turnis
 */
public class CyGame {
	
	 /**
	 * Do nothing square type.
	 */
	public static final int DO_NOTHING = 0;
	/**
	 * Pass go square type.
	 */
	public static final int PASS_GO = 1;
	/**
	 * Cyclone square type.
	 */
	public static final int CYCLONE = 2;
	/**
	 * Pay the other player square type.
	 */
	public static final int PAY_PLAYER = 3;
	/**
	 * Get an extra turn square type.
	 */
	public static final int EXTRA_TURN = 4;
	/**
	 * Jump forward square type.
	 */
	public static final int JUMP_FORWARD = 5;
	/**
	 * Stuck square type.
	 */
	public static final int STUCK = 6;
	/**
	 * Points awarded when landing on or passing over go.
	 */
	public static final int PASS_GO_PRIZE = 200;
	/**
	 * The amount payed to the other player per unit when landing on a
	 * PAY_PLAYER square.
	 */
	public static final int PAYMENT_PER_UNIT = 20;
	/**
	 * The amount of money required to win.
	 */
	public static final int MONEY_TO_WIN = 400;
	/**
	 * The cost of one unit.
	 */
	public static final int UNIT_COST = 50;
	
	public boolean gameEnded;
	
	public int numSquares;
	
	public int currentPlayer;
	
	public int player1Square;
	public int player1Money;
	public int player1Units;
	
	public int player2Square;
	public int player2Money;
	public int player2Units;
	
	public int player1SquareType;
	public int player2SquareType;
	
	
	public CyGame(int numSquares, int startingMoney) {
		gameEnded = false;
		currentPlayer = 1;
		this.numSquares = numSquares;
		player1Square = 0;
		player2Square = 0;
		player1Money = startingMoney;
		player2Money = startingMoney;
		player1Units = 1;
		player2Units = 1;
	}
	
	public void buyUnit() {
		if(gameEnded) {
			return;
		}
		
		if(currentPlayer == 1) {
			player1SquareType = getSquareType(player1Square);
			
			if(player1SquareType == DO_NOTHING && player1Money >= UNIT_COST) {
				player1Money -= UNIT_COST;
				player1Units++;
				endTurn();
				return;
			}
			}
		
		
		if(currentPlayer == 2) {
			player2SquareType = getSquareType(player2Square);
			
			if(player2SquareType == DO_NOTHING && player2Money >= UNIT_COST) {
				player2Money -= UNIT_COST;
				player2Units++;
				endTurn();
				return;
			}
		}
	}
	
	public void endTurn() {
		
		if(player1Money >= MONEY_TO_WIN || player2Money >= MONEY_TO_WIN || player1Money < 0 || player2Money < 0) {
			gameEnded = true;
			return;
		}
		
		if(currentPlayer == 1) {
			currentPlayer++;
		} else {
			currentPlayer = 1;
		}
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public int getPlayer1Money() {
		return player1Money;
	}
	
	public int getPlayer1Square() {
		return player1Square;
	}
	
	public int getPlayer1Units() {
		return player1Units;
	}
	
	public int getPlayer2Money() {
		return player2Money;
	}
	
	public int getPlayer2Square() {
		return player2Square;
	}
	
	public int getPlayer2Units() {
		return player2Units;
	}
	
	public int getSquareType(int square) {
		
		if(square == 0) {
			return PASS_GO;
		} else if(square == (numSquares - 1)) {
			return CYCLONE;
		} else if((square % 5) == 0) {
			return PAY_PLAYER;
		} else if((square % 7) == 0 || (square % 11) == 0) {
			return EXTRA_TURN;
		} else if((square % 3) == 0) {
			return STUCK;
		} else if((square % 2) == 0) {
			return JUMP_FORWARD;
		} else {
			return DO_NOTHING;
		}

	}
	
	public boolean isGameEnded() {
		return gameEnded;
	}
	
	public void roll(int value) {
		
		if(gameEnded) {
			return;
		}
		
		if(currentPlayer == 1 && !gameEnded) {
			player1SquareType = getSquareType(player1Square);
			
			if(player1SquareType == STUCK && (value % 2) == 0) {
				player1Square += value;
			} else if(player1SquareType != STUCK) {
				player1Square += value;
			} else {
				endTurn();
				return;
			}
			
			if(player1Square > numSquares) { //if player passes over the start, apply pass and go prize
				player1Money += PASS_GO_PRIZE;
			}
			player1Square %= numSquares; //if the player reaches the end, their square number will go to 0
			player1SquareType = getSquareType(player1Square);
			
			if(player1SquareType == PASS_GO) {
				player1Money += PASS_GO_PRIZE;
			} else if(player1SquareType == CYCLONE) {
				player1Square = player2Square;
			} else if(player1SquareType == PAY_PLAYER) {
				int money = PAYMENT_PER_UNIT * player2Units;
				player1Money -= money;
				player2Money += money;
			} else if(player1SquareType == EXTRA_TURN) {
				return; //is this right?
			} else if(player1SquareType == STUCK) {
				//nothing happens
			} else if(player1SquareType == JUMP_FORWARD) {
				player1Square += 4;
				if(player1Square > numSquares) { //if player passes over the start, apply pass and go prize
					player1Money += PASS_GO_PRIZE;
				}
				player1Square %= numSquares; //if the player reaches the end, their square number will go to 0
			} else if(player1SquareType == DO_NOTHING) {
				//nothing happens
			}
			
			endTurn();
			return;
			
		}
		
		if(currentPlayer == 2 &&!gameEnded) {
			player2SquareType = getSquareType(player2Square);
			
			if(player2SquareType == STUCK && (value % 2) == 0) {
				player2Square += value;
			} else if(player2SquareType != STUCK) {
				player2Square += value;
			} else {
				endTurn();
				return;
			}
			
		//	System.out.println(getPlayer2Square());
			
			if(player2Square > numSquares) { //if player passes over the start, apply pass and go prize
				player2Money += PASS_GO_PRIZE;
			}
			player2Square %= numSquares; //if the player reaches the end, their square number will go to 0
			player2SquareType = getSquareType(player2Square);
			
			if(player2SquareType == PASS_GO) {
				player2Money += PASS_GO_PRIZE;
			} else if(player2SquareType == CYCLONE) {
			//	System.out.println("hello");
				player2Square = player1Square;
			} else if(player2SquareType == PAY_PLAYER) {
				int money = PAYMENT_PER_UNIT * player1Units;
				player2Money -= money;
				player1Money += money;
			} else if(player2SquareType == EXTRA_TURN) {
				return; //is this right?
			} else if(player2SquareType == STUCK) {
				//nothing happens
			} else if(player2SquareType == JUMP_FORWARD) {
				player2Square += 4;
				if(player2Square > numSquares) { //if player passes over the start, apply pass and go prize
					player2Money += PASS_GO_PRIZE;
				}
				player2Square %= numSquares; //if the player reaches the end, their square number will go to 0
			} else if(player2SquareType == DO_NOTHING) {
				//nothing happens
			}
			
			endTurn();
			return;
		}
		
	}
	
	public void sellUnit() {
		
		if(gameEnded) {
			return;
		}
		
		if(currentPlayer == 1) {
			if(player1Units >= 1) {
				player1Money += UNIT_COST;
				player1Units--;
				endTurn();
				return;
			}
		}
		
		if(currentPlayer == 2) {
			if(player2Units >= 1) {
				player2Money += UNIT_COST;
				player2Units--;
				endTurn();
				return;
			}
		}
		
	}
	
	
	
	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player 1*: (0, 0, $0) Player 2: (0, 0, $0)</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which players turn it
	 * is. The numbers (0, 0, $0) indicate which square the player is on, how
	 * many units the player has, and how much money the player has
	 * respectively.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
	String fmt = "Player 1%s: (%d, %d, $%d) Player 2%s: (%d, %d, $%d)";
	String player1Turn = "";
	String player2Turn = "";
	if (getCurrentPlayer() == 1) {
	player1Turn = "*";
	} else {
	player2Turn = "*";
	}
	return String.format(fmt,
	player1Turn, getPlayer1Square(), getPlayer1Units(), 
	getPlayer1Money(),
	player2Turn, getPlayer2Square(), getPlayer2Units(), 
	getPlayer2Money());
	}
	
}
