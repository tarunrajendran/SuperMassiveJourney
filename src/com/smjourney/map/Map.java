package com.smjourney.map;

import java.util.ArrayList;
import java.util.Collections;

import com.smjourney.smjourney.Main;

/**
 * A Map that stores all elements situated on each seperate Cell of it and each of their individual data.
 * @param <E> the element that each separate Cell on the Map stores.
 */
public class Map<E> 
{
	
	private Object[][] objectArray;
	
	/**
	 * Constructs a map with a certain number of rows and columns
	 * @param nRows number of rows on the Map
	 * @param nCols number of columns on the Map
	 * @throws IllegalArgumentException number of rows <= 0
	 * @throws IllegalArgumentException number of columns <= 0
	 */
	public Map(int nRows, int nCols)
	{
		if (nRows <= 0)
		{
			throw new IllegalArgumentException("nRows <= 0");
		}
		if (nCols <= 0)
		{
			throw new IllegalArgumentException("nCols <= 0");
		}
		
		objectArray = new Object[nRows][nCols];
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Retrieves the number of rows that the Map has
	 * @return the number of rows that the Map has
	 */
	public int getNumRows()
	{
		return objectArray.length;
	}
	
	/**
	 * Retrieves the number of columns that the Map has
	 * @return the number of columns that the Map has
	 */
	public int getNumColumns()
	{
		return objectArray[0].length;
	}
	
	/**
	 * Checks if a certain Cell is within the bounds of the Map
	 * @param c the Cell to be checked
	 * @return true if the Cell is within bounds, false if not
	 */
	public boolean isValid(Cell c)
	{
		boolean rowIsAboveLowBound = (0 <= c.getRow());
		boolean rowIsBelowHighBound = c.getRow() < getNumRows();
		boolean colIsAboveLowBound = (0 <= c.getColumn());
		boolean colIsBelowHighBound = c.getColumn() < getNumColumns();
		
		return rowIsAboveLowBound && rowIsBelowHighBound && colIsAboveLowBound && colIsBelowHighBound;
	}
	
	/**
	 * Checks if a certain group of Cells is within the bounds of the Map
	 * @param cg the group of Cells to be checked
	 * @return true if the group of Cells is within bounds, false if not
	 */
	public boolean isValid(CellGroup cg)
	{
		boolean isValid = true;
		ArrayList<Cell> objectArray = cg.toArrayList();
		
		for (int i = 0; i < objectArray.size(); i++)
		{
			Cell c = objectArray.get(i);
			if (!isValid(c))
			{
				isValid = false;
			}
		}
		
		return isValid;
	}
	
	/**
	 * Returns a list of occupied Cells in the Map
	 * @return cells the list of occupied cells in the Map
	 */
	public ArrayList<Cell> getOccupiedCells()
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for (int row = 0; row < getNumRows(); row++)
		{
			for (int col = 0; col < getNumColumns(); col++)
			{
				Cell c = new Cell(row, col);
				if (get(c) != null)
				{
					cells.add(c);
				}
			}
		}
		
		Collections.sort(cells);
		return cells;
	}
	
	/**
	 * Adds a specific element to a specific Cell on the Map
	 * @param cell the Cell in which the element is added
	 * @param element the element to be added to the Cell
	 * @return oldOccupant the element that used to occupy the Cell
	 * @throws IllegalArgumentException cell is not valid
	 * @throws NullPointerException element added is null
	 */
	public E add(Cell cell, E element)
	{
		if (!isValid(cell))
		{
			throw new IllegalArgumentException(cell
                    + " is not valid");

		}
		if (element == null)
		{
			throw new NullPointerException("element == null");
		}
		
		E oldOccupant = get(cell);
		objectArray[cell.getRow()][cell.getColumn()] = element;
		return oldOccupant;
	}
	
	/**
	 * Adds a list of elements to a group of Cells in sequence
	 * @param cellGroup the Cell in which the element is added
	 * @param elements the list of elements to be added to the Cell group in sequence
	 * @return oldOccupants the elements that used to occupy the group of Cell
	 * @throws IllegalArgumentException number of elements does not match number of cells in group
	 */
	public ArrayList<E> add(CellGroup cellGroup, ArrayList<E> elements)
	{
		ArrayList<Cell> cellGroupArray = cellGroup.toArrayList();
		ArrayList<E> oldOccupants = new ArrayList<E>();
		
		if (cellGroupArray.size() != elements.size())
		{
			throw new IllegalArgumentException("The number of elements does not equal the number of cells.");
		}
		
		for (int i = 0; i < cellGroupArray.size(); i++)
		{
			E addedElement = this.add(cellGroupArray.get(i), elements.get(i));
			oldOccupants.add(addedElement);
		}
		
		return oldOccupants;
	}
	
	/**
	 * Retrieves the element situated at a specific Cell
	 * @param cell the cell in which the element would be situated
	 * @return occupant the element which is occupying the Cell
	 * @throws IllegalArgumentException cell is not valid
	 */
	public E get(Cell cell)
	{
		if (!isValid(cell))
		{
			throw new IllegalArgumentException(cell + "is not valid.");
		}
		
		E occupant = (E) objectArray[cell.getRow()][cell.getColumn()];
		return occupant;
	}
	
	/**
	 * Retrieves a list of elements situated at a specific group of Cells
	 * @param cellGroup the group of cells in which the elements would be situated
	 * @return occupants the list of elements occupying the Cells in the group
	 */
	public ArrayList<E> get(CellGroup cellGroup)
	{
		ArrayList<Cell> cellGroupArray = cellGroup.toArrayList();
		ArrayList<E> occupants = new ArrayList<E>();
		
		for (int i = 0; i < cellGroupArray.size(); i++)
		{
			E occupant = this.get(cellGroupArray.get(i));
			occupants.add(occupant);
		}
		
		return occupants;
	}
	
	/**
	 * Removes an element from a Cell on the Map
	 * @param cell the Cell from which the element is to be removed
	 * @return toBeRemoved the occupying element that was removed
	 * @throws IllegalArgumentException cell is not valid
	 */
	public E remove(Cell cell)
	{
		if (!isValid(cell))
		{
			throw new IllegalArgumentException(cell
                    + " is not valid");
		}
            
        
        // Remove the object from the grid.
        E toBeRemoved = get(cell);
        objectArray[cell.getRow()][cell.getColumn()] = null;
        return toBeRemoved;

	}
	
	/**
	 * Removes a group of elements from a group of Cells
	 * @param cellGroup the group of Cells from which the elements are to be removed
	 * @return occupants the occupying elements that were removed
	 */
	public ArrayList<E> remove(CellGroup cellGroup)
	{
		ArrayList<Cell> cellGroupArray = cellGroup.toArrayList();
		ArrayList<E> occupants = new ArrayList<E>();
		
		for (int i = 0; i < cellGroupArray.size(); i++)
		{
			E removedOccupant = this.remove(cellGroupArray.get(i));
			occupants.add(removedOccupant);
		}
		
		return occupants;

	}
	
	/**
	 * Retrieves the elements neighboring the given Cell
	 * @param c the given Cell
	 * @return a list of neighboring elements
	 */
	public ArrayList<E> getNeighbors(Cell c)
	{
		ArrayList<E> neighbors = new ArrayList<E>();
		for (Cell neighborC : getOccupiedAdjacentCells(c))
		{
			neighbors.add(get(neighborC));
		}
		return neighbors;
	}
	
	/**
	 * Retrieves the elements neighboring the given group of Cells
	 * @param cg the given group of Cells
	 * @return neighbors the list of neighboring elements
	 */
	public ArrayList<E> getNeighbors(CellGroup cg)
	{
		ArrayList<E> neighbors = new ArrayList<E>();
		for (Cell neighborC : getOccupiedSurroundingCells(cg))
		{
			neighbors.add(get(neighborC));
		}
		return neighbors;
	}
	
	/**
	 * Retrieves Cells adjacent to a given Cell that are also within bounds of the map
	 * @param c the given Cell
	 * @return cells the list of adjecent cells
	 */
	public ArrayList<Cell> getValidAdjacentCells(Cell c)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		int direction = Direction.NORTH;
		for (int i = 0; i < 360 / Direction.HALF_RIGHT; i++)
		{
			Cell neighborCell = c.getAdjacentCell(direction);
			
			if (isValid(neighborCell))
			{
				cells.add(neighborCell);
			}
			direction += Direction.HALF_RIGHT;
		}
		
		Collections.sort(cells);
		return cells;
	}
	
	/**
	 * Retrieves a list of Cells surrounding a given group of Cells that are also within bounds of the map
	 * @param cg the given group of Cells
	 * @return adjCells the list of surrounding Cells
	 */
	public ArrayList<Cell> getValidSurroundingCells(CellGroup cg)
	{
		ArrayList<Cell> adjCells = new ArrayList<Cell>();
		ArrayList<Cell> cellGroupArray = cg.toArrayList();
		
		// Adds possibly valid cells into adjCells
		for (Cell c1 : cellGroupArray)
		{
			ArrayList<Cell> tempAdjCells = getValidAdjacentCells(c1);
			for (Cell c2 : tempAdjCells)
			{
				if (!adjCells.contains(c2) && !cellGroupArray.contains(c2))
				{
					adjCells.add(c2);
				}
			}
		}
		
		// Trims cells that aren't valid
		for (Cell c3 : adjCells)
		{
			if(!isValid(c3))
			{
				adjCells.remove(c3);
			}
		}
		
		Collections.sort(adjCells);
		return adjCells;
	}
	
	/**
	 * Retrieves empty Cells adjacent to a given Cell that are also within bounds of the map
	 * @param c the given Cell
	 * @return cells the list of adjecent cells
	 */
	public ArrayList<Cell> getEmptyAdjacentCells(Cell c)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for (Cell neighborCell : getValidAdjacentCells(c))
		{
			if (get(neighborCell) == null)
			{
				cells.add(neighborCell);
			}
		}
		
		Collections.sort(cells);
		return cells;
	}
	
	/**
	 * Retrieves a list of empty Cells surrounding a given group of Cells that are also within bounds of the map
	 * @param cg the given group of Cells
	 * @return adjCells the list of surrounding Cells
	 */
	public ArrayList<Cell> getEmptySurroundingCells(CellGroup cg)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for (Cell neighborCell : getValidSurroundingCells(cg))
		{
			if (get(neighborCell) == null)
			{
				cells.add(neighborCell);
			}
		}
		
		Collections.sort(cells);
		return cells;
	}
	
	/**
	 * Retrieves occupied Cells adjacent to a given Cell that are also within bounds of the map
	 * @param c the given Cell
	 * @return cells the list of adjecent cells
	 */
	public ArrayList<Cell> getOccupiedAdjacentCells(Cell c)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for (Cell neighborCell : getValidAdjacentCells(c))
		{
			if (get(neighborCell) != null)
			{
				cells.add(neighborCell);
			}
		}
		
		Collections.sort(cells);
		return cells;
	}
	
	/**
	 * Retrieves a list of occupied Cells surrounding a given group of Cells that are also within bounds of the map
	 * @param cg the given group of Cells
	 * @return adjCells the list of surrounding Cells
	 */
	public ArrayList<Cell> getOccupiedSurroundingCells(CellGroup cg)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		
		for (Cell neighborCell : getValidSurroundingCells(cg))
		{
			if (get(neighborCell) != null)
			{
				cells.add(neighborCell);
			}
		}
		
		Collections.sort(cells);
		return cells;
	}
	
	/**
	 * Stores all map data into an ArrayList
	 * @return objectArrayList the ArrayList
	 */
	public ArrayList<E> toArrayList()
	{
		ArrayList<E> objectArrayList = new ArrayList<E>();
		
		for (int i = 0; i < objectArray.length; i++)
		{
			for (int j = 0; j < objectArray[i].length; j++)
			{
				E object = (E) objectArray[i][j];
				objectArrayList.add(object);
			}
		}
		
		return objectArrayList;
	}
	
	public String toString()
	{
		String s = "";
		s += "Map[";
		s += "(" + getNumRows() + ", " + getNumColumns() + ")";
		s += ",Occupancy:" + getOccupiedCells().size();
		s += ",Limit:" + (getNumRows() * getNumColumns()) + "]";
		
		return s;
	}

}
