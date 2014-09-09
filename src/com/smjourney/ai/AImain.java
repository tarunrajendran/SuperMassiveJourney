package com.smjourney.ai;

import java.util.ArrayList;

import com.smjourney.entity.EntityPartition;
import com.smjourney.map.CellGroup;
import com.smjourney.map.Map;
import com.smjourney.map.Cell;
/*This is the main AI class which shall be called from BattleManager
 * Then it will make the ship do an action based on what type of ship it is
 * To do this, it will make each ship perform a function in its' own AI class
 */
public class AImain {
/*
 * This is the constructor
 * @param mp the Map which all the entities reside on
 */
	public AImain(Map<EntityPartition> mp){
		Mapstate = mp;
		ArrayList<Cell> clist = new ArrayList<Cell>();
		clist = Mapstate.getOccupiedCells();
	}
	/*This one method will cause all enemy ships to perform their respective actions 
	 */
	public void run(){
		for(Cell c : clist){
			tempCells = c;
		processShip();
		}
	}
	/*
	 * This method will cause each ship to carry out a specific action specific to that 
	 * particular ship
	 */
	public void processShip(){
		
		takenentity = Mapstate.get(tempCells);
		//call ships with a constructor including the Map object
	 //Get the entity type of the thing in the tempCell using the get method from cell and then send that ship to do it's ship thing in the other AI things. yeah.
	}
	/*
	 * This will be for SupportShips, and will get the amount of ships dispatched
	 * to target a player-controlled ship
	 * @return int the amount of ships sent so far
	 */
	public static int getShipsSent(){
		return sentShips;
	}
	/*
	 * This will increment the amount of ships sent when
	 * another ship has been sent out
	 */
	public static void updateShipsSent(){
		sentShips++;
	}
	private Map<EntityPartition> Mapstate;
	private EntityPartition takenentity;
	private Cell tempCells;
	private int indexor = 0;
	private ArrayList<Cell> clist;
	private static int sentShips;
}
