package com.smjourney.ai;

import java.util.ArrayList;
import com.smjourney.map.Direction;

import com.smjourney.entity.AttackShip;
import com.smjourney.entity.EntityPartition;
import com.smjourney.entity.EntityTracker;
import com.smjourney.entity.Mothership;
import com.smjourney.entity.Ship;
import com.smjourney.map.Cell;
import com.smjourney.map.CellGroup;
import com.smjourney.map.Map;
import com.smjourney.smjourney.Main;
/*
 * This is the Enemy intelligence for the Boss ship
 * Though it is a Mothership, the enemy will not lose when this ship is destroyed
 * This is because the enemy still has reinforcements on the planet, whereas the player does not
 */
public class AIBossShip extends Mothership{
	public AIBossShip(Map<EntityPartition> m){
		mp = m;
		new Mothership(true);
		anchor = new Cell(0,25);
		nearcells = mp.getOccupiedSurroundingCells(cg);
		upperbounds = new Cell(0,50);
		lowerbounds = new Cell(0,0);
		this.setDirection(Direction.SOUTH);
		Main.debugStream.println("AIBossShip has been initilized");
	}
	public AIBossShip(Map<EntityPartition> m, int direction){
		mp = m;
		new Mothership(true);
		anchor = new Cell(0,25);
		nearcells = mp.getOccupiedSurroundingCells(cg);
		upperbounds = new Cell(0,50);
		lowerbounds = new Cell(0,0);
		this.setDirection(direction);
		Main.debugStream.println("AIBossShip has been initilized with direction:" + direction);
	}
	/*This will get the units "friendly" towards the enemy within a set distance around the ship
	 * That is all the ships that are enemies.
	 */
	public void attackThreats(){
		for (Cell c : nearcells){
		Ship ourside = (Ship) mp.get(c).getEntityReference();
		if(ourside.isAlly()) 
			operate();
		}
	}
	/*This is the move commands for the Boss Ship
	 * It is made to pace around the back of the map and shoot anything that comes in the way
	 */
	public void moveAt(){
		if(this.getCellGroup().getReferenceCell().equals(upperbounds))
			dir = 1;
		if(this.getCellGroup().getReferenceCell().equals(lowerbounds))
			dir = 0;
		
		if(dir == 0){
			CellGroup ncg = new CellGroup(upperbounds,1);
			this.setDirection(Direction.NORTH);  
			this.moveForward();
		}
		if (dir == 1){
			CellGroup ncg = new CellGroup(upperbounds,1);
			this.setDirection(Direction.SOUTH);  	
			this.moveForward();
		}
		
	}
	private Map<EntityPartition> mp;
	private int dir = 0; // A 0 is UP, 1 is Down.
	private Cell upperbounds;
	private Cell lowerbounds;
	private Cell anchor;
	private ArrayList<Cell> nearcells;
	private CellGroup cg = this.getCellGroup();
	private Ship nearest;
	private CellGroup nearestcg;
}
