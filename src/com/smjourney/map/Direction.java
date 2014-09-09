package com.smjourney.map;

/**
 * Directional angle constants used to manipulate the direction in which an Entity is facing.
 * Includes relative directional constants (ahead, left, right, etc.), absolute directional constants (compass directions), and methods relative to direction calculation.
 */
public class Direction 
{
	
	// Relative directional constants
	/**
	 * Ahead of the reference
	 */
	public static int AHEAD = 0;
	/**
	 * To the left of the reference
	 */
	public static int LEFT = -90;
	/**
	 * To the left diagonal of the reference
	 */
	public static int HALF_LEFT = -45;
	/**
	 * To the right of the diagonal of the reference
	 */
	public static int RIGHT = 90;
	/**
	 * To the right diagonal of the reference
	 */
	public static int HALF_RIGHT = 45;
	/**
	 * Direction which is faced when the reference turns in a full circle
	 */
	public static int FULL_CIRCLE = 360;
	/**
	 * Direction which is faced when the reference turns in a half-circle
	 */
	public static int HALF_CIRCLE = 180;
	
	// Compass directional constants
	/**
	 * Compass direction - North
	 */
	public static int NORTH = 0;
	/**
	 * Compass direction - Northeast
	 */
	public static int NORTHEAST = 45;
	/**
	 * Compass direction - East
	 */
	public static int EAST = 90;
	/**
	 * Compass direction - Southeast
	 */
	public static int SOUTHEAST = 135;
	/**
	 * Compass direction - South
	 */
	public static int SOUTH = 180;
	/**
	 * Compass direction - Southwest
	 */
	public static int SOUTHWEST = 225;
	/**
	 * Compass direction - West
	 */
	public static int WEST = 270;
	/**
	 * Compass direction - Northwest
	 */
	public static int NORTHWEST = 315;
	
	/**
	 * Approximates a direction (between 0 and 360) into a cardinal direction (North, South, East, or West)
	 * @param direction the direction
	 * @return approximateCardinalDirection the approximated cardinal direction
	 */
	public static int toCardinalDirection(int direction)
	{
		if (direction < 0)
		{
			direction = 360 - direction;
		}
		if (direction >= 360)
		{
			direction = direction % 360;
		}
		
		int approximateCardinalDirection = 0;
		
		if ((direction >= 315 && direction <= 360) || (direction >= 0 && direction < 45))
		{
			approximateCardinalDirection = Direction.NORTH;
		}
		if ((direction >= 45 && direction < 135))
		{
			approximateCardinalDirection = Direction.EAST;
		}
		if ((direction >= 135 && direction < 225))
		{
			approximateCardinalDirection = Direction.SOUTH;
		}
		if ((direction >= 225 && direction < 315))
		{
			approximateCardinalDirection = Direction.WEST;
		}
		
		return approximateCardinalDirection;
	}
	
	/**
	 * Approximates a direction (between 0 and 360) into a cardinal direction (North, South, East, or West)
	 * @param direction the direction
	 * @return approximateCardinalDirection the approximated cardinal direction
	 */
	public static int toCardinalDirection(double direction)
	{
		if (direction < 0)
		{
			direction = 360 - direction;
		}
		if (direction >= 360)
		{
			direction = direction % 360;
		}
		
		int approximateCardinalDirection = 0;
		
		if ((direction >= 315 && direction <= 360) || (direction >= 0 && direction < 45))
		{
			approximateCardinalDirection = Direction.NORTH;
		}
		if ((direction >= 45 && direction < 135))
		{
			approximateCardinalDirection = Direction.EAST;
		}
		if ((direction >= 135 && direction < 225))
		{
			approximateCardinalDirection = Direction.SOUTH;
		}
		if ((direction >= 225 && direction < 315))
		{
			approximateCardinalDirection = Direction.WEST;
		}
		
		return approximateCardinalDirection;
	}

}
