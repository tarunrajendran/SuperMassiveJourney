package com.smjourney.smjourney;

import java.io.*;
import com.smjourney.map.*;
import com.smjourney.entity.*;

/**
 * Planet is the level. Holds information for Level Rendering.
 * @author Tarun
 */

public class Planet 
{
	
	private boolean isComplete;
	private String name;
	private int sizeX;
	private int sizeY;
	private int planetID;
	private SolarSystem solarSystemReference;
	
	private Map<EntityPartition> map;

	/**
	 * Constructs a planet of a certain size, determining the size of the Map in which the battle will take place.
	 * @param sizeX the horizontal size of the Planet (row count)
	 * @param sizeY the vertical size of the Planet (column count)
	 */
	public Planet(int sizeX, int sizeY)
	{
		this.name = "Planet";
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		map = new Map<EntityPartition>(getSizeX(), getSizeY());
		Main.debugStream.println("Planet "+ name + " has been initialized");
		isComplete = false;
		renderPlanet();
	}
	
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public void setMap(Map<EntityPartition> map) {
		this.map = map;
	}

	/**
	 * Adds an Entity to the Planet's Map on a Cell
	 * @param entity the Entity to be added
	 * @param toCell the cell in which the Entity is to be added
	 */
	public void add(Entity entity, Cell toCell)
	{
		entity.placeSelfOnMap(map, toCell);
	}

	/**
	 * Returns name of Planet
	 * @return Returns the name of the Planet as a String
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Sets a new name for the Planet
	 * @param newName the new name of the Planet
	 */
	public void setName(String newName)
	{
		name = newName;
	}

	/**
	 * Sets a new completion status for the Planet
	 * @param completionStatus the new completion status
	 */
	public void setCompletionStatus(boolean completionStatus) 
	{
		isComplete = completionStatus;
	}
	
	/**
	 * Changes the completion status of the planet to the opposite of what it
	 * is.
	 */
	public void changeCompletionStatus()
	{
		isComplete = !isComplete;
	}

	/**
	 * Retrieves the horizontal size of the Planet (row count)
	 * @return the horizontal size of the Planet (row count)
	 */
	public int getSizeX() 
	{
		return sizeX;
	}

	/**
	 * Retrieves the vertical size of the Planet (column count)
	 * @return the vertical size of the Planet (column count)
	 */
	public int getSizeY() 
	{
		return sizeY;
	}
	
	/**
	 * Retrieves the Map in which the battle will take place.
	 * @return map the map
	 */
	public Map<EntityPartition> getMap()
	{
		return map;
	}
	
	/**
	 * Retrieves the ID of the Planet
	 * @return the Planet ID
	 */
	public int getPlanetID()
	{
		return  planetID;
	}
	
	/**
	 * Sets a new ID for the Planet
	 * @param newPlanetID the new planet ID
	 */
	public void setPlanetID(int newPlanetID)
	{
		planetID = newPlanetID;
	}
	
	/**
	 * Retrieves whether the Planet is complete or not.
	 * @return the completion status of the Planet
	 */
	public boolean isComplete()
	{
		return isComplete;
	}
	
	/**
	 * Retrieves the SolarSystem that the Planet is contained in.
	 * @return the SolarSystem reference of the Planet
	 */
	public SolarSystem getSolarSystemReference()
	{
		return solarSystemReference;
	}
	
	/**
	 * Sets a reference to the SolarSystem that the Planet is contained in.
	 * @param newSolarSystemRef the new SolarSystem reference
	 */
	public void setSolarSystemReference(SolarSystem newSolarSystemRef)
	{
		solarSystemReference = newSolarSystemRef;
	}
	
	public void renderPlanet()
	{
		
	}
}
