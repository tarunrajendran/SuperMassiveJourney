package com.smjourney.smjourney;

import java.util.*;
import java.io.*;

import com.smjourney.entity.*;
import com.smjourney.map.Map;
import com.smjourney.map.Cell;
import com.smjourney.map.CellGroup;
import com.smjourney.map.Direction;

/**
 * The galaxy, the world of Supermassive Journey, houses multiple Solar Systems with their own Planets,
 * each containing their own Map data to initiate a battle sequence.
 * 
 * Each and every piece of Map data is hard-coded into nested classes in order to facilitate the uniqueness of each battle.
 * The galaxy also contains a code that monitors and stores the completion statuses of each and every Planet for every won battle.
 * 
 * Galaxies can be constructed with a pre-written completion status "save file".  The methods provided make it easier to load/save memory
 * in regards to the galaxy.
 * 
 * @author Advenio Entertainment  
 *
 */
public class Galaxy 
{
	
	private ArrayList<SolarSystem> solarSystems;
	private String completionCode;
	
	/**
	 * Constructs a galaxy with 6 solar systems, with their planets left incomplete.
	 */
	public Galaxy()
	{
		solarSystems = new ArrayList<SolarSystem>();
		
		SolarSystem1 ss1 = new SolarSystem1();		
		SolarSystem2 ss2 = new SolarSystem2();
		SolarSystem3 ss3 = new SolarSystem3();
		SolarSystem4 ss4 = new SolarSystem4();
		SolarSystem5 ss5 = new SolarSystem5();
		SolarSystem6 ss6 = new SolarSystem6();
		solarSystems.add(ss1);
		solarSystems.add(ss2);
		solarSystems.add(ss3);
		solarSystems.add(ss4);
		solarSystems.add(ss5);
		solarSystems.add(ss6);
		
		generateCompletionCode();
		Main.debugStream.println("BattleManager created");
	}
	
	/**
	 * Constructs a galaxy with 6 solar systems, with the completion status of the planets determined by a code
	 * @param completionCode the code that determines completion status
	 */
	public Galaxy(String completionCode)
	{
		solarSystems = new ArrayList<SolarSystem>();
		
		SolarSystem1 ss1 = new SolarSystem1(completionCode.substring(0, 3));
		SolarSystem2 ss2 = new SolarSystem2(completionCode.substring(3, 7));
		SolarSystem3 ss3 = new SolarSystem3(completionCode.substring(7, 11));
		SolarSystem4 ss4 = new SolarSystem4(completionCode.substring(11, 15));
		SolarSystem5 ss5 = new SolarSystem5(completionCode.substring(15, 19));
		SolarSystem6 ss6 = new SolarSystem6(completionCode.substring(19, 22));
		solarSystems.add(ss1);
		solarSystems.add(ss2);
		solarSystems.add(ss3);
		solarSystems.add(ss4);
		solarSystems.add(ss5);
		solarSystems.add(ss6);
		
		generateCompletionCode();
	}
	
	/**
	 * Generates or updates the completion code of the galaxy.
	 */
	public void generateCompletionCode()
	{
		String newCompletionCode = "";
		
		for (int i = 1; i <= getSolarSystems().size(); i++)
		{
			SolarSystem ss = getSolarSystems().get(i - 1);
			newCompletionCode += i + "";
			
			for (int j = 1; j <= ss.getPlanetCount(); j++)
			{
				boolean completionStatus = this.getCompletionStatus(i, j);
				if (completionStatus == true)
				{
					newCompletionCode += "t";
				}
				else
				{
					newCompletionCode += "f";
				}
			}
			
			completionCode = newCompletionCode;
		}
	}
	
	/**
	 * Retrieves the completion code.
	 * @return completionCode the completion code.
	 */
	public String getCompletionCode()
	{
		generateCompletionCode();
		return completionCode;
	}
	
	/**
	 * Sets a new completion code
	 * @param newCompletionCode the new completion code
	 */
	public void setCompletionCode(String newCompletionCode)
	{
		completionCode = newCompletionCode;
	}
	
	/**
	 * Retrieves a list of SolarSystems in the galaxy.
	 * @return the list of SolarSystems
	 */
	public ArrayList<SolarSystem> getSolarSystems()
	{
		return solarSystems;
	}
	
	/**
	 * Sets a new list of SolarSystems in the galaxy.
	 * @param newSolarSystems the new list of solar systems
	 */
	public void setSolarSystems(ArrayList<SolarSystem> newSolarSystems)
	{
		solarSystems = newSolarSystems;
	}
	
	/**
	 * Retrieves an individual SolarSystem based on the ID provided
	 * @param solarSystemID the ID of the SolarSystem
	 * @return the individual SolarSystem
	 */
	public SolarSystem getSolarSystem(int solarSystemID)
	{
		return getSolarSystems().get(solarSystemID - 1);
	}
	
	/**
	 * Retrieves the number of SolarSystems in the Galaxy.
	 * @return the individual SolarSystem
	 */
	public int getSolarSystemCount()
	{
		return getSolarSystems().size();
	}
	
	public Planet getPlanet(int solarSystemID, int planetID)
	{
		return getSolarSystem(solarSystemID).getPlanet(planetID);
	}
	
	/**
	 * Retrieves a Map from a specific Planet based on the ID of the SolarSystem and the Planet within it.
	 * @param solarSystemID the ID of the SolarSystem
	 * @param planetID the ID of the Planet
	 * @return the Map associated with the Planet
	 */
	public Map<EntityPartition> getMap(int solarSystemID, int planetID)
	{
		SolarSystem ss = getSolarSystem(solarSystemID);
		Planet p = ss.getPlanet(planetID);
		Map<EntityPartition> map = p.getMap();
		
		return map;
	}
	
	/**
	 * Retrieves a Map from a specific Planet based on the ID of the SolarSystem and the Planet within it.
	 * @param solarSystemID the ID of the SolarSystem
	 * @param planetID the ID of the Planet
	 * @return the Map associated with the Planet
	 */
	public boolean getCompletionStatus(int solarSystemID, int planetID)
	{
	if(Main.cheatwin){
		return true;
	}
		SolarSystem ss = getSolarSystem(solarSystemID);
		Planet p = ss.getPlanet(planetID);
		return p.isComplete();
	}
	
	/**
	 * Sets the completion status of a specific Planet based on the ID of the SolarSystem and the Planet within it.
	 * @param solarSystemID the ID of the SolarSystem
	 * @param planetID the ID of the Planet
	 * @param completionStatus the new completion status of the Planet
	 */
	public void setCompletionStatus(int solarSystemID, int planetID, boolean completionStatus)
	{
		SolarSystem ss = getSolarSystem(solarSystemID);
		Planet p = ss.getPlanet(planetID);
		p.setCompletionStatus(completionStatus);
	}
	
	/**
	 * Switches the completion status of a specific Planet based on the ID of the SolarSystem and the Planet within it.
	 * @param solarSystemID the ID of the SolarSystem
	 * @param planetID the ID of the Planet
	 */
	public void changeCompletionStatus(int solarSystemID, int planetID)
	{
		SolarSystem ss = getSolarSystem(solarSystemID);
		Planet p = ss.getPlanet(planetID);
		boolean b = p.isComplete();
		p.setCompletionStatus(!b);
	}
	
	/**
	 * Retrieves the name of a SolarSystem based on its ID in the Galaxy.
	 * @param solarSystemID the ID of the SolarSystem
	 * @return the name of the SolarSystem
	 */
	public String getName(int solarSystemID)
	{
		SolarSystem ss = getSolarSystem(solarSystemID);
		String name = ss.getName();
		return name;
	}
	
	/**
	 * Retrieves the name of a Planet based on its ID in the SolarSystem and the ID of the SolarSystem within the Galaxy.
	 * @param solarSystemID the ID of the SolarSystem
	 * @return the name of the SolarSystem
	 */
	public String getName(int solarSystemID, int planetID)
	{
		SolarSystem ss = getSolarSystem(solarSystemID);
		Planet p = ss.getPlanet(planetID);
		String name = p.getName();
		return name;
	}
	
	/**
	 * Retrieves the number of SolarSystems cleared in the Galaxy
	 * @return the number of SolarSystems cleared
	 */
	public int getNumberOfSystemsCleared()
	{
		int cleared = 0;
		for (SolarSystem ss : getSolarSystems())
		{
			if (ss.isCleared())
			{
				cleared++;
			}
		}
		
		return cleared;
	}
	
	/**
	 * Retrieves the number of Planets cleared in the Galaxy
	 * @return the number of Planets cleared
	 */
	public int getNumberOfPlanetsCompleted()
	{
		ArrayList<SolarSystem> ssList = getSolarSystems();
		ArrayList<Planet> pList = new ArrayList<Planet>();
		
		for (int i = 0; i < getSolarSystemCount(); i++)
		{
			SolarSystem solarSystem = ssList.get(i);
			pList.addAll(solarSystem.getPlanets());
		}
		
		int nCompleted = 0;
		
		for (Planet planet : pList)
		{
			if (planet.isComplete())
			{
				nCompleted++;
			}
		}
		
		return nCompleted;
	}
	
	// Specific SolarSystems and Planets
	
	class SolarSystem1 extends SolarSystem
	{
		public SolarSystem1()
		{
			super();
			this.setName("Elona");
			this.setSystemID(1);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem1.Planet1());
			planets.add(new SolarSystem1.Planet2());
			for (Planet p : planets)
			{
				p.setCompletionStatus(false);
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		public SolarSystem1(String completionCode)
		{
			super();
			this.setName("Elona");
			this.setSystemID(1);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem1.Planet1());
			planets.add(new SolarSystem1.Planet2());
			for (int i = 1; i <= planets.size(); i++)
			{
				Planet p = planets.get(i - 1);
				if (completionCode.charAt(i) == 't')
				{
					p.setCompletionStatus(true);
				}
				else
				if (completionCode.charAt(i) == 'f')
				{
					p.setCompletionStatus(false);
				}
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		class Planet1 extends Planet
		{
			public Planet1()
			{
				super(15, 25);
				this.setName("Lensa");
				this.setPlanetID(1);
				
				add(new ScoutShip(), new Cell(6,7));
				add(new ScoutShip(), new Cell(7,7));
				add(new ScoutShip(), new Cell(8,7));
				add(new ScoutShip(), new Cell(9,7));
                                
  				add(new Mothership(), new Cell(6,3));


				add(new ScoutShip(true), new Cell(6,24));
				add(new ScoutShip(true), new Cell(7,24));
				add(new ScoutShip(true), new Cell(8,24));
				add(new ScoutShip(true), new Cell(9,24));

			}
		} 
		class Planet2 extends Planet
		{
			public Planet2()
			{
				super(15, 25);
				this.setName("Olin");
				this.setPlanetID(2);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));

			}
			
			public void renderPlanet()
			{
				
			}
		}
	}
	
	class SolarSystem2 extends SolarSystem
	{
		public SolarSystem2()
		{
			super();
			this.setName("Ponzy");
			this.setSystemID(2);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem2.Planet1());
			planets.add(new SolarSystem2.Planet2());
			planets.add(new SolarSystem2.Planet3());
			for (Planet p : planets)
			{
				p.setCompletionStatus(false);
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		public SolarSystem2(String completionCode)
		{
			super();
			this.setName("Ponzy");
			this.setSystemID(2);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem2.Planet1());
			planets.add(new SolarSystem2.Planet2());
			planets.add(new SolarSystem2.Planet3());
			for (int i = 1; i <= planets.size(); i++)
			{
				Planet p = planets.get(i - 1);
				if (completionCode.charAt(i) == 't')
				{
					p.setCompletionStatus(true);
				}
				else
				if (completionCode.charAt(i) == 'f')
				{
					p.setCompletionStatus(false);
				}
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		class Planet1 extends Planet
		{
			public Planet1()
			{
				super(20, 30);
				this.setName("Amor");
				this.setPlanetID(1);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));
			}
		}
		class Planet2 extends Planet
		{
			public Planet2()
			{
				super(20, 30);
				this.setName("Thine");
				this.setPlanetID(2);
				
				add(new ScoutShip(), new Cell(6,7));
				add(new ScoutShip(), new Cell(7,7));
				add(new ScoutShip(), new Cell(8,7));
				add(new ScoutShip(), new Cell(9,7));
                                
  				add(new Mothership(), new Cell(6,3));


				add(new ScoutShip(true), new Cell(6,24));
				add(new ScoutShip(true), new Cell(7,24));
				add(new ScoutShip(true), new Cell(8,24));
				add(new ScoutShip(true), new Cell(9,24));

			}
		}
		class Planet3 extends Planet
		{
			public Planet3()
			{
				super(20, 30);
				this.setName("Dwyer");
				this.setPlanetID(3);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));

			}
		}
	}
	
	class SolarSystem3 extends SolarSystem
	{
		public SolarSystem3()
		{
			super();
			this.setName("Chunen");
			this.setSystemID(3);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem3.Planet1());
			planets.add(new SolarSystem3.Planet2());
			planets.add(new SolarSystem3.Planet3());
			for (Planet p : planets)
			{
				p.setCompletionStatus(false);
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		public SolarSystem3(String completionCode)
		{
			super();
			this.setName("Chunen");
			this.setSystemID(3);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem3.Planet1());
			planets.add(new SolarSystem3.Planet2());
			planets.add(new SolarSystem3.Planet3());
			for (int i = 1; i <= planets.size(); i++)
			{
				Planet p = planets.get(i - 1);
				if (completionCode.charAt(i) == 't')
				{
					p.setCompletionStatus(true);
				}
				else
				if (completionCode.charAt(i) == 'f')
				{
					p.setCompletionStatus(false);
				}
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		class Planet1 extends Planet
		{
			public Planet1()
			{
				super(25, 40);
				this.setName("Gruman");
				this.setPlanetID(1);
				
				add(new ScoutShip(), new Cell(6,7));
				add(new ScoutShip(), new Cell(7,7));
				add(new ScoutShip(), new Cell(8,7));
				add(new ScoutShip(), new Cell(9,7));
                                
  				add(new Mothership(), new Cell(6,3));


				add(new ScoutShip(true), new Cell(6,24));
				add(new ScoutShip(true), new Cell(7,24));
				add(new ScoutShip(true), new Cell(8,24));
				add(new ScoutShip(true), new Cell(9,24));
			}
		}
		class Planet2 extends Planet
		{
			public Planet2()
			{
				super(25, 40);
				this.setName("Razh");
				this.setPlanetID(2);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));
			}
		}
		class Planet3 extends Planet
		{
			public Planet3()
			{
				super(25, 40);
				this.setName("Kamdar");
				this.setPlanetID(3);
				
				add(new ScoutShip(), new Cell(6,7));
				add(new ScoutShip(), new Cell(7,7));
				add(new ScoutShip(), new Cell(8,7));
				add(new ScoutShip(), new Cell(9,7));
                                
  				add(new Mothership(), new Cell(6,3));


				add(new ScoutShip(true), new Cell(6,24));
				add(new ScoutShip(true), new Cell(7,24));
				add(new ScoutShip(true), new Cell(8,24));
				add(new ScoutShip(true), new Cell(9,24));
			}
		}
	}
	
	class SolarSystem4 extends SolarSystem
	{
		public SolarSystem4()
		{
			super();
			this.setName("Zajras");
			this.setSystemID(4);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem4.Planet1());
			planets.add(new SolarSystem4.Planet2());
			planets.add(new SolarSystem4.Planet3());
			for (Planet p : planets)
			{
				p.setCompletionStatus(false);
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		public SolarSystem4(String completionCode)
		{
			super();
			this.setName("Zajras");
			this.setSystemID(4);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem4.Planet1());
			planets.add(new SolarSystem4.Planet2());
			planets.add(new SolarSystem4.Planet3());
			for (int i = 1; i <= planets.size(); i++)
			{
				Planet p = planets.get(i - 1);
				if (completionCode.charAt(i) == 't')
				{
					p.setCompletionStatus(true);
				}
				else
				if (completionCode.charAt(i) == 'f')
				{
					p.setCompletionStatus(false);
				}
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		class Planet1 extends Planet
		{
			public Planet1()
			{
				super(30, 45);
				this.setName("Dozin");
				this.setPlanetID(1);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));
			}
		}
		class Planet2 extends Planet
		{
			public Planet2()
			{
				super(30, 45);
				this.setName("Pyrica");
				this.setPlanetID(2);
				
				add(new ScoutShip(), new Cell(6,7));
				add(new ScoutShip(), new Cell(7,7));
				add(new ScoutShip(), new Cell(8,7));
				add(new ScoutShip(), new Cell(9,7));
                                
  				add(new Mothership(), new Cell(6,3));


				add(new ScoutShip(true), new Cell(6,24));
				add(new ScoutShip(true), new Cell(7,24));
				add(new ScoutShip(true), new Cell(8,24));
				add(new ScoutShip(true), new Cell(9,24));
			}
		}
		class Planet3 extends Planet
		{
			public Planet3()
			{
				super(30, 45);
				this.setName("Felan");
				this.setPlanetID(3);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));
			}
		}
	}
	
	class SolarSystem5 extends SolarSystem
	{
		public SolarSystem5()
		{
			super();
			this.setName("Dynock");
			this.setSystemID(5);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem5.Planet1());
			planets.add(new SolarSystem5.Planet2());
			planets.add(new SolarSystem5.Planet3());
			for (Planet p : planets)
			{
				p.setCompletionStatus(false);
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		public SolarSystem5(String completionCode)
		{
			super();
			this.setName("Dynock");
			this.setSystemID(5);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem5.Planet1());
			planets.add(new SolarSystem5.Planet2());
			planets.add(new SolarSystem5.Planet3());
			for (int i = 1; i <= planets.size(); i++)
			{
				Planet p = planets.get(i - 1);
				if (completionCode.charAt(i) == 't')
				{
					p.setCompletionStatus(true);
				}
				else
				if (completionCode.charAt(i) == 'f')
				{
					p.setCompletionStatus(false);
				}
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		class Planet1 extends Planet
		{
			public Planet1()
			{
				super(40, 50);
				this.setName("Chilto");
				this.setPlanetID(1);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));
			}
		}
		class Planet2 extends Planet
		{
			public Planet2()
			{
				super(40, 50);
				this.setName("Dynastar");
				this.setPlanetID(2);
				
				add(new ScoutShip(), new Cell(6,7));
				add(new ScoutShip(), new Cell(7,7));
				add(new ScoutShip(), new Cell(8,7));
				add(new ScoutShip(), new Cell(9,7));
                                
  				add(new Mothership(), new Cell(6,3));


				add(new ScoutShip(true), new Cell(6,24));
				add(new ScoutShip(true), new Cell(7,24));
				add(new ScoutShip(true), new Cell(8,24));
				add(new ScoutShip(true), new Cell(9,24));
			}
		}
		class Planet3 extends Planet
		{
			public Planet3()
			{
				super(40, 50);
				this.setName("Planet 5-3");
				this.setPlanetID(3);
				
				add(new ScoutShip(), new Cell(4, 7));
				add(new ScoutShip(), new Cell(11, 7));
				add(new ScoutShip(), new Cell(5, 8));
				add(new ScoutShip(), new Cell(10, 8));
				add(new ScoutShip(), new Cell(7, 9));
				add(new ScoutShip(), new Cell(8, 9));

				add(new AttackShip(), new Cell(4, 3));
				add(new AttackShip(), new Cell(11, 3));
				add(new AttackShip(), new Cell(12, 3));
				add(new AttackShip(), new Cell(12, 3));
                                
				add(new BeamShip(), new Cell(6, 6));
                                
				add(new Mothership(), new Cell(6, 2));


				add(new ScoutShip(true), new Cell(7, 22));
				add(new ScoutShip(true), new Cell(8, 22));
				add(new ScoutShip(true), new Cell(6, 21));
				add(new ScoutShip(true), new Cell(9, 21));
			}
		}
	}
	
	class SolarSystem6 extends SolarSystem
	{
		public SolarSystem6()
		{
			super();
			this.setName("Solar System 6");
			this.setSystemID(6);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem6.Planet1());
			planets.add(new SolarSystem6.Planet2());
			for (Planet p : planets)
			{
				p.setCompletionStatus(false);
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		public SolarSystem6(String completionCode)
		{
			super();
			this.setName("Solar System 6");
			this.setSystemID(6);
			ArrayList<Planet> planets = new ArrayList<Planet>();
			planets.add(new SolarSystem6.Planet1());
			planets.add(new SolarSystem6.Planet2());
			for (int i = 1; i <= planets.size(); i++)
			{
				Planet p = planets.get(i - 1);
				if (completionCode.charAt(i) == 't')
				{
					p.setCompletionStatus(true);
				}
				else
				if (completionCode.charAt(i) == 'f')
				{
					p.setCompletionStatus(false);
				}
				p.setSolarSystemReference(this);
			}
			this.setPlanets(planets);
		}
		
		class Planet1 extends Planet
		{
			public Planet1()
			{
				super(60, 60);
				this.setName("Planet 6-1");
				this.setPlanetID(1);
			}
		}
		class Planet2 extends Planet
		{
			public Planet2()
			{
				super(60, 60);
				this.setName("Planet 6-2");
				this.setPlanetID(2);
			}
		}
	}
}
