package com.smjourney.ai;

import java.util.ArrayList;
import java.math.*;

import com.smjourney.entity.*;
import com.smjourney.map.Cell;
import com.smjourney.map.CellGroup;
import com.smjourney.map.Map;
import java.math.*;
import com.smjourney.map.Direction;
import com.smjourney.smjourney.Main;
public class AIScoutShip extends ScoutShip 
{
	
	public AIScoutShip(Map<EntityPartition> m)
	{
		mp = m; 
		new ScoutShip(true); 
		this.setDirection(Direction.SOUTH);
		Main.debugStream.println("AIScoutShip has been initilized");
	}
	public AIScoutShip(Map<EntityPartition> m, int direction)
	{
		mp = m; 
		new ScoutShip(true); 
		this.setDirection(direction);
		Main.debugStream.println("AIScoutShip has been initilized with direction:" + direction);
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
/*This will have the scout ship follow random attack ships
 * as their behavior is to do so.
 */
public void moveAt(){
	while(rAttack > nearbyAttackShips.size() -1){ //Will keep on repeating until a number below present AttackShip is found
		rAttack= (int) Math.random();
	}
	follower = nearbyAttackShips.get(rAttack);
	CellGroup ofFollower = follower.getCellGroup();
	this.setDirection(this.getCellGroup().getDirectionTowards(ofFollower));
	this.moveForward();
}
	private ArrayList<Cell> nearest; //Has the free cells so use Math.random's temp value to get something from this
	private int count = 0;
	private int rAttack = 0;
	private ArrayList<Cell> AttackShipCells;
	private ArrayList<Cell> nearbyFriendly;
	private ArrayList<AttackShip> nearbyAttackShips;
	private CellGroup nearestcg;
	private Map<EntityPartition> mp;
	private CellGroup cg = this.getCellGroup();
	private ArrayList<Cell> nearcells;
	private  ArrayList<Cell> range;
	private AttackShip follower = null;
}
