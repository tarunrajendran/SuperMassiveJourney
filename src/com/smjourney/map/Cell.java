package com.smjourney.map;

import com.smjourney.smjourney.Main;

/**
 * A cell which is situated at a certain row or column on the all-encompassing Map.
 */
public class Cell implements Comparable
{
	
	// Instance fields
	private int row;
	private int column;
	
	
	/**
	 * Creates a new Cell reference situated at a specific row and column
	 * @param row the row which the Cell refers to
	 * @param column the column which the Cell refers to
	 */
	public Cell(int row, int column)
	{
		this.row = row;
		this.column = column;
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Gets the column at which the current Cell is situated
	 * @return the column at which the current Cell is situated
	 */
	public int getColumn()
	{
		return column;
	}
	
	
	/**
	 * Gets the row at which the current Cell is situated
	 * @return the row at which the current Cell is situated
	 */
	public int getRow()
	{
		return row;
	}
	
	/**
	 * Gets the Cell adjacent to the current Cell
	 * @param direction the direction towards the adjacent Cell
	 * @return the direction towards the other Cell
	 */
	public Cell getAdjacentCell(int direction)
	{
		if (direction % 45 != 0)
		{
			return null;
		}
		
		int adjacentRow = this.row;
		int adjacentColumn = this.column;
		
		if (direction == Direction.NORTH)
		{
			adjacentRow = this.row - 1;
			adjacentColumn = this.column; 
		}
		if (direction == Direction.NORTHEAST)
		{
			adjacentRow = this.row - 1;
			adjacentColumn = this.column + 1;
		}
		if (direction == Direction.EAST)
		{
			adjacentRow = this.row;
			adjacentColumn = this.column + 1;
		}
		if (direction == Direction.SOUTHEAST)
		{
			adjacentRow = this.row + 1;
			adjacentColumn = this.column + 1; 
		}
		if (direction == Direction.SOUTH)
		{
			adjacentRow = this.row + 1;
			adjacentColumn = this.column;
		}
		if (direction == Direction.SOUTHWEST)
		{
			adjacentRow = this.row + 1;
			adjacentColumn = this.column - 1;
		}
		if (direction == Direction.WEST)
		{
			adjacentRow = this.row;
			adjacentColumn = this.column - 1;
		}
		if (direction == Direction.NORTHWEST)
		{
			adjacentRow = this.row - 1;
			adjacentColumn = this.column - 1;
		}
		
		
		Cell adjacentCell = new Cell(adjacentRow, adjacentColumn);
		return adjacentCell;
	}
	
	/**
	 * Gets direction from current Cell to other Cell
	 * @param cell the other Cell
	 * @return the direction towards the other Cell
	 */
	public int getDirectionTowards(Cell cell)
	{		
		int rowDiff = cell.getRow() - this.getRow();
		int columnDiff = cell.getColumn() - this.getColumn();
		
		int dy = -1 * rowDiff;
		int dx = columnDiff;
		
		// Translate unit circle angle to game direction angle
		int angle = (int) Math.toDegrees(Math.atan2(dy, dx));
		int trueDirection = 90 - angle;
		trueDirection += 45 / 2;
		
		if (trueDirection < 0)
		{
			trueDirection += 360;
		}
		
		int directionTowards = (trueDirection / 45) * 45;
		return directionTowards;
	}
	
	/**
	 * Compares Cells for differences for ordering.
	 * Cells are primarily sorted based on rows (descending),
	 * and secondarily sorted based on columns (rightward).
	 * @param o the other Cell to be compared
	 * @return 0 if equal, 1 if this Cell is greater than other, -1 is this Cell is less than other
	 */
	public int compareTo(Object o)
	{
		Cell otherCell = (Cell) o;
		int diff = 0;
		
		if (this.getRow() < otherCell.getRow())
		{
			diff = -1;
		}
		else
		if (this.getRow() > otherCell.getRow())
		{
			diff = 1;
		}
		else
		if (this.getRow() == otherCell.getRow())
		{
			if (this.getColumn() < otherCell.getColumn())
			{
				diff = -1;
			}
			else
			if (this.getColumn() > otherCell.getColumn())
			{
				diff = 1;
			}
			else
			if (this.getColumn() == otherCell.getColumn())
			{
				diff = 0;
			}
		}
		
		return diff;
	}
	
	/**
	 * Compares Cells for equality
	 * @param o the other Cell to be compared
	 * @return true if equal, false if not
	 */
	public boolean equals(Object o)
	{
		Cell otherCell = (Cell) o;
		boolean areEqual = false;
		
		if (this.getRow() == otherCell.getRow() &&
				this.getColumn() == otherCell.getColumn())
		{
			areEqual = true;
		}
		
		return areEqual;
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "Cell[";
		s += getRow() + ", ";
		s += getColumn() + "]";
		
		return s;
	}
}
