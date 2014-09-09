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
 * A general Entity, and object with an ID and one or more parts, that can be placed on a group of cells within a map to function.
 * It is given a value of armor durability, which dictates the "health" or "HP" of the Entity, and a value for size dictating how many cells
 * the Entity takes up. 
 * 
 * Each Entity is divided into EntityPartitions, located a single cells on the map, with the Entities themselves occupying the GROUP that the individual cells make up.
 */
public class Entity implements Destructible {

	private Map<EntityPartition> map;
	private ArrayList<EntityPartition> partsOfEntity;
	private CellGroup cellGroup;
	private int direction;
	private int entityID;

	
	private BufferedImage sprite;
	
	// Constants for entity ID matching
	/**
	 * Entity ID reference - Default Ship (0)
	 */
	public static int SHIP = 0;
	/**
	 * Entity ID reference - Attack Ship (1)
	 */
	public static int ATTACKSHIP = 1;
	/**
	 * Entity ID reference - Scout Ship (2)
	 */
	public static int SCOUTSHIP = 2;
	/**
	 * Entity ID reference - Beam Ship (3)
	 */
	public static int BEAMSHIP = 3;
	/**
	 * Entity ID reference - Support Ship (4)
	 */
	public static int SUPPORTSHIP = 4;
	/**
	 * Entity ID reference - Mothership (5)
	 */
	public static int MOTHERSHIP = 5;
	/**
	 * Entity ID reference - Default Debris (6)
	 */
	public static int DEBRIS = 6;
	/**
	 * Entity ID reference - Small Debris (7)
	 */
	public static int SMALLDEBRIS = 7;
	/**
	 * Entity ID reference - Medium Debris (8)
	 */
	public static int MEDIUMDEBRIS = 8;
	/**
	 * Entity ID reference - Large Debris (9)
	 */
	public static int LARGEDEBRIS = 9;

	private double armorDurability;
	private double maxArmorDurability;
	private int size;

	/**
	 * Constructs an Entity of default size 1
	 */
	public Entity() {
		direction = Direction.NORTH;
		partsOfEntity = null;
		map = null;
		cellGroup = null;

		this.armorDurability = 100;
		this.armorDurability = 100;
		this.size = 1;
		this.entityID = -1;
		Main.debugStream.println(this.toString() + " created.");
	}

	/**
	 * Constructs an Entity of a certain size
	 * @param size size of entity
	 */
	public Entity(int size) {
		new Entity();
		this.armorDurability = 100;
		this.maxArmorDurability = 100;
		this.size = size;
		Main.debugStream.println(this.toString() + " created.");
	}

	/**
	 * Retrieves the direction that the Entity is facing
	 * @return direction the direction that the Entity is facing
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Retrieves the ID identifying what type of Entity the Entity is
	 * @return entityID the entity's ID
	 */
	public int getEntityID() {
		return entityID;
	}
	
	/**
	 * Sets the ID identifying what type of Entity the Entity is
	 * @return entityID the entity's ID
	 */
	protected void setEntityID(int id) {
		entityID = id;
	}

	/**
	 * Sets a direction for the Entity to face
	 * @param newDirection the new direction that the Entity will face
	 */
	public void setDirection(int newDirection) {
		direction = newDirection % 360;

		if (direction < 0) {
			direction += 360;
		}
	}

	/**
	 * Retrieves the Map in which this Entity is situated
	 * @return map the Map in which this Entity is situated
	 */
	public Map<EntityPartition> getMap() {
		return map;
	}

	/**
	 * Retrieves data for each separate part of the Entity
	 * @return partsOfEntity the data for each separate part of the Entity
	 */
	public ArrayList<EntityPartition> getPartsOfEntity() {
		return partsOfEntity;
	}

	/**
	 * Retrieves the group of cells upon which the Entity is situated
	 * @return the group of cells upon which the Entity is situated
	 */
	public CellGroup getCellGroup() {
		return cellGroup;
	}

	/**
	 * Sets a new group of cells for the Entity to be situated on
	 * @param the new group of cells for the Entity to be situated on
	 */
	protected void setCellGroup(CellGroup newCellGroup) {
		cellGroup = newCellGroup;
	}

	/**
	 * Places the Entity on the Map upon a certain group of cells
	 * @param m the Map in which the Entity will be placed on
	 * @param cg the CellGroup in which the Entity will be placed upon
	 * @throws IllegalStateException the entity is on a map already
	 * @throws IllegalStateException the entity cannot be placed on the map at the specified cell group
	 */
	public void placeSelfOnMap(Map<EntityPartition> m, CellGroup cg) 
	{
		if (map != null) 
		{
			throw new IllegalStateException("This entity is on a map already.");
		}
		if (!m.isValid(cg))
		{
			throw new IllegalStateException("This entity cannot be placed on the map at cell group " + cg.toString());
		}

		ArrayList<EntityPartition> entityParts = m.get(cg);
		ArrayList<Entity> entities = new ArrayList<Entity>();

		for (int i = 0; i < entityParts.size(); i++) {
			if (entityParts.get(i) != null) {
				Entity e = entityParts.get(i).getEntityReference();
				if (!entities.contains(e)) {
					entities.add(e);
				}
			}
		}

		if (entities != null) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).removeSelfFromMap();
			}
		}
		
		setCellGroup(cg);
		this.map = m;
		updateParts();

		for (int i = 0; i < partsOfEntity.size(); i++) {
			m.add(cg, partsOfEntity);
		}
	}

	/**
	 * Places the Entity on the Map upon a certain group of cells given a top-left reference cell and the size of the Entity
	 * @param m the Map in which the Entity will be placed on
	 * @param referenceCell the top-left-most cell of the group of cells in which the Entity will be placed upon
	 */
	public void placeSelfOnMap(Map<EntityPartition> m, Cell referenceCell) 
	{
		CellGroup cg = new CellGroup(referenceCell, size);
		placeSelfOnMap(m ,cg);
	}

	/**
	 * Removes the Entity from the Map
	 * @throws IllegalStateException the entity is not on a map
	 * @throws IllegalStateException the map contains a different entity at the current cell group
	 */
	public void removeSelfFromMap() {
		if (map == null) {
			throw new IllegalStateException(
					"This entity is not on a map currently.");
		}

		if (!map.get(cellGroup).equals(this.getPartsOfEntity())) {
			throw new IllegalStateException(
					"This map contains a different entity at cell group "
							+ cellGroup + ".");
		}

		map.remove(cellGroup);
		map = null;
		cellGroup = null;
		partsOfEntity.clear();
	}

	/**
	 * Retrieves the armor durability of the Entity, the informal "health" or "HP" of the Entity
	 * @return the armor durability of the Entity
	 */
	public double getArmorDurability() {
		return armorDurability;
	}

	/**
	 * Sets a new value of armor durability for the Entity
	 * @param newArmorDurability the new value of armor durability for the Entity
	 */
	public void setArmorDurability(double newArmorDurability) {
		if (newArmorDurability < 0)
		{
			this.armorDurability = 0;
		}
		if (newArmorDurability > getMaxArmorDurability())
		{
			this.armorDurability = getMaxArmorDurability();
		}
		else
		{
			this.armorDurability = newArmorDurability;
		}
	}
	
	public double getMaxArmorDurability()
	{
		return maxArmorDurability;
	}
	
	public void setMaxArmorDurability(double newMaxArmorDurability)
	{
		this.maxArmorDurability = newMaxArmorDurability;
	}

	/**
	 * Retrieves the size of the Entity (as a size x size square)
	 * @return size the size of the Entity
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Destroys the Entity, removing it from the Map that it is currently situated on
	 * @throws IllegalStateException the entity's armor durability level has not been depleted yet
	 */
	public void beDestroyed() {
		if (map == null)
		{
			return;
		}
		if (armorDurability > 0) 
		{
			throw new IllegalStateException(
					"This entity's armor has not been worn out yet.");
		} 
		else
		{
			removeSelfFromMap();
		}
	}
	
	/**
	 * Retrieves whether the entity is destroyed (not on a map) or not.
	 * @return whether the entity is destroyed or not.
	 */
	public boolean isDestroyed()
	{
		return (map == null);
	}
	
	/**
	 * Updates the cell data for every single partition of the Entity
	 */
	protected void updateParts()
	{
		if (map == null || cellGroup == null)
		{
			return;
		}
		
		if (partsOfEntity == null)
		{
			partsOfEntity = new ArrayList<EntityPartition>();
		}
			
		if (partsOfEntity.size() > 0)
		{
			partsOfEntity.clear();
		}
		
		ArrayList<Cell> cells = getCellGroup().toArrayList();
		
		for (int i = 0; i < cells.size(); i++)
		{
			EntityPartition ep = new EntityPartition(this, cells.get(i));
			partsOfEntity.add(ep);
		}
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "Entity[";
		s += "id: " + getEntityID() + ", ";
		s += "size: " + getSize() + "]";
		
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
		if (this instanceof Ship)
		{
			Ship s = (Ship) this;
			if (s.isAlly())
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
			if (s.isEnemy())
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
		if (this instanceof Debris)
		{
			try { sprite = ImageIO.read(new File("res/Debris (Small) (Scaled).png")); } catch (Exception e) {}
		}
		if (this instanceof AttackShip)
		{
			AttackShip s = (AttackShip) this;
			if (s.isAlly())
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
			if (s.isEnemy())
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
		if (this instanceof ScoutShip)
		{
			ScoutShip s = (ScoutShip) this;
			if (s.isAlly())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/bluescoutship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/bluescoutship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/bluescoutship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/bluescoutship.png")); } catch (Exception e) {}
				}
			}
			if (s.isEnemy())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/redscoutship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/redscoutship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/redscoutship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/redscoutship.png")); } catch (Exception e) {}
				}
			}
		}
		if (this instanceof BeamShip)
		{
			BeamShip s = (BeamShip) this;
			if (s.isAlly())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/bluebeamship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/bluebeamship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/bluebeamship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/bluebeamship.png")); } catch (Exception e) {}
				}
			}
			if (s.isEnemy())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/redbeamship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/redbeamship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/redbeamship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/redbeamship.png")); } catch (Exception e) {}
				}
			}
		}
		if (this instanceof SupportShip)
		{
			SupportShip s = (SupportShip) this;
 			if (s.isAlly())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/bluesupportship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/bluesupportship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/bluesupportship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/bluesupportship.png")); } catch (Exception e) {}
				}
			}
			if (s.isEnemy())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/redsupportship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/redsupportship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/redsupportship.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/redsupportship.png")); } catch (Exception e) {}
				}
			}
		}
		if (this instanceof Mothership)
		{
			Mothership s = (Mothership) this;
			if (s.isAlly())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/bluemothership.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/bluemothership.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/bluemothership.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/bluemothership.png")); } catch (Exception e) {}
				}
			}
			if (s.isEnemy())
			{
				if (getDirection() == Direction.NORTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsNorth/redmothership.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.SOUTH)
				{
					try { sprite = ImageIO.read(new File("res/ShipsSouth/redmothership.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.EAST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsEast/redmothership.png")); } catch (Exception e) {}
				}
				if (getDirection() == Direction.WEST)
				{
					try { sprite = ImageIO.read(new File("res/ShipsWest/redmothership.png")); } catch (Exception e) {}
				}
			}
		}
		if (this instanceof SmallDebris)
		{
			try { sprite = ImageIO.read(new File("res/Debris (Small) (Scaled).png")); } catch (Exception e) {}
		}
		if (this instanceof MediumDebris)
		{
			try { sprite = ImageIO.read(new File("res/Debris (Medium) (Scaled).png")); } catch (Exception e) {}
		}
		if (this instanceof LargeDebris)
		{
			try { sprite = ImageIO.read(new File("res/Debris (Large) (Scaled).png")); } catch (Exception e) {}
		}

	}
	
	/**
	 * Retrieves the DESTRYOED SFX source file path associated with the Entity.
	 * return the DESTROYED SFX
	 */
	public String getDestroyedSfxSrc()
	{
		return SFX.DEBRIS_DESTROY_SM;
	}
	
	/**
	 * Retrieves the FIRE SFX source file path associated with the Entity.
	 * return the FIRE SFX
	 */
	public String getFireSfxSrc()
	{
		return SFX.NULL;
	}
	
	/**
	 * Retrieves the DAMAGED SFX source file path associated with the Entity.
	 * return the DAMAGED SFX
	 */
	public String getDamagedSfxSrc()
	{
		return SFX.DEBRIS_DAMAGE_SM;
	}
	
}
