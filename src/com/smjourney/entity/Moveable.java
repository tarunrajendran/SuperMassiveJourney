package com.smjourney.entity;

import com.smjourney.map.*;

/**
 * Implemented in any object considered moveable.
 */
public interface Moveable 
{
	/**
	 * Checks if the object can move or not
	 * @return true if able to move, false if not
	 */
	boolean canMove();
	/**
	 * Moves the object to a target certain group of cells
	 * @param cg the target cell group
	 */
	void moveTo(CellGroup cg);

}
