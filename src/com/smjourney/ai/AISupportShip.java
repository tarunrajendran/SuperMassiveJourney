package com.smjourney.ai;

import java.util.ArrayList;
import com.smjourney.map.Direction;

import com.smjourney.entity.*;
import com.smjourney.map.CellGroup;
import com.smjourney.map.Cell;
import com.smjourney.map.Map;
import com.smjourney.smjourney.Main;
import com.smjourney.ai.AImain;
import com.smjourney.entity.EntityTracker;


public class AISupportShip extends SupportShip{

	public AISupportShip(Map<EntityPartition> m)
	{
		mp = m;
		new SupportShip(true);
		range = getAttackCells(); 
		this.setDirection(Direction.SOUTH);
		Main.debugStream.println("AISupportShip has been initilized");
	}
	public AISupportShip(Map<EntityPartition> m, int direction)
	{
		mp = m;
		new SupportShip(true);
		range = getAttackCells(); 
		this.setDirection(direction);
		Main.debugStream.println("AISupportShip has been initilized with direction:" + direction);
	}
	/*This will get the number of ships sent so far
	 * It is in AIMain so that all SupportShips can access it*/
	 
	public void numberShipsSent(){
	sentShips = AImain.getShipsSent();	 
	}
	/*Gets the groups of ships for behavioral purposes
	 * */
	 
	public void sendThis(){
		//This is going to be the hardest part, a formula will be saved into the private instance field
		// Which will be used to send Support ships based on Risk and other factors
		if(sendThis){
			AImain.updateShipsSent();
		}
	}
	/*This is the support ship movement method
	 * 
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
	private Map<EntityPartition> mp;
	private boolean sendThis = false;
	private int sentShips = 0;
	private  ArrayList<Cell> range;
	//Constant fields of pairing risk with certain ships
}
//

/*
 * Find what a "group of the ships are"
 * Add the risk values
 */
