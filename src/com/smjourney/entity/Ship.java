package com.smjourney.entity;


import com.smjourney.audio.SFX;
import com.smjourney.map.Map;
import com.smjourney.map.CellGroup;
import com.smjourney.map.Direction;
import com.smjourney.map.Cell;
import com.smjourney.smjourney.Main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A functioning starship that operates with its own shields, energy capacity, thruster power, and attack power.
 * Shields dictate the attack power that the ship can resist from enemy ships before being destroyed.
 * Energy capacity dictates the extent to which a ship can function (moving, attacking, etc.)
 * Thruster power dictates how far a ship can move on the map using a unit of energy.
 * Attack power dictates the damage that a ship can deal to another ship on the map.
 * 
 * Each ship is automatically assigned randomized stats upon construction based on the type of ship that is constructed.
 * Each ship also has a designated square-size like an Entity
 * Ships can also be designated as enemies for control by artificial intelligence.
 */
public class Ship extends Entity implements Moveable, Destructible, Comparable
{
	
	// Ship stat constants
	/**
	 * Stat reference - Shields
	 */
	public static int SHIELDS = 1;
	/**
	 * Stat reference - Capacity
	 */
	public static int CAPACITY = 2;
	/**
	 * Stat reference - Thrusters
	 */
	public static int THRUSTERS = 3;
	/**
	 * Stat reference - Attack
	 */
	public static int ATTACK = 4;
	/**
	 * Stat reference - Risk
	 */
	public static int RISK = 5;
		
	// Ship properties
	private double maxShields;
	private double maxCapacity;
	private double maxThrusters;
	private double maxAttack;
	private double shields;
	private double capacity;
	private double thrusters;
	private double attack;
	private double riskFactor;
	
	// Miscellaneous
	private boolean isEnemy;
	private Entity target;
	private int attackRange;
	
	private BufferedImage sprite;
	public static final int ENERGY_USE_COEFF = 2;
	
	/**
	 * Constructs a ship of size 1 that is an ally and has weak stats.
	 */
	public Ship()
	{
		super(1);
		this.shields = 10;
		this.maxShields = 10;
		this.capacity = 5;
		this.maxCapacity = 5;
		this.thrusters = 1;
		this.maxThrusters = 1;
		this.attack = 10;
		this.maxAttack = 10;
		this.riskFactor = 1;
		
		automaticallyAssignStats();
		
		this.isEnemy = false;
		this.target = null;
		setEntityID(Entity.SHIP);
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
	 * Constructs a ship of size 1 that is an ally and has weak stats.
	 * @param isEnemy true if ship is to be identified as an enemy, false if not.
	 */
	public Ship(boolean isEnemy)
	{
		super(1);
		this.shields = 10;
		this.maxShields = 10;
		this.capacity = 5;
		this.maxCapacity = 5;
		this.thrusters = 1;
		this.maxThrusters = 1;
		this.attack = 10;
		this.maxAttack = 10;
		this.riskFactor = 1;
		
		automaticallyAssignStats();
		
		this.isEnemy = isEnemy;
		this.target = null;
		setEntityID(Entity.SHIP);
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
	 * Constructs a ship of a certain size with weak stats.
	 * @param size the size of the ship
	 */
	public Ship(int size)
	{
		super(size);
		this.shields = 10;
		this.maxShields = 10;
		this.capacity = 5;
		this.maxCapacity = 5;
		this.thrusters = 1;
		this.maxThrusters = 1;
		this.attack = 10;
		this.maxAttack = 10;
		this.riskFactor = 1;
		
		automaticallyAssignStats();
		
		this.isEnemy = false;
		this.target = null;
		setEntityID(Entity.SHIP);
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
	 * Constructs a ship of a certain size with weak stats.
	 * @param size the size of the ship
	 * @param isEnemy true if ship is to be identified as an enemy, false if not.
	 */
	public Ship(int size, boolean isEnemy)
	{
		super(size);
		this.shields = 10;
		this.maxShields = 10;
		this.capacity = 5;
		this.maxCapacity = 5;
		this.thrusters = 1;
		this.maxThrusters = 1;
		this.attack = 10;
		this.maxAttack = 10;
		this.riskFactor = 1;
		
		automaticallyAssignStats();
		
		this.isEnemy = isEnemy;
		this.target = null;
		setEntityID(Entity.SHIP);
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
	 * Constructs a ship with specific stats and a certain size.
	 * @param shields the number of shields the ship has
	 * @param capacity the energy capacity of the ship
	 * @param thrusters the thruster power of the ship
	 * @param attack the attacking power of the ship
	 * @param size the size of the ship
	 */
	public Ship(int shields, int capacity, int thrusters, int attack, int size)
	{
		super(size);
		this.shields = shields;
		this.maxShields = this.shields;
		this.capacity = capacity;
		this.maxCapacity = this.capacity;
		this.thrusters = thrusters;
		this.maxThrusters = this.thrusters;
		this.attack = attack;
		this.maxAttack = this.attack;
		this.riskFactor = 1;
		
		this.isEnemy = false;
		this.target = null;
		setEntityID(Entity.SHIP);
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
	 * Constructs a ship with specific stats and a certain size.
	 * @param shields the number of shields the ship has
	 * @param capacity the energy capacity of the ship
	 * @param thrusters the thruster power of the ship
	 * @param attack the attacking power of the ship
	 * @param size the size of the ship
	 */
	public Ship(int shields, int capacity, int thrusters, int attack, int size, boolean isEnemy)
	{
		super(size);
		this.shields = shields;
		this.maxShields = this.shields;
		this.capacity = capacity;
		this.maxCapacity = this.capacity;
		this.thrusters = thrusters;
		this.maxThrusters = this.thrusters;
		this.attack = attack;
		this.maxAttack = this.attack;
		this.riskFactor = 1;
		
		this.isEnemy = isEnemy;
		this.target = null;
		setEntityID(Entity.SHIP);
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
	 * Checks if a ship is able to move forward into a certain space or not.
	 * @return canMove true if the ship can move forward, false if not
	 */
	public boolean canMove()
	{	
		Map<EntityPartition> m = this.getMap();
		
		if (m == null)
		{
			return false;
		}
		
		CellGroup target = this.getCellGroup().getAdjacentCellGroup(getDirection());
		
		if (!m.isValid(target))
		{
			return false;
		}
		
		if (getCapacity() <= 0)
		{
			return false;
		}
		
		ArrayList<EntityPartition> entityParts = m.get(target);
		
		boolean canMove = true;
		
		for (int i = 0; i < entityParts.size(); i++)
		{
			EntityPartition entityPart = entityParts.get(i);
			
			if (entityPart != null)
			{
				if (!entityPart.getEntityReference().equals(this))
				{
					canMove = false;
				}
			}
		}
		
		return canMove;
	}
	
	public boolean canMoveTo(CellGroup target)
	{	
		Map<EntityPartition> m = this.getMap();
		
		if (m == null)
		{
			return false;
		}
		
		if (!m.isValid(target))
		{
			return false;
		}
		
		if (getCapacity() <= 0)
		{
			return false;
		}
		
		int row1 = this.getCellGroup().getReferenceCell().getRow();
        int col1 = this.getCellGroup().getReferenceCell().getColumn();
        int row2 = target.getReferenceCell().getRow();
        int col2 = target.getReferenceCell().getColumn();
        
        double energyUsed = (Math.sqrt(Math.pow(row2 - row1, 2) + Math.pow(col2 - col1, 2))) * ENERGY_USE_COEFF;
        
        if (energyUsed/this.getThrusters() > getCapacity())
        {
            return false;
        }
		
		ArrayList<EntityPartition> entityParts = m.get(target);
		
		boolean canMove = true;
		
		for (int i = 0; i < entityParts.size(); i++)
		{
			EntityPartition entityPart = entityParts.get(i);
			
			if (entityPart != null)
			{
				if (!entityPart.getEntityReference().equals(this))
				{
					canMove = false;
				}
			}
		}
		
		return canMove;
	}
	
	/**
	 * Moves the ship to a certain target cell group on the map.
	 * @param target the target cell group
	 * @throws IllegalStateException this ship is not on a map
	 * @throws IllegalStateException the map contains different entities at the target
	 * @throws IllegalStateException the target cell group's size does not correspond to that of the ship's
	 * @throws IllegalArgumentException target is not valid
	 */
	public void moveTo(CellGroup target)
	{
		
		Map<EntityPartition> m = getMap();
		if (m == null)
		{
			throw new IllegalStateException("This ship is not on a map.");
		}
		if (!m.get(getCellGroup()).equals(getPartsOfEntity()))
		{
			throw new IllegalStateException("This map contains different Entities at " + getCellGroup().toString() + " .");
		}
		if(getCellGroup().toArrayList().size() != target.toArrayList().size())
		{
			throw new IllegalStateException("The target cell group's size does not correspond to that of the ship's.");
		}
		if(!m.isValid(target))
		{
			throw new IllegalArgumentException("The target is not valid.");
		}
		if (target.equals(getCellGroup()))
		{
			return;
		}
		
		int row1 = this.getCellGroup().getReferenceCell().getRow();
        int col1 = this.getCellGroup().getReferenceCell().getColumn();
        int row2 = target.getReferenceCell().getRow();
        int col2 = target.getReferenceCell().getColumn();
        
        double energyUsed = (Math.sqrt(Math.pow(row2 - row1, 2) + Math.pow(col2 - col1, 2))) * ENERGY_USE_COEFF;
        
        if (energyUsed/this.getThrusters() > getCapacity())
        {
            return;
        }
		
		CellGroup cellsAhead = getCellGroup().getSeparateCellGroup(target);
		m.remove(cellsAhead);
		
		ArrayList<EntityPartition> otherEntityParts = m.get(cellsAhead);
		ArrayList<Entity> otherEntities = new ArrayList<Entity>();
		
		for (int i = 0; i < otherEntityParts.size(); i++)
		{
			EntityPartition otherEntityPart = otherEntityParts.get(i);
			if (otherEntityPart != null)
			{
				Entity otherEntity = otherEntityPart.getEntityReference();
				if (!otherEntities.contains(otherEntity))
				{
					otherEntities.add(otherEntity);
				}
			}
		}
		
		if (otherEntities.size() > 0)
		{
			for (int i = 0; i < otherEntities.size(); i++)
			{
				Entity otherEntity = otherEntities.get(i);
				otherEntity.removeSelfFromMap();
			}
		}
		
		this.setCellGroup(target);
		updateParts();
		m.add(target, this.getPartsOfEntity());
	}
	
	/**
	 * Moves the ship forward by one unit.
	 * @throws IllegalStateException The ship is not on a map to move on.
	 * @throws IllegalStateException Ship cannot move forward.
	 */
	public void moveForward()
	{
		Map<EntityPartition> m = getMap();
		if (m == null)
		{
			throw new IllegalStateException("The ship is not on a map to move on.");
		}
		CellGroup target = getCellGroup().getAdjacentCellGroup(getDirection());
		if (m.isValid(target) && canMove())
		{
			moveTo(target);
			decrementStat(Ship.CAPACITY, ENERGY_USE_COEFF / getThrusters());
			incrementStat(Ship.RISK, 0.25);
		}
		else
		{
			throw new IllegalStateException("Ship cannot move forward.");
		}
	}
	/**
	 * Carries out the special function of the ship (default: unbalanced attack function) (turns ship to attack if necessary)
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
		setDirection(Direction.toCardinalDirection(directionTowardsTarget));
		
		// Damage the entity
		if (target instanceof Ship)
		{
			Ship shipTarget = (Ship) target;
			shipTarget.decrementStat(Ship.SHIELDS, getAttack());
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
	 * Aligns the ship northward.
	 */
	public void faceNorth()
	{
		setDirection(Direction.NORTH);
	}
	
	/**
	 * Aligns the ship southward.
	 */
	public void faceSouth()
	{
		setDirection(Direction.SOUTH);
	}
	
	/**
	 * Aligns the ship eastward.
	 */
	public void faceEast()
	{
		setDirection(Direction.EAST);
	}
	
	/**
	 * Aligns the ship westward.
	 */
	public void faceWest()
	{
		setDirection(Direction.WEST);
	}
	
	/**
	 * Turns the ship left.
	 */
	public void turnLeft()
	{
		setDirection(getDirection() - 90);
	}
	
	/**
	 * Turns the ship right.
	 */
	public void turnRight()
	{
		setDirection(getDirection() + 90);
	}
	
	/**
	 * Retrieves the attack power of the ship.
	 * @return attack the attack power of the ship
	 */
	public double getAttack() 
	{
		return attack;
	}

	/**
	 * Sets a value of attack power for the ship.
	 * @param newAttack the new value of attack power for the ship.
	 */
	public void setAttack(double newAttack) 
	{
		if (newAttack > maxAttack)
		{
			this.attack = maxAttack;
		}
		else
		if (newAttack < 0)
		{
			this.attack = 0;
		}
		else
		{
			this.attack = newAttack;
		}
	}

	/**
	 * Retrieves the thruster power of the ship.
	 * @return thrusters the thruster power of the ship
	 */
	public double getThrusters() 
	{
		return thrusters;
	}

	/**
	 * Sets a value of thruster power for the ship.
	 * @param newThrusters the new value of thruster power for the ship.
	 */
	public void setThrusters(double newThrusters) 
	{
		if (newThrusters > maxThrusters)
		{
			this.thrusters = maxThrusters;
		}
		else
		if (newThrusters < 0)
		{
			this.thrusters = 0;
		}
		else
		{
			this.thrusters = newThrusters;
		}
	}

	/**
	 * Retrieves the energy capacity of the ship.
	 * @return capacity the energy capacity of the ship
	 */
	public double getCapacity() 
	{
		return capacity;
	}

	/**
	 * Sets a value of energy capacity for the ship.
	 * @param newCapacity the new value of energy capacity for the ship.
	 */
	public void setCapacity(double newCapacity) 
	{
		if (newCapacity > maxCapacity)
		{
			this.capacity = maxCapacity;
		}
		else
		if (newCapacity < 0)
		{
			this.capacity = 0;
		}
		else
		{
			this.capacity = newCapacity;
		}
	}

	/**
	 * Retrieves the number of shields of the ship.
	 * @return shields the number of shields of the ship.
	 */
	public double getShields() 
	{
		return shields;
	}

	/**
	 * Sets a value of number of shields for the ship.
	 * @param newShields the new value of number of shields for the ship.
	 */
	public void setShields(double newShields) 
	{
		if (newShields > maxShields)
		{
			this.shields = maxShields;
		}
		else
		if (newShields < 0)
		{
			this.shields = 0;
		}
		else
		{
			this.shields = newShields;
		}
	}

	/**
	 * Retrieves the MAXIMUM number of shields of the ship.
	 * @return shields the MAXIMUM number of shields of the ship.
	 */
	public double getMaxShields() 
	{
		return maxShields;
	}

	/**
	 * Sets a value of MAXIMUM number of shields for the ship.
	 * @param newMaxShields the new value of MAXIMUM number of shields for the ship.
	 */
	public void setMaxShields(double newMaxShields) 
	{
		this.maxShields = newMaxShields;
	}

	/**
	 * Retrieves the MAXIMUM energy capacity of the ship.
	 * @return capacity the MAXIMUM energy capacity of the ship
	 */
	public double getMaxCapacity()
	{
		return maxCapacity;
	}

	/**
	 * Sets a MAXIMUM value of energy capacity for the ship.
	 * @param newMaxCapacity the new MAXIMUM value of energy capacity for the ship.
	 */
	public void setMaxCapacity(double newMaxCapacity) 
	{
		this.maxCapacity = newMaxCapacity;
	}

	/**
	 * Retrieves the MAXIMUM thruster power of the ship.
	 * @return thrusters the thruster power of the ship
	 */
	public double getMaxThrusters() 
	{
		return maxThrusters;
	}

	/**
	 * Sets a MAXIMUM value of thruster power for the ship.
	 * @param newMaxThrusters the new MAXIMUM value of thruster power for the ship.
	 */
	public void setMaxThrusters(double newMaxThrusters) 
	{
		this.maxThrusters = newMaxThrusters;
	}

	/**
	 * Retrieves the MAXIMUM attack power of the ship.
	 * @return attack the MAXIMUM attack power of the ship
	 */
	public double getMaxAttack()
	{
		return maxAttack;
	}

	/**
	 * Sets a value of MAXIMUM attack power for the ship.
	 * @param newMaxAttack the new value of MAXIMUM attack power for the ship.
	 */
	public void setMaxAttack(double newMaxAttack) 
	{
		this.maxAttack =newMaxAttack;
	}
	
	/**
	 * Retrieves the attack range of the ship.
	 * @return attackRange the attack range of the ship.
	 */
	public int getAttackRange() {
		return attackRange;
	}

	/**
	 * Sets a new attack range for the ship.
	 * @param newAttackRange the new attack range of the ship
	 */
	public void setAttackRange(int newAttackRange) {
		this.attackRange = newAttackRange;
	}

	/**
	 * Destroys the ship.
	 */
	public void beDestroyed()
	{
		setArmorDurability(0);
		super.beDestroyed();
	}
	
	/**
	 * Increments a certain stat by 1 point.
	 * @param stat the stat that is incremented, determined by constants
	 */
	public void incrementStat(int stat)
	{
		if (stat == Ship.ATTACK)
		{
			setAttack(getAttack() + 1);
		}
		if (stat == Ship.CAPACITY)
		{
			setCapacity(getCapacity() + 1);
		}
		if (stat == Ship.SHIELDS)
		{
			setShields(getShields() + 1);
		}
		if (stat == Ship.THRUSTERS)
		{
			setThrusters(getThrusters() + 1);
		}
		if (stat == Ship.RISK)
		{
			setRiskFactor(getRiskFactor() + 1);
		}
	}
	
	/**
	 * Increments a certain stat by a certain number of points.
	 * @param stat the stat that is incremented, determined by constants
	 * @param quantity the number of points that the stat is incremented by.
	 */
	public void incrementStat(int stat, double quantity)
	{
		if (stat == Ship.ATTACK)
		{
			setAttack(getAttack() + quantity);
		}
		if (stat == Ship.CAPACITY)
		{
			setCapacity(getCapacity() + quantity);
		}
		if (stat == Ship.SHIELDS)
		{
			setShields(getShields() + quantity);
		}
		if (stat == Ship.THRUSTERS)
		{
			setThrusters(getThrusters() + quantity);
		}
		if (stat == Ship.RISK)
		{
			setRiskFactor(getRiskFactor() + quantity);
		}
	}
	
	/**
	 * Decrements a certain stat by 1 point.
	 * @param stat the stat that is decremented, determined by constants
	 */
	public void decrementStat(int stat)
	{
		if (stat == Ship.ATTACK)
		{
			setAttack(getAttack() - 1);
		}
		if (stat == Ship.CAPACITY)
		{
			setCapacity(getCapacity() - 1);
		}
		if (stat == Ship.SHIELDS)
		{
			setShields(getShields() - 1);
		}
		if (stat == Ship.THRUSTERS)
		{
			setThrusters(getThrusters() - 1);
		}
		if (stat == Ship.RISK)
		{
			setRiskFactor(getRiskFactor() - 1);
		}
	}
	
	/**
	 * Decrements a certain stat by a certain number of points.
	 * @param stat the stat that is decremented, determined by constants
	 * @param quantity the number of points that the stat is decremented by.
	 */
	public void decrementStat(int stat, double quantity)
	{
		if (stat == Ship.ATTACK)
		{
			setAttack(getAttack() - quantity);
		}
		if (stat == Ship.CAPACITY)
		{
			setCapacity(getCapacity() - quantity);
		}
		if (stat == Ship.SHIELDS)
		{
			setShields(getShields() - quantity);
		}
		if (stat == Ship.THRUSTERS)
		{
			setThrusters(getThrusters() - quantity);
		}
		if (stat == Ship.RISK)
		{
			setRiskFactor(getRiskFactor() - quantity);
		}
	}
	
	/**
	 * Converts a stat into a percentage based on the current stat and the MAXIMUM stat
	 * @param stat the stat converted, determined by constants
	 * @return percentage the percentage of the stat left
	 */
	public double statToPercentage(int stat)
	{
		double maxStat = 0;
		double currentStat = 0;
		double percentage = 0;
		
		if (stat >= 5)
		{
			return percentage;
		}
		
		if (stat == Ship.ATTACK)
		{
			maxStat = getMaxAttack();
			currentStat = getAttack();
			percentage = currentStat / maxStat;
		}
		if (stat == Ship.CAPACITY)
		{
			maxStat = getMaxCapacity();
			currentStat = getCapacity();
			percentage = currentStat / maxStat;
		}
		if (stat == Ship.SHIELDS)
		{
			maxStat = getMaxShields();
			currentStat = getShields();
			percentage = currentStat / maxStat;
		}
		if (stat == Ship.THRUSTERS)
		{
			maxStat = getMaxThrusters();
			currentStat = getThrusters();
			percentage = currentStat / maxStat;
		}
		
		return percentage * 100;
	}
	
	/**
	 * Retrieves whether the ship is an enemy (bad guy) or not
	 * @return whether the ship is an enemy (bad guy) or not
	 */
	public boolean isEnemy()
	{
		return isEnemy;
	}
	
	/**
	 * Retrieves whether the ship is an ally (good guy) or not
	 * @return whether the ship is an ally (good guy) or not
	 */
	public boolean isAlly()
	{
		return !isEnemy;
	}
	
	/**
	 * Retrieves the entity that the ship is targeting to be affected
	 * @return the entity that the ship is targeting to be affected
	 */
	public Entity getTarget()
	{
		return target;
	}
	
	/**
	 * Sets a new entity target for the ship.
	 * @param newTarget the new entity target for the ship.
	 */
	public void setTarget(Entity newTarget)
	{
		this.target = newTarget;
	}
	
	/**
	 * Clears the entity target from the ship data.
	 */
	public void clearTarget()
	{
		setTarget(null);
	}
	
	/**
	 * Automatically assigns stats to ships based on the type of ship is constructed.
	 */
	protected void automaticallyAssignStats()
	{
		int total = 100;
		
		double pShields = 1.0 / 3.0;
		double pCapacity = 1.0 / 3.0;
		
		double qShields = 0;
		double qCapacity = 0;
		double qThrusters = 1;
		double qAttack = 0;
		
		pCapacity += pShields;
		
		for(int i = total; i > 0; i--) 
		{
			double rand = Math.random();
			if (rand > pCapacity) 
			{
				qCapacity++;
			} 
			else
			if (rand > pShields) 
			{
				qShields++;
			} 
			else 
			{
				qAttack++;
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
		
		setAttackRange(3);
		setRiskFactor(getSize());
	}
	
	/**
	 * Retrieves a list of Cells that the Ship can perform attacks on, depending on the attack range of the Ship
	 * @return attackCells the list of Cells that the Ship can attack
	 */
	public ArrayList<Cell> getAttackCells()
	{
		ArrayList<Cell> attackCells = new ArrayList<Cell>();
		
		/* UPDATED: NEW CODE FOR MULTIDIRECTIONAL TARGETTING */
		//Formation of target cell group
		CellGroup thisCellGroup = getCellGroup();
		Cell thisRefCell = thisCellGroup.getReferenceCell();
		Cell biggerRefCell = new Cell(thisRefCell.getRow() - getAttackRange(), thisRefCell.getColumn() - getAttackRange());
		CellGroup targetCellGroup = new CellGroup(biggerRefCell, getSize() + (getAttackRange() * 2));
		
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
		
		attackCells = targetCellGroup.toArrayList();
		
		return attackCells;
	}
	
	/**
	 * Judges if a set target is within the attack range of the ship.
	 * @return true if target is within range, false if not (or if not set)
	 */
	public boolean targetIsWithinRange()
	{
		if (getTarget() == null)
		{
			return false;
		}
		
		ArrayList<Cell> attackCells = getAttackCells();
		
		/* DEFFERED: OLD CODE FOR UNIDIRECTIONAL TARGETTING */
		/*if (getSize() % 2 != 0)
		{
			// Add cells to front-left of ship (ODD NUMBERED SIZE)
			CellGroup transposer = new CellGroup(getCellGroup().getReferenceCell(), attackRange);
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection());
			}
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection() + 270);
			}
			attackCells.addAll(transposer.toArrayList());
			
			// Add cells to front-right of ship
			transposer = new CellGroup(getCellGroup().getReferenceCell(), attackRange);
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection());
			}
			transposer = transposer.getAdjacentCellGroup(getDirection() + Direction.RIGHT);
			attackCells.addAll(transposer.toArrayList());
			
			// Add cells ahead of ship
			transposer = getCellGroup();
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection());
				attackCells.addAll(transposer.toArrayList());
			}
			Collections.sort(attackCells);
		}	
		else
		{
			// Add cells to front-left of ship (EVEN NUMBERED SIZE)
			CellGroup transposer = new CellGroup(getCellGroup().getReferenceCell(), attackRange);
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection());
			}
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection() + 270);
			}
			attackCells.addAll(transposer.toArrayList());
			
			// Add cells to front-right of ship
			transposer = new CellGroup(getCellGroup().getReferenceCell(), attackRange);
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection());
			}
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection() + 90);
			}
			attackCells.addAll(transposer.toArrayList());
			// Add cells ahead of ship
			transposer = getCellGroup();
			for (int i = 0; i < attackRange; i++)
			{
				transposer = transposer.getAdjacentCellGroup(getDirection());
				attackCells.addAll(transposer.toArrayList());
			}
			Collections.sort(attackCells);
		}*/
		// Remove invalid cells
		
		// Judge if target EntityPartition is in range or not (within the attack cells)
		boolean inRange = false;
		
		for (Cell c : attackCells)
		{
			EntityPartition ep = getMap().get(c);
			if (ep == null)
			{
				continue;
			}
			if (ep.getEntityReference().equals(target))
			{
				inRange = true;
			}
		}
		
		return inRange;
	}

	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "Ship[";
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
				try { sprite = ImageIO.read(new File("res/ShipsNorth/blueattackship.png")); } catch (Exception e) {}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/blueattackship.png")); } catch (Exception e) {}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/blueattackship.png")); } catch (Exception e) {}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/blueattackship.png")); } catch (Exception e) {}
			}
		}
		if (isEnemy())
		{
			if (getDirection() == Direction.NORTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsNorth/redattackship.png")); } catch (Exception e) {}
			}
			if (getDirection() == Direction.SOUTH)
			{
				try { sprite = ImageIO.read(new File("res/ShipsSouth/redattackship.png")); } catch (Exception e) {}
			}
			if (getDirection() == Direction.EAST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsEast/redattackship.png")); } catch (Exception e) {}
			}
			if (getDirection() == Direction.WEST)
			{
				try { sprite = ImageIO.read(new File("res/ShipsWest/redattackship.png")); } catch (Exception e) {}
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

	/**
	 * Compares ships by RISK
	 * return riskDifference the difference of risk between ships.
	 */
	public int compareTo(Object otherShip) 
	{
		Ship other = (Ship) otherShip;
		int riskDifference = (int) Math.round(this.getRiskFactor() - other.getRiskFactor());
		return riskDifference;
	}
	
	/**
	 * Retrieves the value of risk that the Ship holds to the opposing side
	 * @return riskFactor the risk value
	 */
	public double getRiskFactor() 
	{
		return riskFactor;
	}

	/**
	 * Sets the value of risk that the Ship holds to the opposing side
	 * @param newRiskFactor the new value of risk
	 */
	public void setRiskFactor(double newRiskFactor) 
	{
		this.riskFactor = newRiskFactor;
	}
	
}

