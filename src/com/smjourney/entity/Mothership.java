package com.smjourney.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.smjourney.audio.SFX;
import com.smjourney.map.*;
import com.smjourney.smjourney.Main;

/**
 * This ship type is characterized by being the center of command for the fleet.
 * If this ship is destroyed, the player is defeated.
 * The Mothership has very high shields, no attack, high capacity, and very low thrusters.
 * It can be defended with Support Ships
 */
public class Mothership extends Ship 
{

	private BufferedImage sprite;
	
	private int beamRange;
	
	/**
	 * Constructs the Mothership, which has a size of 4.
	 */
	public Mothership()
	{
		super(4);
		setEntityID(Entity.MOTHERSHIP);
		automaticallyAssignStats();
		updateSprite();
		Main.debugStream.println(this.toString() + " created.");
		if (this.isEnemy())
		{
			setDirection(Direction.WEST);
		}
		else
		{
			setDirection(Direction.EAST);
		}
	}
	
	/**
	 * Constructs the Mothership, which has a size of 4.
	 * @param isEnemy true if ship is to be identified as an enemy, false if not.
	 */
	public Mothership(boolean isEnemy)
	{
		super(4, isEnemy);
		setEntityID(Entity.MOTHERSHIP);
		automaticallyAssignStats();
		updateSprite();
		Main.debugStream.println(this.toString() + " created.");
		if (this.isEnemy())
		{
			setDirection(Direction.WEST);
		}
		else
		{
			setDirection(Direction.EAST);
		}
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
	 * Automatically assigns stats to ships based on the type of ship is constructed.
	 */
	protected void automaticallyAssignStats()
	{
		int total = 145;
		double pShields = 0.6;
		double pCapacity = 0.4;
		double qShields = 0;
		double qCapacity = 0;
		double qAttack = 0;
		double qThrusters = 0.5;
		
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
		
		s += "Mothership[";
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
				try { sprite = ImageIO.read(new File("res/ShipsNorth/bluemothership.png")); } catch (Exception e) {
					Main.errorStream.println("Blue MotherShip North Image could not be loaded");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/bluemothership.png")); } catch (Exception e) {
					Main.errorStream.println("Blue MotherShip South Image could not be loaded");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/bluemothership.png")); } catch (Exception e) {
					Main.errorStream.println("Blue MotherShip East Image could not be loaded");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/bluemothership.png")); } catch (Exception e) {
					Main.errorStream.println("Blue MotherShip West Image could not be loaded");
				}
			}
		}
		if (isEnemy())
		{
			if (getDirection() == Direction.NORTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsNorth/redmothership.png")); } catch (Exception e) {
					Main.errorStream.println("Red MotherShip North Image could not be loaded");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/redmothership.png")); } catch (Exception e) {
					Main.errorStream.println("Red MotherShip South Image could not be loaded");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/redmothership.png")); } catch (Exception e) {
					Main.errorStream.println("Red MotherShip East Image could not be loaded");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/redmothership.png")); } catch (Exception e) {
					Main.errorStream.println("Red MotherShip West Image could not be loaded");
				}
			}
		}
	}
	
	public int getBeamRange()
	{
		return beamRange;
	}
	
	public void setBeamRange(int newBeamRange)
	{
		beamRange = newBeamRange;
	}
	
	/**
	 * Retrieves the DESTRYOED SFX source file path associated with the Entity.
	 * return the DESTROYED SFX
	 */
	public String getDestroyedSfxSrc()
	{
		return SFX.SHIP_DESTROY_LG;
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
		return SFX.SHIP_DAMAGE_LG;
	}
}
	