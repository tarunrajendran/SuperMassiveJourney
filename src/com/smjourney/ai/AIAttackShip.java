package com.smjourney.ai;

import com.smjourney.entity.EntityTracker;
import com.smjourney.smjourney.*;
import java.util.ArrayList;


import com.smjourney.entity.*;
import com.smjourney.map.Cell;
import com.smjourney.entity.*;
import com.smjourney.map.CellGroup;
import com.smjourney.map.Map;
import com.smjourney.map.Direction;
/*
 * This is the enemy artificial intelligence on the AttackShip
 */
public class AIAttackShip extends Ship
{
	
	public AIAttackShip(Map m)
	{
		mp = m;
		new AttackShip(true);
		range = getAttackCells();
		this.setDirection(Direction.SOUTH);
		Main.debugStream.println("AIAttackShip has been initilized");
	}
	public AIAttackShip(Map m, int direction)
	{
		mp = m;
		new AttackShip(true);
		range = getAttackCells();
		this.setDirection(direction);
		Main.debugStream.println("AIAttackShip has been initilized with direction:" + direction);
	}

		/*This will get the nearby attackships on the enemy side and save the closest one to the private instance field
		 * it will be locked onto the cell of a nearby attack ship.
		 * */
	public void getNearbyFriendly(){

		nearcells = mp.getOccupiedSurroundingCells(cg);
		nearbyAttackShips = EntityTracker.getEnemyAttackShips(mp);
		for (AttackShip att: nearbyAttackShips){
			AttackShipCells.add(att.getCellGroup().getReferenceCell());
		}
        for (Cell c: nearcells){
        	for (Cell ca : AttackShipCells){
        		if (c.equals(ca))
        			nearbyFriendly.add(c);
        	}
        } 

	}
	/*This will have the AttackShip move
	 * It will move towards the nearest ship
	 */
	public void moveAt(){
		if(range != null){
		for (Cell c: range){
			Ship targeting = (Ship) mp.get(c).getEntityReference();
			if(targeting.isAlly()){
				operate();
			}
		}
	}
		else{
		this.moveForward();
		}
	}
private ArrayList<Cell> AttackShipCells;
private ArrayList<Cell> nearbyFriendly;
private ArrayList<AttackShip> nearbyAttackShips;
private CellGroup nearestcg;
private Map<EntityPartition> mp;
private CellGroup cg = this.getCellGroup();
private ArrayList<Cell> nearcells;
private  ArrayList<Cell> range;
}
