package com.smjourney.map;

import java.util.ArrayList;
import java.util.Collections;

import com.smjourney.smjourney.Main;
/**
 * A groups of cells that are packed in one class for specific functions pertaining to whole groups of cells.
 */
public class CellGroup implements Comparable
{
	
	private ArrayList<Cell> cellArray;
	
	/**
	 * Constructs a group of cells from an ArrayList of Cells.
	 * @param cell_array the ArrayList of Cells to be grouped.
	 */
	public CellGroup(ArrayList<Cell> cell_array)
	{ 
		this.cellArray = cell_array;
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Constructs a SQUARE group of cells using a top-left "reference" cell and a specified size of the square-grouping
	 * @param referenceCell the top-left-most cell of the group
	 * @param size the size of the square-grouping
	 */
	public CellGroup(Cell referenceCell, int size)
	{ 
		ArrayList<Cell> cell_array = new ArrayList<Cell>();
		int row = referenceCell.getRow();
		int column = referenceCell.getColumn();
		
		for (int r = row; r < row + size; r++)
		{
			for (int c = column; c < column + size; c++)
			{
				cell_array.add(new Cell(r, c));
			}
		}
		
		this.cellArray = cell_array;
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Retrieves the top-left-most cell in the group
	 * @return the top-left-most cell in the group
	 */
	public Cell getReferenceCell()
	{
		return cellArray.get(0);
	}
	
	/**
	 * Retrieves the geometric center of the cell group in an array of integers (first element is the row, second element is the column, like a Cell, but incorporating non-integer real numbers)
	 * @return centroid the geometric center of the cell group in the form of an ArrayList
	 */
	public ArrayList<Double> getCentroid()
	{
		double rowCount = 0;
		double colCount = 0;
		
		for (int i = 0; i < cellArray.size(); i++)
		{
			rowCount += cellArray.get(i).getRow();
			colCount += cellArray.get(i).getColumn();
		}
		
		double centerRow = rowCount / (cellArray.size() * 1.0);
		double centerCol = colCount / (cellArray.size() * 1.0);
		
		ArrayList<Double> centroid = new ArrayList<Double>();
		centroid.add(centerRow);
		centroid.add(centerCol);
		
		return centroid;
	}
	
	/**
	 * Retrieves the cell group whose cells are adjacent to the cells in the original cell group in a certain direction
	 * @param direction the direction of the adjacent cell group from the original cell group
	 * @return adjCellGroup the adjacent cell group
	 */
	public CellGroup getAdjacentCellGroup(int direction)
	{
		ArrayList<Cell> newCellArray = new ArrayList<Cell>();
		
		for (int i = 0; i < cellArray.size(); i++)
		{
			Cell newCell = cellArray.get(i).getAdjacentCell(direction);
			newCellArray.add(newCell);
		}
		
		CellGroup adjCellGroup = new CellGroup(newCellArray);
		return adjCellGroup;
	}
	
	/**
	 * Retrieves the cells from a given cell group that are overlapping the original cell group
	 * @param cg the given cell group
	 * @return overlappingCellGroup the overlapping cell group
	 */
	public CellGroup getOverlappingCellGroup(CellGroup cg)
	{
		ArrayList<Cell> otherCellArray = cg.toArrayList();
		ArrayList<Cell> overlappingCells = new ArrayList<Cell>();
		
		for (int i = 0; i < cellArray.size(); i++)
		{
			for (int j = 0; j < otherCellArray.size(); j++)
			{
				if (cellArray.get(i).equals(otherCellArray.get(j)))
				{
					overlappingCells.add(cellArray.get(i));
				}
			}
		}
		
		CellGroup overlappingCellGroup = new CellGroup(overlappingCells);
		return overlappingCellGroup;
	}
	
	/**
	 * Retrieves the cells from a given cell group that are separate from the original cell group
	 * @param cg the given cell group
	 * @return overlappingCellGroup the overlapping cell group
	 */
	public CellGroup getSeparateCellGroup(CellGroup cg)
	{
		ArrayList<Cell> otherCellArray = cg.toArrayList();
		ArrayList<Cell> overlappingCells = this.getOverlappingCellGroup(cg).toArrayList();
		
		ArrayList<Cell> separateCells = new ArrayList<Cell>();
		
		for (int i = 0; i < cellArray.size(); i++)
		{
			separateCells.add(cellArray.get(i));
		}
		
		for (int i = 0; i < otherCellArray.size(); i++)
		{
			if (!separateCells.contains(otherCellArray.get(i)))
			{
				separateCells.add(otherCellArray.get(i));
			}
		}
		
		for (int i = 0; i < overlappingCells.size(); i++)
		{
			separateCells.remove(overlappingCells.get(i));
		}
		
		CellGroup separateCellGroup = new CellGroup(separateCells);
		return separateCellGroup;
	}

	
	/**
	 * Retrieves the direction from the centroid of the original cell group to the centroid of the given cell group
	 * @param cg the given cell group
	 * @return directionTowards the direction towards the centroid of the given cell group
	 */
	public int getDirectionTowards(CellGroup cg)
	{
		ArrayList<Double> thisCentroid = this.getCentroid();
		ArrayList<Double> otherCentroid = cg.getCentroid();
		
		double thisRow = thisCentroid.get(0);
		double thisColumn = thisCentroid.get(1);
		double otherRow = otherCentroid.get(0);
		double otherColumn = otherCentroid.get(1);
		
		double rowDiff = otherRow - thisRow;
		double columnDiff = otherColumn - thisColumn;
		
		double dy = -1 * rowDiff;
		double dx = columnDiff;
		
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
	 * Trims a given cell away from the group of cells
	 * @param c the cell to be trimmed
	 */
	public void trim(Cell c)
	{
		ArrayList<Cell> trimmedArray = cellArray;
		
		if (trimmedArray.contains(c))
		{
			trimmedArray.remove(c);
		}
		
		cellArray = trimmedArray;
	} 
	
	/**
	 * Trims a given group of cells away from the original group of cells
	 * @param cg the group of cells to be trimmed
	 */
	public void trim(CellGroup cg)
	{
		ArrayList<Cell> otherCellArray = cg.toArrayList();
		ArrayList<Cell> trimmedArray = cellArray;
		
		for (int i = 0; i < otherCellArray.size(); i++)
		{
			Cell temp = otherCellArray.get(i);
			
			if (trimmedArray.contains(temp))
			{
				trimmedArray.remove(temp);
			}
		}

		cellArray = trimmedArray;
	}
	
	/**
	 * Stores all cells in the group into an ArrayList and retrieves it.
	 * @return the ArrayList form of the cell group
	 */
	public ArrayList<Cell> toArrayList()
	{
		return cellArray;
	}
	
	/**
	 * Compares one cell group to another based on where the reference cell is located and what the quanitity of cells in the group is.
	 * @param o the group to be compared
	 * @return difference a positive number if superior, negative number if inferior, zero if equal
	 */
	public int compareTo(Object o)
	{
		CellGroup otherCellGroup = (CellGroup) o;
		
		Cell thisRefCell = this.getReferenceCell();
		Cell otherRefCell = otherCellGroup.getReferenceCell();
		
		int difference = 0;
		
		if (thisRefCell.equals(otherRefCell))
		{
			difference = (this.toArrayList().size() - otherCellGroup.toArrayList().size());
		}
		else
		{
			difference = thisRefCell.compareTo(otherRefCell);
		}
		
		return difference;
	}
	
	/**
	 * Checks if two cell groups are identical.
	 * @param o the group to be compared
	 * @return true if identical, false if not
	 */
	public boolean equals(Object o)
	{
		CellGroup otherCellGroup = (CellGroup) o;
		
		boolean equality = false;
		
		if (this.compareTo(otherCellGroup) == 0)
		{
			equality = true;
		}
		
		return equality;
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		ArrayList<Cell> cells = toArrayList();
		
		s += "CellGroup[";
		
		for (Cell cell : cells)
		{
			s += ":" + cell.toString() + ":";
		}
		
		s += "]";
		
		return s;
	}

}
