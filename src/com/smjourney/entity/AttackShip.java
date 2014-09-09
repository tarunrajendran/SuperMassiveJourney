package com.smjourney.entity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.smjourney.audio.SFX;
import com.smjourney.map.*;
import com.smjourney.smjourney.Main;

/**
 * This ship type is characterized by high attack, low shields, medium capacity, and medium thrusters. 
 * These ships can output high amounts of damage with their high attack and medium capacity, 
 * but cannot move places quickly or absorb very much damage. 
 * They are most useful for taking down ships with high shields. 
 * Fleets have a medium number of these ships.
 */
public class AttackShip extends Ship 
{
	
	private BufferedImage sprite;
	
	/**
	 * Constructs the Attack Ship, which has a size of 1.
	 */
	public AttackShip()
	{
		super(1);
		setEntityID(Entity.ATTACKSHIP);
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
	 * Constructs the Attack Ship, which has a size of 1.
	 * @param isEnemy true if ship is to be identified as an enemy, false if not.
	 */
	public AttackShip(boolean isEnemy)
	{
		super(1, isEnemy);
		setEntityID(Entity.ATTACKSHIP);
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
		int total = 80;
		double pShields = 0.2;
		double pCapacity = 0.3;
		double qShields = 0;
		double qCapacity = 0;
		double qAttack = 0;
		double qThrusters = 2;
		
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
		
		setAttackRange(4);
		setRiskFactor(getSize());
	}

	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "AttackShip[";
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
				try { sprite = ImageIO.read(new File("res/ShipsNorth/blueattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Attack Ship Image North could not be loaded");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/blueattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Attack Ship Image South could not be loaded");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/blueattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Attack Ship Image East could not be loaded");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/blueattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Blue Attack Ship Image West could not be loaded");
				}
			}
		}
		if (isEnemy())
		{
			if (getDirection() == Direction.NORTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsNorth/redattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Attack Ship Image North could not be loaded");
				}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/redattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Attack Ship Image South could not be loaded");
				}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/redattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Attack Ship Image East could not be loaded");
				}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/redattackship.png")); } catch (Exception e) {
					Main.errorStream.println("Red Attack Ship Image West could not be loaded");
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
		return SFX.SHIP_DESTROY_SM;
	}
	
	/**
	 * Retrieves the FIRE SFX source file path associated with the Entity.
	 * return the FIRE SFX
	 */
	public String getFireSfxSrc()
	{
		return SFX.SHIP_FIRE_SM;
	}
	
	/**
	 * Retrieves the DAMAGED SFX source file path associated with the Entity.
	 * return the DAMAGED SFX
	 */
	public String getDamagedSfxSrc()
	{
		return SFX.SHIP_DAMAGE_SM;
	}
	
}

