package com.smjourney.entity;

import com.smjourney.audio.SFX;
import com.smjourney.map.*;
import com.smjourney.smjourney.Main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * This ship type is characterized by its ability to refuel nearby ships.
 * Beam Ships continuously generate energy with nuclear reactors,
 * and have the capacity to beam this energy to an infinite number of ships within range,
 * allowing these nearby ships to attack and move.
 * Beam Ships have low shields, high thrusters, high capacity, and cannot attack, 
 * but their unique ability to regenerate energy makes them very important to keep alive.
 * Fleets have a low amount of these ships.
 */
public class BeamShip extends Ship 
{
	
	private BufferedImage sprite;
	
	private int beamRange;
	
	/**
	 * Constructs the Beam Ship, which has a size of 1 and a beaming range of 3.
	 */
	public BeamShip()
	{
		super(2);
		setEntityID(Ship.BEAMSHIP);
		automaticallyAssignStats();
		updateSprite();
		if (this.isEnemy())
		{
			setDirection(Direction.WEST);
		}
		else
		{
			setDirection(Direction.EAST);
		}
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Constructs the Beam Ship, which has a size of 1 and a beaming range of 3.
	 * @param isEnemy true if ship is to be identified as an enemy, false if not.
	 */
	public BeamShip(boolean isEnemy)
	{
		super(2, isEnemy);
		setEntityID(Ship.BEAMSHIP);
		automaticallyAssignStats();
		updateSprite();
		if (this.isEnemy())
		{
			setDirection(Direction.WEST);
		}
		else
		{
			setDirection(Direction.EAST);
		}
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Sends out a beam of energy to refill surrounding ally ships with energy.
	 */
	public void operate()
	{		
		if (getCapacity() <= 0)
		{
			throw new IllegalStateException("The ship is depleted.");
			//return;
		}
		
		//Formation of target cell group
		CellGroup thisCellGroup = getCellGroup();
		Cell thisRefCell = thisCellGroup.getReferenceCell();
		Cell biggerRefCell = new Cell(thisRefCell.getRow() - getBeamRange(), thisRefCell.getColumn() - getBeamRange());
		CellGroup targetCellGroup = new CellGroup(biggerRefCell, getSize() + (getBeamRange() * 2));
		
		targetCellGroup.trim(thisCellGroup);
		ArrayList<Cell> tcgArray = targetCellGroup.toArrayList();
		ArrayList<Cell> invalidCells = new ArrayList<Cell>();
		
		for (Cell c : tcgArray)
		{
			if (!getMap().isValid(c))
			{
				invalidCells.add(c);
			}
		}
		
		CellGroup invalidCellGroup = new CellGroup(invalidCells);
		targetCellGroup = new CellGroup(tcgArray);
		targetCellGroup.trim(invalidCellGroup);
		
		//Retrieving ally ships
		ArrayList<EntityPartition> entityParts = getMap().get(targetCellGroup);
		ArrayList<Ship> allyShips = new ArrayList<Ship>();
		
		for (EntityPartition ep : entityParts)
		{
			if (ep == null)
			{
				continue;
			}
			Entity e = ep.getEntityReference();	
			if (e instanceof Ship)
			{
				Ship s = (Ship) e;
				if (!s.isEnemy() && !allyShips.contains(s))
				{
					allyShips.add(s);
				}
			}
		}
		
		// Increment capacity of ally ships
		for (Ship ally : allyShips)
		{
			ally.incrementStat(Ship.CAPACITY, 5);
		}
		
		incrementStat(Ship.RISK, 2);
		decrementStat(Ship.CAPACITY, 200);
	}
	
	/**
	 * Retrieves the distance through which energy can be beamed by the Beam Ship.
	 * @return beamRange the distance through which energy can be beamed by the Beam Ship.
	 */
	public int getBeamRange()
	{
		return beamRange;
	}
	
	/**
	 * Sets the distance through which energy can be beamed by the Beam Ship.
	 * @param newBeamRange the new distance through which energy can be beamed by the Beam Ship.
	 */
	public void setBeamRange(int newBeamRange)
	{
		this.beamRange = newBeamRange;
	}
	
	/**
	 * Automatically assigns stats to ships based on the type of ship is constructed.
	 */
	protected void automaticallyAssignStats()
	{
		int total = 90;
		double pShields = 0.3;
		double pCapacity = 0.7;
		double qShields = 0;
		double qCapacity = 0;
		double qAttack = 0;
		double qThrusters = 3;
		
		pCapacity += pShields;
		
		for(int i = total; i > 0; i--) 
		{
			double rand = Math.random();
			if (rand > pCapacity) 
			{
				qAttack++;
			} 
			else
			if (rand > pShields) 
			{
				qShields++;
			} 
			else 
			{
				qCapacity++;
			}
		}
		
		setMaxAttack(qAttack);
		setAttack(qAttack); 

		setMaxCapacity(qCapacity);
		setCapacity(qCapacity); 

		setMaxShields(qShields);
		setShields(qShields); 
		
		setMaxThrusters(qThrusters);
		setThrusters(qThrusters); 
		
		setBeamRange(3);
		setAttackRange(3);
		setRiskFactor(getSize());
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "BeamShip[";
		s += "S:" + getShields() + "/" + getMaxShields() + ", ";
		s += "C:" + getCapacity() + "/" + getMaxCapacity() + ", ";
		s += "T:" + getThrusters() + "/" + getMaxThrusters() + ", ";
		s += "A:" + getAttack() + "/" + getMaxAttack() + ", ";
		if (isEnemy())
		{
			s += "Enemy";
		}
		else
		{
			s += "Ally";
		}
		
		s += "]";
		
		return s;
	}
	
	/**
	 * Retrieves the sprite image associated with the Entity
	 * @return sprite the sprite image
	 */
	public BufferedImage getSprite()
	{
		updateSprite();
		return sprite;
	}
	
	/**
	 * Updates the sprite image associated with the Entity
	 */
	public void updateSprite()
	{
		if (isAlly())
		{
			if (getDirection() == Direction.NORTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsNorth/bluebeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Beam Ship Image North could not be loaded");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/bluebeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Beam Ship Image South could not be loaded");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/bluebeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Beam Ship Image East could not be loaded");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/bluebeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Beam Ship Image West could not be loaded");
				}
			}
		}
		if (isEnemy())
		{
			if (getDirection() == Direction.NORTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsNorth/redbeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Beam Ship Image North could not be loaded");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/redbeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Beam Ship Image South could not be loaded");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/redbeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Beam Ship Image East could not be loaded");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/redbeamship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Beam Ship Image West could not be loaded");
				}
			}
		}
	}
	
	/**
	 * Retrieves the DESTRYOED SFX source file path associated with the Entity.
	 * return the DESTROYED SFX
	 */
	public String getDestroyedSfxSrc()
	{
		return SFX.SHIP_DESTROY_MD;
	}
	
	/**
	 * Retrieves the FIRE SFX source file path associated with the Entity.
	 * return the FIRE SFX
	 */
	public String getFireSfxSrc()
	{
		return SFX.SHIP_BEAM;
	}
	
	/**
	 * Retrieves the DAMAGED SFX source file path associated with the Entity.
	 * return the DAMAGED SFX
	 */
	public String getDamagedSfxSrc()
	{
		return SFX.SHIP_DAMAGE_MD;
	}

}