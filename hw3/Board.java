package hw3;

import static api.Direction.*;
import static api.Orientation.*;

import java.util.ArrayList;

import api.Cell;
import api.CellType;
import api.Direction;
import api.Move;
import api.Orientation;

/**
 * Represents a board in the Block Slider game. A board contains a 2D grid of
 * cells and a list of blocks that slide over the cells.
 * @author Nathan Turnis
 */
public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left cornner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of blocks that are positioned on the board.
	 */
	private ArrayList<Block> blocks;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of blocks on the board.
	 */
	private ArrayList<Move> moveHistory;
	
	/**
	 * Which block is currently grabbed. Is set to null if no block is grabbed.
	 */
	private Block grabbedBlock;
	/**
	 * Which cell is currently grabbed.
	 */
	private Cell grabbedCell;

	/**
	 * Constructs a new board from a given 2D array of cells and list of blocks. The
	 * cells of the grid should be updated to indicate which cells have blocks
	 * placed over them (i.e., setBlock() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param blocks list of blocks already containing row-column position which
	 *               should be placed on the board
	 */
	public Board(Cell[][] grid, ArrayList<Block> blocks) {
		
		this.grid = grid;
		this.blocks = blocks;
		moveHistory = new ArrayList<Move>();
		
		//update which cells have blocks over them
		for(int i = 0; i < blocks.size(); i++) {
			int row;
			int col;
			int length;
			Orientation orientation;
			
			Block block = blocks.get(i);
			
			row = block.getFirstRow();
			col = block.getFirstCol();
			length = block.getLength();
			orientation = block.getOrientation();
			
			if(orientation == HORIZONTAL) {
				for(int j = 0; j < length; j++) {
					//go through each cell the block touches, and set it as a block cell
					grid[row][col].setBlock(block);
					col++;
				}
			} else if(orientation == VERTICAL) {
				for(int j = 0; j < length; j++) {
					//go through each cell the block touches, and set it as a block cell
					grid[row][col].setBlock(block);
					row++;
				}
			}
			
		}
		
	}

	/**
	 * Constructs a new board from a given 2D array of String descriptions.
	 * <p>
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBlocks(desc));
	}

	/**
	 * Models the user grabbing a block over the given row and column. The purpose
	 * of grabbing a block is for the user to be able to drag the block to a new
	 * position, which is performed by calling moveGrabbedBlock(). This method
	 * records two things: the block that has been grabbed and the cell at which it
	 * was grabbed.
	 * 
	 * @param row row to grab the block from
	 * @param col column to grab the block from
	 */
	public void grabBlockAtCell(int row, int col) {
		
		grabbedBlock = grid[row][col].getBlock();
		grabbedCell = new Cell(CellType.FLOOR, row, col);
		
	}

	/**
	 * Set the currently grabbed block to null.
	 */
	public void releaseBlock() {
		grabbedBlock = null;
	}

	/**
	 * Returns the currently grabbed block.
	 * 
	 * @return the current block
	 */
	public Block getGrabbedBlock() {
		return grabbedBlock;
	}

	/**
	 * Returns the currently grabbed cell.
	 * 
	 * @return the current cell
	 */
	public Cell getGrabbedCell() {	
		return grabbedCell;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a block
	 * to be placed over it. Blocks can only be placed over floors and exits. A
	 * block cannot be placed over a cell that is occupied by another block.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a block, otherwise false
	 */
	public boolean canPlaceBlock(int row, int col) {
		
		if((grid[row][col].isFloor() || grid[row][col].isExit()) && grid[row][col].hasBlock() == false) {
			return true;
		} else if(grid[row][col].isWall() || grid[row][col].hasBlock()){
			return false;
		}
		
		return false; //this should theoretically never be run
	}

	/**
	 * Returns the number of moves made so far in the game.
	 * 
	 * @return the number of moves
	 */
	public int getMoveCount() {
		return moveHistory.size();
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		return grid.length;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		return grid[0].length;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns a list of all blocks on the board.
	 * 
	 * @return a list of all blocks
	 */
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a block
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				//may be inefficient way of doing this, but if the grid cell is an exit and has a block, return true
				if(grid[i][j].isExit() && grid[i][j].hasBlock()) {
					return true;
				}
				
			}
		}
		
		return false;
	}

	/**
	 * Moves the currently grabbed block by one cell in the given direction. A
	 * horizontal block is only allowed to move right and left and a vertical block
	 * is only allowed to move up and down. A block can only move over a cell that
	 * is a floor or exit and is not already occupied by another block. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No block is currently grabbed by the user.</li>
	 * <li>A block is currently grabbed by the user, but the block is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does the following:
	 * <ul>
	 * <li>Moves the block object by calling its move method.</li>
	 * <li>Sets the block for the grid cell that the block is being moved into.</li>
	 * <li>For the grid cell that the block is being moved out of, sets the block to
	 * null.</li>
	 * <li>Moves the currently grabbed cell by one cell in the same moved direction.
	 * The purpose of this is to make the currently grabbed cell move with the block
	 * as it is being dragged by the user.</li>
	 * <li>Adds the move to the end of the moveHistory list.</li>
	 * <li>Increment the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBlock(Direction dir) {
		
		if(isGameOver() || grabbedBlock == null) {
			return;
		}
		
		if(grabbedBlock.getOrientation() == Orientation.VERTICAL) {
			if(dir == dir.UP && canPlaceBlock(grabbedBlock.getFirstRow() - 1, grabbedBlock.getFirstCol())) {
				grid[grabbedBlock.getFirstRow() + grabbedBlock.getLength() - 1][grabbedBlock.getFirstCol()].clearBlock();
				grabbedBlock.move(dir); //move the block
				grabbedCell = new Cell(CellType.FLOOR, grabbedCell.getRow() - 1, grabbedCell.getCol());
				grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()].setBlock(grabbedBlock);
				moveHistory.add(new Move(grabbedBlock, dir)); //move count is essentially the # of elements in this list
			} else if(dir == dir.DOWN && canPlaceBlock(grabbedBlock.getFirstRow() + grabbedBlock.getLength(), grabbedBlock.getFirstCol())) {
				grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()].clearBlock();
				grabbedBlock.move(dir); //move the block
				grabbedCell = new Cell(CellType.FLOOR, grabbedCell.getRow() + 1, grabbedCell.getCol());
				grid[grabbedBlock.getFirstRow() + grabbedBlock.getLength() - 1][grabbedBlock.getFirstCol()].setBlock(grabbedBlock);
				moveHistory.add(new Move(grabbedBlock, dir)); //move count is essentially the # of elements in this list
			}
		} else if(grabbedBlock.getOrientation() == Orientation.HORIZONTAL) {
			if(dir == dir.LEFT && canPlaceBlock(grabbedBlock.getFirstRow(), grabbedBlock.getFirstCol() - 1)) {
				grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength() - 1].clearBlock();
				grabbedBlock.move(dir); //move the block
				grabbedCell = new Cell(CellType.FLOOR, grabbedCell.getRow(), grabbedCell.getCol() - 1);
				grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()].setBlock(grabbedBlock);
				moveHistory.add(new Move(grabbedBlock, dir)); //move count is essentially the # of elements in this list
			} else if(dir == dir.RIGHT && canPlaceBlock(grabbedBlock.getFirstRow(), grabbedBlock.getFirstCol() + grabbedBlock.getLength())) {
				grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol()].clearBlock();
				grabbedBlock.move(dir); //move the block
				grabbedCell = new Cell(CellType.FLOOR, grabbedCell.getRow(), grabbedCell.getCol() + 1);
				grid[grabbedBlock.getFirstRow()][grabbedBlock.getFirstCol() + grabbedBlock.getLength() - 1].setBlock(grabbedBlock);
				moveHistory.add(new Move(grabbedBlock, dir)); //move count is essentially the # of elements in this list
			}
		}
		
	}

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each block object. It also updates each grid cells by calling
	 * their setBlock method to either set a block if one is located over the cell
	 * or set null if no block is located over the cell.
	 */
	public void reset() {
		
		moveHistory.clear();
		
		for(int i = 0; i < blocks.size(); i++) { //go through each block
			int row;
			int col;
			Orientation orientation;
			
			Block block = blocks.get(i);
			row = block.getFirstRow();
			col = block.getFirstCol();
			orientation = block.getOrientation();
			
			if(orientation == HORIZONTAL) {
				//go through each cell the block touches, and clear it
				for(int j = 0; j < block.getLength(); j++) {
					grid[row][col].clearBlock();
 					col++;
				}
			} else if(orientation == VERTICAL) {
				//go through each cell the block touches, and clear it
				for(int j = 0; j < block.getLength(); j++) {
					grid[row][col].clearBlock();
					row++;
				}
			}
			
			block.reset();	
			
			row = block.getFirstRow();
			col = block.getFirstCol();
			
			if(orientation == HORIZONTAL) {
				//go through each cell the block now touches, and set it as a block cell
				for(int j = 0; j < block.getLength(); j++) {
					grid[row][col].setBlock(block);
					col++;
				}
			} else if(orientation == VERTICAL) {
				//go through each cell the block now touches, and set it as a block cell
				for(int j = 0; j < block.getLength(); j++) {
					grid[row][col].setBlock(block);
					row++;
				}
			}
			
			
		}
		
	}

	/**
	 * Returns a list of all legal moves that can be made by any block on the
	 * current board. If the game is over there are no legal moves.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		
		for(int i = 0; i < blocks.size(); i++) { //go through each block
			
			Block block = blocks.get(i);
			
			if(block.getOrientation() == HORIZONTAL) {
				if(canPlaceBlock(block.getFirstRow(), block.getFirstCol() - 1)) { //can move left?
					possibleMoves.add(new Move(block, LEFT));
				} else if(canPlaceBlock(block.getFirstRow(), block.getFirstCol() + block.getLength())) { //can move right?
					possibleMoves.add(new Move(block, RIGHT));
				}
			} else if(block.getOrientation() == VERTICAL) {
				if(canPlaceBlock(block.getFirstRow() - 1, block.getFirstCol())) { //can move up?
					possibleMoves.add(new Move(block, UP));
				} else if(canPlaceBlock(block.getFirstRow() + block.getLength(), block.getFirstCol())) { //can move down?
					possibleMoves.add(new Move(block, DOWN));
				}
			}
					
		}
		
		return possibleMoves;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		return moveHistory;
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>grabs the moved block and calls moveGrabbedBlock passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBlock twice</li>
	 * <li>if required, sets is game over to false</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		
		if(moveHistory.isEmpty()) {
			return;
		}
		
		Move lastMove = moveHistory.get(moveHistory.size() - 1);
		
		Block block = lastMove.getBlock();
		Direction dir = lastMove.getDirection();
		
		grabBlockAtCell(block.getFirstRow(), block.getFirstCol());
		moveHistory.remove(moveHistory.size() - 1);
		
		if(dir == LEFT) { 
			moveGrabbedBlock(RIGHT);
			moveHistory.remove(moveHistory.size() - 1);
		} else if(dir == RIGHT) {
			moveGrabbedBlock(LEFT);
			moveHistory.remove(moveHistory.size() - 1);
		} else if(dir == UP) {
			moveGrabbedBlock(DOWN);
			moveHistory.remove(moveHistory.size() - 1);
		} else if(dir == DOWN) {
			moveGrabbedBlock(UP);
			moveHistory.remove(moveHistory.size() - 1);
		}
		
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
