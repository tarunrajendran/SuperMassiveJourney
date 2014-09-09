package com.smjourney.entity;

import java.util.ArrayList;

import com.smjourney.entity.*;
import com.smjourney.map.*;

/**
 * This class contains static methods to track specific entities on a map,
 * given a Map parameter that is updated constantly outside of the context of this tracker.
 * @author Ashwin Chakilum
 */
public class EntityTracker 
{
	
	// SHORTCUT METHODS
	
	/**
	 * Retrieves a list of specific entities on the specified map.
	 * @param entityID the ID of the specific entity
	 * @param onMap the specified map
	 * @return entities the list of entities
	 */
	public static ArrayList<Entity> getEntities(int entityID, Map<EntityPartition> onMap)
	{
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		if (entityID == Entity.SHIP)
		{
			entities.addAll(getShips(onMap));
		}
		if (entityID == Entity.ATTACKSHIP)
		{
			entities.addAll(getAttackShips(onMap));
		}
		if (entityID == Entity.SCOUTSHIP)
		{
			entities.addAll(getScoutShips(onMap));
		}
		if (entityID == Entity.BEAMSHIP)
		{
			entities.addAll(getBeamShips(onMap));
		}
		if (entityID == Entity.SUPPORTSHIP)
		{
			entities.addAll(getSupportShips(onMap));
		}
		if (entityID == Entity.MOTHERSHIP)
		{
			entities.addAll(getMotherships(onMap));
		}
		if (entityID == Entity.DEBRIS)
		{
			entities.addAll(getDebris(onMap));
		}
		if (entityID == Entity.SMALLDEBRIS)
		{
			entities.addAll(getSmallDebris(onMap));
		}
		if (entityID == Entity.MEDIUMDEBRIS)
		{
			entities.addAll(getMediumDebris(onMap));
		}
		if (entityID == Entity.LARGEDEBRIS)
		{
			entities.addAll(getLargeDebris(onMap));
		}
		
		return entities;
	}
	
	/**
	 * Retrieves a list of specific ships on the specified map.
	 * @param shipID the ID of the specific ship
	 * @param onMap the specified map
	 * @return ships the list of ships
	 */
	public static ArrayList<Ship> getShips(int shipID, Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = new ArrayList<Ship>();
		
		if (shipID == Entity.SHIP)
		{
			ships.addAll(getShips(onMap));
		}
		if (shipID == Entity.ATTACKSHIP)
		{
			ships.addAll(getAttackShips(onMap));
		}
		if (shipID == Entity.SCOUTSHIP)
		{
			ships.addAll(getScoutShips(onMap));
		}
		if (shipID == Entity.BEAMSHIP)
		{
			ships.addAll(getBeamShips(onMap));
		}
		if (shipID == Entity.SUPPORTSHIP)
		{
			ships.addAll(getSupportShips(onMap));
		}
		if (shipID == Entity.MOTHERSHIP)
		{
			ships.addAll(getMotherships(onMap));
		}
		
		return ships;
	}
	
	/**
	 * Retrieves a list of specific ships on the specified map.
	 * @param shipID the ID of the specific ship
	 * @param isEnemy whether the ship to be listed should be an enemy or not
	 * @param onMap the specified map
	 * @return ships the list of ships
	 */
	public static ArrayList<Ship> getShips(int shipID, boolean isEnemy, Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = new ArrayList<Ship>();
		
		if (isEnemy)
		{
			ships = getEnemyShips(shipID, onMap);
		}
		else
		{
			ships = getAlliedShips(shipID, onMap);
		}
		
		return ships;
	}
	
	/**
	 * Retrieves a list of specific allied ships on the specified map.
	 * @param shipID the ID of the specific ship
	 * @param onMap the specified map
	 * @return alliedShips the list of allied ships
	 */
	public static ArrayList<Ship> getAlliedShips(int shipID, Map<EntityPartition> onMap)
	{
		ArrayList<Ship> alliedShips = new ArrayList<Ship>();
		
		if (shipID == Entity.SHIP)
		{
			alliedShips.addAll(getAlliedShips(onMap));
		}
		if (shipID == Entity.ATTACKSHIP)
		{
			alliedShips.addAll(getAlliedAttackShips(onMap));
		}
		if (shipID == Entity.SCOUTSHIP)
		{
			alliedShips.addAll(getAlliedScoutShips(onMap));
		}
		if (shipID == Entity.BEAMSHIP)
		{
			alliedShips.addAll(getAlliedBeamShips(onMap));
		}
		if (shipID == Entity.SUPPORTSHIP)
		{
			alliedShips.addAll(getAlliedSupportShips(onMap));
		}
		if (shipID == Entity.MOTHERSHIP)
		{
			alliedShips.addAll(getAlliedMotherships(onMap));
		}
		
		return alliedShips;
	}
	
	/**
	 * Retrieves a list of specific enemy ships on the specified map.
	 * @param shipID the ID of the specific ship
	 * @param onMap the specified map
	 * @return enemyShips the list of enemy ships
	 */
	public static ArrayList<Ship> getEnemyShips(int shipID, Map<EntityPartition> onMap)
	{
		ArrayList<Ship> enemyShips = new ArrayList<Ship>();
		
		if (shipID == Entity.SHIP)
		{
			enemyShips.addAll(getEnemyShips(onMap));
		}
		if (shipID == Entity.ATTACKSHIP)
		{
			enemyShips.addAll(getEnemyAttackShips(onMap));
		}
		if (shipID == Entity.SCOUTSHIP)
		{
			enemyShips.addAll(getEnemyScoutShips(onMap));
		}
		if (shipID == Entity.BEAMSHIP)
		{
			enemyShips.addAll(getEnemyBeamShips(onMap));
		}
		if (shipID == Entity.SUPPORTSHIP)
		{
			enemyShips.addAll(getEnemySupportShips(onMap));
		}
		if (shipID == Entity.MOTHERSHIP)
		{
			enemyShips.addAll(getEnemyMotherships(onMap));
		}
		
		return enemyShips;
	}
	
	/**
	 * Retrieves a list of specific debris on the specified map.
	 * @param debrisID the ID of the specific debris
	 * @param onMap the specified map
	 * @return debris the list of debris
	 */
	public static ArrayList<Debris> getDebris(int debrisID, Map<EntityPartition> onMap)
	{
		ArrayList<Debris> debris = new ArrayList<Debris>();
		
		if (debrisID == Entity.DEBRIS)
		{
			debris.addAll(getDebris(onMap));
		}
		if (debrisID == Entity.SMALLDEBRIS)
		{
			debris.addAll(getSmallDebris(onMap));
		}
		if (debrisID == Entity.MEDIUMDEBRIS)
		{
			debris.addAll(getMediumDebris(onMap));
		}
		if (debrisID == Entity.LARGEDEBRIS)
		{
			debris.addAll(getLargeDebris(onMap));
		}
		
		return debris;
	}
	
	/**
	 * Retrieves a list of entity IDs of a specific type of entity on the specified map.
	 * @param entityID the ID of the general entity classification
	 * @param onMap the specified map
	 * @return entityIDs the list of entity IDs
	 */
	public static ArrayList<Integer> getEntityIDs(int entityID, Map<EntityPartition> onMap)
	{
		ArrayList<Entity> entities = getEntities(entityID, onMap);
		ArrayList<Integer> entityIDs = new ArrayList<Integer>();
		
		for (Entity e : entities)
		{
			entityIDs.add(e.getEntityID());
		}
		
		return entityIDs;
	}
	
	/**
	 * Retrieves the number of a specific entities on the specified map.
	 * @param entityID the ID of the general entity classification
	 * @param onMap the specified map
	 * @return entityIDs the list of entity IDs
	 */
	public static int getCountOf(int entityID, Map<EntityPartition> onMap)
	{
		return getEntities(entityID, onMap).size();
	}
	
	/**
	 * Retrieves the number of a specific ships on the specified map.
	 * @param entityID the ID of the general entity classification
	 * @param isEnemy whether the type of ship to be counted should be an enemy or not
	 * @param onMap the specified map
	 * @return entityIDs the list of entity IDs
	 */
	public static int getShipCountOf(int entityID, boolean isEnemy, Map<EntityPartition> onMap)
	{	
		if (isEnemy)
		{
			return getEnemyShips(entityID, onMap).size();
		}
		else
		{
			return getAlliedShips(entityID, onMap).size();
		}
	}
	
	/**
	 * Retrieves a list of CellGroups affiliated with each specific entity on the specific map
	 * @param entityID the ID of the general entity classification
	 * @param onMap the specified map
	 * @return entityIDs the list of entity IDs
	 */
	public static ArrayList<CellGroup> getCellGroups(int entityID, Map<EntityPartition> onMap)
	{
		ArrayList<Entity> entities = getEntities(entityID, onMap);
		ArrayList<CellGroup> cellGroups = new ArrayList<CellGroup>();
		
		for (Entity e : entities)
		{
			cellGroups.add(e.getCellGroup());
		}
		
		return cellGroups;
	}
	
	public static ArrayList<Cell> getReferenceCells(int entityID, Map<EntityPartition> onMap)
	{
		ArrayList<CellGroup> cellGroups = getCellGroups(entityID, onMap);
		ArrayList<Cell> refCells = new ArrayList<Cell>();
		
		for (CellGroup cg : cellGroups)
		{
			refCells.add(cg.getReferenceCell());
		}
		
		return refCells;
	}
	
	// SPECIFIC METHODS
	
	/**
	 * Retrieves a list of entities on the specified map.
	 * @param onMap the specified map
	 * @return entities the list of entities
	 */
	public static ArrayList<Entity> getEntities(Map<EntityPartition> onMap)
	{
		ArrayList<EntityPartition> entityParts = onMap.toArrayList();
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		for (EntityPartition ep : entityParts)
		{
			if (ep == null)
			{
				continue;
			}
			else
			{
				Entity e = ep.getEntityReference();
				if (!entities.contains(e))
				{
					entities.add(e);
				}
			}
		}
		
		return entities;
	}
	
	/**
	 * Retrieves a list of entity IDs on the specified map.
	 * @param onMap the specified map
	 * @return entityIDs the list of entity IDs
	 */
	public static ArrayList<Integer> getEntityIDs(Map<EntityPartition> onMap)
	{
		ArrayList<Entity> entities = getEntities(onMap);
		ArrayList<Integer> entityIDs = new ArrayList<Integer>();
		
		for (Entity e : entities)
		{
			entityIDs.add(e.getEntityID());
		}
		
		return entityIDs;
	}
	
	/**
	 * Retrieves the number of entities on the specified map.
	 * @param onMap the specified map
	 * @return the number of entities
	 */
	public static int getEntityCount(Map<EntityPartition> onMap)
	{
		return getEntities(onMap).size();
	}
	
	/**
	 * Retrieves a list of ships on the specified map.
	 * @param onMap the specified map
	 * @return ships the list of ships
	 */
	public static ArrayList<Ship> getShips(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = new ArrayList<Ship>();
		ArrayList<Entity> entities = getEntities(onMap);
		
		for (Entity e : entities)
		{
			if (e instanceof Ship)
			{
				Ship s = (Ship) e;
				ships.add(s);
			}
		}
		
		return ships;
	}
	
	/**
	 * Retrieves a list of allied ships on the specified map.
	 * @param onMap the specified map
	 * @return alliedShips the list of allied ships
	 */
	public static ArrayList<Ship> getAlliedShips(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> alliedShips = new ArrayList<Ship>();
		ArrayList<Ship> ships = getShips(onMap);
		
		for (Ship s : ships)
		{
			if (s.isAlly())
			{
				alliedShips.add(s);
			}
		}
		
		return alliedShips;
	}
	
	/**
	 * Retrieves a list of enemy ships on the specified map.
	 * @param onMap the specified map
	 * @return enemyShips the list of enemy ships
	 */
	public static ArrayList<Ship> getEnemyShips(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> enemyShips = new ArrayList<Ship>();
		ArrayList<Ship> ships = getShips(onMap);
		
		for (Ship s : ships)
		{
			if (s.isEnemy())
			{
				enemyShips.add(s);
			}
		}
		
		return enemyShips;
	}
	
	/**
	 * Retrieves the number of ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of ships
	 */
	public static int getShipCount(Map<EntityPartition> onMap)
	{
		return getShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of allied ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of allied ships
	 */
	public static int getAlliedShipCount(Map<EntityPartition> onMap)
	{
		return getAlliedShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of enemy ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of enemy ships
	 */
	public static int getEnemyShipCount(Map<EntityPartition> onMap)
	{
		return getEnemyShips(onMap).size();
	}
	
	/**
	 * Retrieves a list of ship IDs on the specified map.
	 * @param onMap the specified map
	 * @return shipIDs the list of ship IDs
	 */
	public static ArrayList<Integer> getShipIDs(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getShips(onMap);
		ArrayList<Integer> shipIDs = new ArrayList<Integer>();
		
		for (Ship s : ships)
		{
			shipIDs.add(s.getEntityID());
		}
		
		return shipIDs;
	}
	
	/**
	 * Retrieves a list of allied ship IDs on the specified map.
	 * @param onMap the specified map
	 * @return shipIDs the list of allied ship IDs
	 */
	public static ArrayList<Integer> getAlliedShipIDs(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getAlliedShips(onMap);
		ArrayList<Integer> shipIDs = new ArrayList<Integer>();
		
		for (Ship s : ships)
		{
			shipIDs.add(s.getEntityID());
		}
		
		return shipIDs;
	}
	
	/**
	 * Retrieves a list of enemy ship IDs on the specified map.
	 * @param onMap the specified map
	 * @return shipIDs the list of enemy ship IDs
	 */
	public static ArrayList<Integer> getEnemyShipIDs(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getEnemyShips(onMap);
		ArrayList<Integer> shipIDs = new ArrayList<Integer>();
		
		for (Ship s : ships)
		{
			shipIDs.add(s.getEntityID());
		}
		
		return shipIDs;
	}
	
	/**
	 * Retrieves a list of attack ships on the specified map.
	 * @param onMap the specified map
	 * @return attackShips the list of attack ships
	 */
	public static ArrayList<AttackShip> getAttackShips(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getShips(onMap);
		ArrayList<AttackShip> attackShips = new ArrayList<AttackShip>();
		
		for (Ship s : ships)
		{
			if (s instanceof AttackShip)
			{
				AttackShip as = (AttackShip) s;
				attackShips.add(as);
			}
		}
		
		return attackShips;
	}
	
	/**
	 * Retrieves a list of allied attack ships on the specified map.
	 * @param onMap the specified map
	 * @return alliedAttackShips the list of allied attack ships
	 */
	public static ArrayList<AttackShip> getAlliedAttackShips(Map<EntityPartition> onMap)
	{
		ArrayList<AttackShip> attackShips = getAttackShips(onMap);
		ArrayList<AttackShip> alliedAttackShips = new ArrayList<AttackShip>();
		
		for (AttackShip as : attackShips)
		{
			if (as.isAlly())
			{
				alliedAttackShips.add(as);
			}
		}
		
		return alliedAttackShips;
	}
	
	/**
	 * Retrieves a list of enemy attack ships on the specified map.
	 * @param onMap the specified map
	 * @return enemyAttackShips the list of enemy attack ships
	 */
	public static ArrayList<AttackShip> getEnemyAttackShips(Map<EntityPartition> onMap)
	{
		ArrayList<AttackShip> attackShips = getAttackShips(onMap);
		ArrayList<AttackShip> enemyAttackShips = new ArrayList<AttackShip>();
		
		for (AttackShip as : attackShips)
		{
			if (as.isEnemy())
			{
				enemyAttackShips.add(as);
			}
		}
		
		return enemyAttackShips;
	}
	
	/**
	 * Retrieves the number of attack ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of attack ships
	 */
	public static int getAttackShipCount(Map<EntityPartition> onMap)
	{
		return getAttackShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of allied attack ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of allied attack ships
	 */
	public static int getAlliedAttackShipCount(Map<EntityPartition> onMap)
	{
		return getAlliedAttackShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of enemy attack ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of enemy attack ships
	 */
	public static int getEnemyAttackShipCount(Map<EntityPartition> onMap)
	{
		return getEnemyAttackShips(onMap).size();
	}
	
	/**
	 * Retrieves a list of scout ships on the specified map.
	 * @param onMap the specified map
	 * @return scoutShips the list of scout ships
	 */
	public static ArrayList<ScoutShip> getScoutShips(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getShips(onMap);
		ArrayList<ScoutShip> scoutShips = new ArrayList<ScoutShip>();
		
		for (Ship s : ships)
		{
			if (s instanceof ScoutShip)
			{
				ScoutShip ss = (ScoutShip) s;
				scoutShips.add(ss);
			}
		}
		
		return scoutShips;
	}
	
	/**
	 * Retrieves a list of allied scout ships on the specified map.
	 * @param onMap the specified map
	 * @return alliedScoutShips the list of allied scout ships
	 */
	public static ArrayList<ScoutShip> getAlliedScoutShips(Map<EntityPartition> onMap)
	{
		ArrayList<ScoutShip> scoutShips = getScoutShips(onMap);
		ArrayList<ScoutShip> alliedScoutShips = new ArrayList<ScoutShip>();
		
		for (ScoutShip ss : scoutShips)
		{
			if (ss.isAlly())
			{
				alliedScoutShips.add(ss);
			}
		}
		
		return alliedScoutShips;
	}
	
	/**
	 * Retrieves a list of enemy scout ships on the specified map.
	 * @param onMap the specified map
	 * @return enemyScoutShips the list of enemy scout ships
	 */
	public static ArrayList<ScoutShip> getEnemyScoutShips(Map<EntityPartition> onMap)
	{
		ArrayList<ScoutShip> scoutShips = getScoutShips(onMap);
		ArrayList<ScoutShip> enemyScoutShips = new ArrayList<ScoutShip>();
		
		for (ScoutShip ss : scoutShips)
		{
			if (ss.isEnemy())
			{
				enemyScoutShips.add(ss);
			}
		}
		
		return enemyScoutShips;
	}
	
	/**
	 * Retrieves the number of scout ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of scout ships
	 */
	public static int getScoutShipCount(Map<EntityPartition> onMap)
	{
		return getScoutShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of allied scout ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of allied scout ships
	 */
	public static int getAlliedScoutShipCount(Map<EntityPartition> onMap)
	{
		return getAlliedScoutShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of enemy scout ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of enemy scout ships
	 */
	public static int getEnemyScoutShipCount(Map<EntityPartition> onMap)
	{
		return getEnemyScoutShips(onMap).size();
	}
	
	/**
	 * Retrieves a list of beam ships on the specified map.
	 * @param onMap the specified map
	 * @return beamShips the list of beam ships
	 */
	public static ArrayList<BeamShip> getBeamShips(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getShips(onMap);
		ArrayList<BeamShip> beamShips = new ArrayList<BeamShip>();
		
		for (Ship s : ships)
		{
			if (s instanceof BeamShip)
			{
				BeamShip bs = (BeamShip) s;
				beamShips.add(bs);
			}
		}
		
		return beamShips;
	}
	
	/**
	 * Retrieves a list of allied beam ships on the specified map.
	 * @param onMap the specified map
	 * @return alliedBeamShips the list of allied beam ships
	 */
	public static ArrayList<BeamShip> getAlliedBeamShips(Map<EntityPartition> onMap)
	{
		ArrayList<BeamShip> beamShips = getBeamShips(onMap);
		ArrayList<BeamShip> alliedBeamShips = new ArrayList<BeamShip>();
		
		for (BeamShip bs : beamShips)
		{
			if (bs.isAlly())
			{
				alliedBeamShips.add(bs);
			}
		}
		
		return alliedBeamShips;
	}
	
	/**
	 * Retrieves a list of enemy beam ships on the specified map.
	 * @param onMap the specified map
	 * @return enemyBeamShips the list of enemy beam ships
	 */
	public static ArrayList<BeamShip> getEnemyBeamShips(Map<EntityPartition> onMap)
	{
		ArrayList<BeamShip> beamShips = getBeamShips(onMap);
		ArrayList<BeamShip> enemyBeamShips = new ArrayList<BeamShip>();
		
		for (BeamShip bs : beamShips)
		{
			if (bs.isEnemy())
			{
				enemyBeamShips.add(bs);
			}
		}
		
		return enemyBeamShips;
	}
	
	/**
	 * Retrieves the number of beam ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of beam ships
	 */
	public static int getBeamShipCount(Map<EntityPartition> onMap)
	{
		return getBeamShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of allied beam ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of allied beam ships
	 */
	public static int getAlliedBeamShipCount(Map<EntityPartition> onMap)
	{
		return getAlliedBeamShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of enemy beam ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of enemy beam ships
	 */
	public static int getEnemyBeamShipCount(Map<EntityPartition> onMap)
	{
		return getEnemyBeamShips(onMap).size();
	}
	
	/**
	 * Retrieves a list of support ships on the specified map.
	 * @param onMap the specified map
	 * @return supportShips the list of support ships
	 */
	public static ArrayList<SupportShip> getSupportShips(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getShips(onMap);
		ArrayList<SupportShip> supportShips = new ArrayList<SupportShip>();
		
		for (Ship s : ships)
		{
			if (s instanceof SupportShip)
			{
				SupportShip ss = (SupportShip) s;
				supportShips.add(ss);
			}
		}
		
		return supportShips;
	}
	
	/**
	 * Retrieves a list of allied support ships on the specified map.
	 * @param onMap the specified map
	 * @return alliedSupportShips the list of allied support ships
	 */
	public static ArrayList<SupportShip> getAlliedSupportShips(Map<EntityPartition> onMap)
	{
		ArrayList<SupportShip> supportShips = getSupportShips(onMap);
		ArrayList<SupportShip> alliedSupportShips = new ArrayList<SupportShip>();
		
		for (SupportShip ss : supportShips)
		{
			if (ss.isAlly())
			{
				alliedSupportShips.add(ss);
			}
		}
		
		return alliedSupportShips;
	}
	
	/**
	 * Retrieves a list of enemy support ships on the specified map.
	 * @param onMap the specified map
	 * @return enemySupportShips the list of enemy support ships
	 */
	public static ArrayList<SupportShip> getEnemySupportShips(Map<EntityPartition> onMap)
	{
		ArrayList<SupportShip> supportShips = getSupportShips(onMap);
		ArrayList<SupportShip> enemySupportShips = new ArrayList<SupportShip>();
		
		for (SupportShip ss : supportShips)
		{
			if (ss.isEnemy())
			{
				enemySupportShips.add(ss);
			}
		}
		
		return enemySupportShips;
	}
	
	/**
	 * Retrieves the number of support ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of support ships
	 */
	public static int getSupportShipCount(Map<EntityPartition> onMap)
	{
		return getSupportShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of allied support ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of allied support ships
	 */
	public static int getAlliedSupportShipCount(Map<EntityPartition> onMap)
	{
		return getAlliedSupportShips(onMap).size();
	}
	
	/**
	 * Retrieves the number of enemy support ships on the specified map.
	 * @param onMap the specified map
	 * @return the number of enemy support ships
	 */
	public static int getEnemySupportShipCount(Map<EntityPartition> onMap)
	{
		return getEnemySupportShips(onMap).size();
	}
	
	/**
	 * Retrieves a list of motherships on the specified map.
	 * @param onMap the specified map
	 * @return motherships the list of motherships
	 */
	public static ArrayList<Mothership> getMotherships(Map<EntityPartition> onMap)
	{
		ArrayList<Ship> ships = getShips(onMap);
		ArrayList<Mothership> motherships = new ArrayList<Mothership>();
		
		for (Ship s : ships)
		{
			if (s instanceof Mothership)
			{
				Mothership ms = (Mothership) s;
				motherships.add(ms);
			}
		}
		
		return motherships;
	}
	
	/**
	 * Retrieves a list of allied motherships on the specified map.
	 * @param onMap the specified map
	 * @return alliedMotherships the list of allied motherships
	 */
	public static ArrayList<Mothership> getAlliedMotherships(Map<EntityPartition> onMap)
	{
		ArrayList<Mothership> motherships = getMotherships(onMap);
		ArrayList<Mothership> alliedMotherships = new ArrayList<Mothership>();
		
		for (Mothership ms : motherships)
		{
			if (ms.isAlly())
			{
				alliedMotherships.add(ms);
			}
		}
		
		return alliedMotherships;
	}
	
	/**
	 * Retrieves a list of enemy motherships on the specified map.
	 * @param onMap the specified map
	 * @return alliedMotherships the list of enemy motherships
	 */
	public static ArrayList<Mothership> getEnemyMotherships(Map<EntityPartition> onMap)
	{
		ArrayList<Mothership> motherships = getMotherships(onMap);
		ArrayList<Mothership> enemyMotherships = new ArrayList<Mothership>();
		
		for (Mothership ms : motherships)
		{
			if (ms.isEnemy())
			{
				enemyMotherships.add(ms);
			}
		}
		
		return enemyMotherships;
	}
	
	/**
	 * Retrieves the number of motherships on the specified map.
	 * @param onMap the specified map
	 * @return the number of motherships
	 */
	public static int getMothershipCount(Map<EntityPartition> onMap)
	{
		return getMotherships(onMap).size();
	}
	
	/**
	 * Retrieves the number of allied motherships on the specified map.
	 * @param onMap the specified map
	 * @return the number of allied motherships
	 */
	public static int getAlliedMothershipCount(Map<EntityPartition> onMap)
	{
		return getAlliedMotherships(onMap).size();
	}
	
	/**
	 * Retrieves the number of enemy motherships on the specified map.
	 * @param onMap the specified map
	 * @return the number of enemy motherships
	 */
	public static int getEnemyMothershipCount(Map<EntityPartition> onMap)
	{
		return getEnemyMotherships(onMap).size();
	}
	
	/**
	 * Retrieves a list of debris on the specified map.
	 * @param onMap the specified map
	 * @return debris the list of debris
	 */
 	public static ArrayList<Debris> getDebris(Map<EntityPartition> onMap)
	{
		ArrayList<Debris> debris = new ArrayList<Debris>();
		ArrayList<Entity> entities = getEntities(onMap);
		
		for (Entity e : entities)
		{
			if (e instanceof Debris)
			{
				Debris d = (Debris) e;
				debris.add(d);
			}
		}
		
		return debris;
	}
	
	/**
	 * Retrieves the number of debris on the specified map.
	 * @param onMap the specified map
	 * @return the number of debris
	 */
	public static int getDebrisCount(Map<EntityPartition> onMap)
	{
		return getDebris(onMap).size();
	}
	
	/**
	 * Retrieves a list of debris IDs on the specified map.
	 * @param onMap the specified map
	 * @return debrisIDs the list of debris IDs
	 */
	public static ArrayList<Integer> getDebrisIDs(Map<EntityPartition> onMap)
	{
		ArrayList<Debris> debris = getDebris(onMap);
		ArrayList<Integer> debrisIDs = new ArrayList<Integer>();
		
		for (Debris d : debris)
		{
			debrisIDs.add(d.getEntityID());
		}
		
		return debrisIDs;
	}
	
	/**
	 * Retrieves a list of small debris on the specified map.
	 * @param onMap the specified map
	 * @return smallDebris the list of small debris
	 */
	public static ArrayList<SmallDebris> getSmallDebris(Map<EntityPartition> onMap)
	{
		ArrayList<Debris> debris = getDebris(onMap);
		ArrayList<SmallDebris> smallDebris = new ArrayList<SmallDebris>();
		
		for (Debris d : debris)
		{
			if (d instanceof SmallDebris)
			{
				SmallDebris sd = (SmallDebris) d;
				smallDebris.add(sd);
			}
		}
		
		return smallDebris;
	}
	
	/**
	 * Retrieves the number of small debris on the specified map.
	 * @param onMap the specified map
	 * @return the number of small debris
	 */
	public static int getSmallDebrisCount(Map<EntityPartition> onMap)
	{
		return getSmallDebris(onMap).size();
	}
	
	/**
	 * Retrieves a list of medium debris on the specified map.
	 * @param onMap the specified map
	 * @return mediumDebris the list of medium debris
	 */
	public static ArrayList<MediumDebris> getMediumDebris(Map<EntityPartition> onMap)
	{
		ArrayList<Debris> debris = getDebris(onMap);
		ArrayList<MediumDebris> mediumDebris = new ArrayList<MediumDebris>();
		
		for (Debris d : debris)
		{
			if (d instanceof MediumDebris)
			{
				MediumDebris md = (MediumDebris) d;
				mediumDebris.add(md);
			}
		}
		
		return mediumDebris;
	}
	
	/**
	 * Retrieves the number of medium debris on the specified map.
	 * @param onMap the specified map
	 * @return the number of medium debris
	 */
	public static int getMediumDebrisCount(Map<EntityPartition> onMap)
	{
		return getMediumDebris(onMap).size();
	}
	
	/**
	 * Retrieves a list of large debris on the specified map.
	 * @param onMap the specified map
	 * @return largeDebris the list of large debris
	 */
	public static ArrayList<LargeDebris> getLargeDebris(Map<EntityPartition> onMap)
	{
		ArrayList<Debris> debris = getDebris(onMap);
		ArrayList<LargeDebris> largeDebris = new ArrayList<LargeDebris>();
		
		for (Debris d : debris)
		{
			if (d instanceof LargeDebris)
			{
				LargeDebris md = (LargeDebris) d;
				largeDebris.add(md);
			}
		}
		
		return largeDebris;
	}
	
	/**
	 * Retrieves the number of large debris on the specified map.
	 * @param onMap the specified map
	 * @return the number of large debris
	 */
	public static int getLargeDebrisCount(Map<EntityPartition> onMap)
	{
		return getLargeDebris(onMap).size();
	}
}
