package hw3;

import static api.Direction.*;

import api.Direction;
import api.Orientation;

/**
 * Represents a block in the Block Slider game.
 * @author Nathan Turnis
 */
public class Block {
	
	/**
	 * The current firstRow of the block.
	 */
	private int currentRow;
	/**
	 * The original firstRow of the block when it was made.
	 */
	private int origRow;
	/**
	 * The current firstCol of the block.
	 */
	private int currentCol;
	/**
	 * The original firstCol of the block when it was made.
	 */
	private int origCol;
	/**
	 * The length of the block.
	 */
	private int length;
	/**
	 * The orientation of the block. Can either be VERTICAL or HORIZONTAL.
	 */
	private Orientation orientation;

	/**
	 * Constructs a new Block with a specific location relative to the board. The
	 * upper/left most corner of the block is given as firstRow and firstCol. All
	 * blocks are only one cell wide. The length of the block is specified in cells.
	 * The block can either be horizontal or vertical on the board as specified by
	 * orientation.
	 * 
	 * @param firstRow    the first row that contains the block
	 * @param firstCol    the first column that contains the block
	 * @param length      block length in cells
	 * @param orientation either HORIZONTAL or VERTICAL
	 */
	public Block(int firstRow, int firstCol, int length, Orientation orientation) {
		
		currentRow = firstRow;
		currentCol = firstCol;
		this.length = length;
		this.orientation = orientation;
		
		origRow = firstRow;
		origCol = firstCol;
	}

	/**
	 * Resets the position of the block to the original firstRow and firstCol values
	 * that were passed to the constructor during initialization of the the block.
	 */
	public void reset() {
		currentRow = origRow;
		currentCol = origCol;
	}

	/**
	 * Move the blocks position by one cell in the direction specified. The blocks
	 * first column and row should be updated. The method will only move VERTICAL
	 * blocks UP or DOWN and HORIZONTAL blocks RIGHT or LEFT. Invalid movements are
	 * ignored.
	 * 
	 * @param dir direction to move (UP, DOWN, RIGHT, or LEFT)
	 */
	public void move(Direction dir) {
		
		if(orientation == orientation.HORIZONTAL) {
			//move left or right
			if(dir == dir.LEFT) { //move left
				currentCol--;
			} else if(dir == dir.RIGHT) { //move right
				currentCol++;
			}
			
		} else if(orientation == orientation.VERTICAL) {
			//move up or down
			if(dir == dir.UP) { //move up
				currentRow--;
			} else if(dir == dir.DOWN) { //move down
				currentRow++;
			}
		}
		
	}

	/**
	 * Gets the first row of the block on the board.
	 * 
	 * @return first row
	 */
	public int getFirstRow() {
		return currentRow;
	}

	/**
	 * Sets the first row of the block on the board.
	 * 
	 * @param firstRow first row
	 */
	public void setFirstRow(int firstRow) {
		currentRow = firstRow;
	}

	/**
	 * Gets the first column of the block on the board.
	 * 
	 * @return first column
	 */
	public int getFirstCol() {
		return currentCol;
	}

	/**
	 * Sets the first column of the block on the board.
	 * 
	 * @param firstCol first column
	 */
	public void setFirstCol(int firstCol) {
		currentCol = firstCol;
	}

	/**
	 * Gets the length of the block.
	 * 
	 * @return length measured in cells
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Gets the orientation of the block.
	 * 
	 * @return either VERTICAL or HORIZONTAL
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public String toString() {
		return "(row=" + getFirstRow() + ", col=" + getFirstCol() + ", len=" + getLength()
				+ ", ori=" + getOrientation() + ")";
	}
}
