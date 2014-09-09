package com.smjourney.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.smjourney.audio.*;
import com.smjourney.map.*;
import com.smjourney.smjourney.Main;

/**
 * This ship type is characterized by high shields, low attack, high capacity, and low thrusters.
 * These ships can withstand large amounts of damage with their high shields and also deal decent amounts of damage,
 * but cannot move very far due to an extremely low thrusters stat.
 * They are most useful for defending the Mothership or other strategically important ships.
 * Fleets have a low number of these ships.
 */
public class SupportShip extends Ship 
{

	private BufferedImage sprite;
	
	/**
	 * Constructs the Support Ship, which has a size of 2.
	 */
	public SupportShip()
	{
		super(2);
		setEntityID(Entity.SUPPORTSHIP);
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
	 * Constructs the Support Ship, which has a size of 2.
	 * @param isEnemy true if ship is to be identified as an enemy, false if not.
	 */
	public SupportShip(boolean isEnemy)
	{
		super(2, isEnemy);
		setEntityID(Entity.SUPPORTSHIP);
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
	 * Deals damage to the target ship and clears target from the ship data
	 * @throws IllegalStateException There is no target specified or ship is depleted.
	 * @throws IllegalStateException The target is out of range.
	 */
	/**
	 * Deals damage to the target ship and clears target from the ship data (turns ship if necessary)
	 * @throws IllegalStateException There is no target specified or ship is depleted.
	 * @throws IllegalStateException The target is out of range.
	 */
	public void operate()
	{
		// Retrieve target
		Entity target = getTarget();
		
		if (getCapacity() <= 0)
		{
			throw new IllegalStateException("The ship is depleted.");
		}
		if (target == null)
		{
			throw new IllegalStateException("There is no target specified");
			//return;
		}
		if (!targetIsWithinRange())
		{
			throw new IllegalStateException("The target is out of range.");
			//return;
		}
		
		int directionTowardsTarget = this.getCellGroup().getDirectionTowards(target.getCellGroup());
		this.setDirection(Direction.toCardinalDirection(directionTowardsTarget));
		
		// Damage the entity
		if (target instanceof Ship)
		{
			Ship shipTarget = (Ship) target;
			shipTarget.decrementStat(Ship.SHIELDS, getAttack() / 4);
			// NEEDED? Destroys ship immediately
			if (shipTarget.getShields() <= 0)
			{
				shipTarget.beDestroyed();
			}
		}
		if (target instanceof Debris)
		{
			Debris debrisTarget = (Debris) target;
			debrisTarget.setArmorDurability(debrisTarget.getArmorDurability() - getAttack()); 
			// NEEDED? Destroys debris immediately
			if (debrisTarget.getArmorDurability() <= 0)
			{
				debrisTarget.beDestroyed();
			}
		}
		
		this.decrementStat(Ship.CAPACITY, 1);
		
		clearTarget();
		incrementStat(Ship.RISK, 1);
	}
	
	/**
	 * Automatically assigns stats to ships based on the type of ship is constructed.
	 */
	protected void automaticallyAssignStats()
	{
		int total = 110;
		double pShields = 0.4;
		double pCapacity = 0.4;
		double qShields = 0;
		double qCapacity = 0;
		double qAttack = 0;
		double qThrusters = 1;
		
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
		
		setAttackRange(2);
		setRiskFactor(getSize());
	}

	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "SupportShip[";
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
				try { sprite = ImageIO.read(new File("res/ShipsNorth/bluesupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/bluesupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/bluesupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/bluesupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
				}
			}
		}
		if (isEnemy())
		{
			if (getDirection() == Direction.NORTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsNorth/redsupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/redsupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/redsupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/redsupportship.png")); } catch (Exception e) {
					Main.errorStream.println("Support Ship Image could not be Found");
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
		return SFX.SHIP_FIRE_MD;
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