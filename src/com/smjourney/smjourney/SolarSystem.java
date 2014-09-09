package com.smjourney.smjourney;

import java.util.*;

/**
 * A star system class containing and managing Planets.  
 * This system is considered "cleared" when every Planet in it is complete.
 * @author Ashwin Chakilum
 *
 */
public class SolarSystem 
{

	private ArrayList<Planet> planets;
	//private ArrayList<Boolean> completionStatuses;
	private String systemCompletionCode;
	private String name;
	private int systemID;
	
	/**
	 * Constructs a SolarSystem that is meant to contain and manage Planets.
	 */
	public SolarSystem()
	{
		this.name = "Solar System";
		planets = new ArrayList<Planet>();
		//completionStatuses = new ArrayList<Boolean>();
	}
	
	/**
	 * Retrieves a list of the Planets within the SolarSystem
	 * @return the list of Planets
	 */
	public ArrayList<Planet> getPlanets()
	{
		return planets;
	}
	
	/**
	 * Sets a list of the Planets to be contained in the SolarSystem
	 * @param newPlanets the list of Planets to be set
	 */
	public void setPlanets(ArrayList<Planet> newPlanets)
	{
		planets = newPlanets;
	}
	
	/**
	 * Retrieves a list of completion statuses for all Planets, in order of organization.
	 * @return completionStatuses the list of completion statuses
	 */
	public ArrayList<Boolean> getCompletionStatuses()
	{ 
		ArrayList<Boolean> completionStatuses = new ArrayList<Boolean>();
		
		for (int i = 1; i <= getPlanetCount(); i++)
		{
			Planet p = this.getPlanet(i);
			completionStatuses.add(p.isComplete());
		}
		
		return completionStatuses;
	}
	
	/**
	 * Sets completion statuses for a group of Planets, in order of organization.
	 * @param newCompletionStatuses the list of completion statuses
	 */
	public void setCompletionStatuses(ArrayList<Boolean> newCompletionStatuses)
	{
		if (newCompletionStatuses.size() != planets.size())
		{
			return;
		}
		
		for (int i = 1; i <= getPlanets().size(); i++)
		{
			Planet p = this.getPlanet(i);
			p.setCompletionStatus(newCompletionStatuses.get(i - 1));
		}
	}
	
	/**
	 * Retrieves the completion code for this SolarSystem, which determines which Planets are complete or not upon reading the code. 
	 * @return systemCompletionCode the completion code
	 */
	public String getSystemCompletionCode()
	{
		return systemCompletionCode;
	}
	
	/**
	 * Sets the completion code for this SolarSystem, which determines which Planets are complete or not upon reading the code. 
	 * @param newCode the new completion code
	 */
	public void setSystemCompletionCode(String newCode)
	{
		systemCompletionCode = newCode;
	}
	
	/**
	 * Retrieves the name of the SolarSystem
	 * @return name the name of the SolarSystem
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Sets the name of the SolarSystem
	 * @param newName the new name of the SolarSystem
	 */
	public void setName(String newName)
	{
		name = newName;
	}
	
	/**
	 * Retrieves the ID of the SolarSystem
	 * @return systemID the ID of the SolarSystem
	 */
	public int getSystemID()
	{
		return systemID;
	}
	
	/**
	 * Sets the ID of the SolarSystem
	 * @param newSystemID the new ID of the SolarSystem
	 */
	public void setSystemID(int newSystemID)
	{
		systemID = newSystemID;
	}
	
	/**
	 * Retrieves a specific planet from the SolarSystem based on its ID
	 * @param planetID the ID of the specific planet
	 */
	public Planet getPlanet(int planetID)
	{
		return getPlanets().get(planetID - 1);
	}
	
	/**
	 * Retrieves the number of planets in the SolarSystem
	 * @return the number of planets in the SolarSystem
	 */
	public int getPlanetCount()
	{
		return getPlanets().size();
	}
	
	/**
	 * Retrieves whether the entire SolarSystem is cleared or not, judged by whether all Planets within the SolarSystem are cleared or not.
	 * @return isCleared the SolarSystem cleared status
	 */
	public boolean isCleared()
	{
		boolean isCleared = true;
		for (Planet p : getPlanets())
		{
			if (!p.isComplete())
			{
				isCleared = false;
			}
		}
		
		return isCleared;
	}
	
}
