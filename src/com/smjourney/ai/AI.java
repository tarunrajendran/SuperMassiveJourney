package com.smjourney.ai;

import com.smjourney.smjourney.*;
import com.smjourney.map.*;
import com.smjourney.entity.*;
import com.smjourney.gui.*;

import java.util.ArrayList;
import java.util.Collections;

public class AI 
{
	
	public static final int EASY = 5;
	public static final int AMATUER = 7;
	public static final int MEDIUM = 9;
	public static final int ADVANCED = 11;
	public static final int HARD = 13;
	
	private BattleDisplay displayReference;
	private Ship selectedShip;
	private Ship targetedShip;
	
	private ArrayList<Ship> allyShipPriorityLog;
	private ArrayList<Ship> enemyShipPriorityLog;
	
	private int maxInputCount;
	
	public AI(BattleDisplay displayReference, int difficulty)
	{
		this.displayReference = displayReference;
		this.maxInputCount = difficulty; // Number of AI inputs positively correlates to difficulty setting of AI
		
		selectedShip = null;
		targetedShip = null;
		
		allyShipPriorityLog = new ArrayList<Ship>();
		enemyShipPriorityLog = new ArrayList<Ship>();
		
		Main.debugStream.println("AI has been set up.");
	}

	public void runOnce()
	{
		if (selectedShip != null)
		{
			if (selectedShip.getCapacity() <= 0)
			{
				displayReference.setEntitySelected(null);
				selectedShip = null;
				enemyShipPriorityLog.remove(0);
			}
		}
		if (targetedShip != null)
		{
			if (targetedShip.isDestroyed())
			{
				displayReference.setEntityTargeted(null);
				targetedShip = null;
				allyShipPriorityLog.remove(0);
			}
		}
		
		selectedShip = enemyShipPriorityLog.get(0);
		targetedShip = allyShipPriorityLog.get(0);
		
		displayReference.setEntitySelected(selectedShip);
		displayReference.setEntityTargeted(targetedShip);
		
		if (selectedShip instanceof Mothership)
		{
			return;
		}
		selectedShip.setTarget(targetedShip);
		if (!selectedShip.targetIsWithinRange())
		{
			deploySelectionToTarget(selectedShip, targetedShip);
			return;
		}
		else
		{
			displayReference.operateEntity(selectedShip);
		}
	}
	
	public void wakeUp()
	{
		selectedShip = null;
		targetedShip = null;
		displayReference.setEntitySelected(null);
		displayReference.setEntityTargeted(null);
		
		this.prioritizeAllyShips();
		this.prioritizeEnemyShips();
	}
	
	public void goToSleep()
	{
		selectedShip = null;
		targetedShip = null;
		displayReference.setEntitySelected(null);
		displayReference.setEntityTargeted(null);
	}
	
	public void prioritizeAllyShips()
	{
		// Originally from lowest to highest risk
		ArrayList<Ship> allyShips = EntityTracker.getAlliedShips(displayReference.getMap());
		Collections.sort(allyShips);
		Collections.reverse(allyShips);
		
		allyShipPriorityLog = allyShips;
	}
	
	public void prioritizeEnemyShips()
	{
		ArrayList<Ship> enemyShips = new ArrayList<Ship>();
		
		ArrayList<ScoutShip> scoutShips = EntityTracker.getEnemyScoutShips(displayReference.getMap());
		ArrayList<AttackShip> attackShips = EntityTracker.getEnemyAttackShips(displayReference.getMap());
		ArrayList<SupportShip> supportShips = EntityTracker.getEnemySupportShips(displayReference.getMap());
		
		Collections.sort(scoutShips);
		Collections.sort(attackShips);
		Collections.sort(supportShips);
		
		enemyShips.addAll(scoutShips);
		enemyShips.addAll(attackShips);
		enemyShips.addAll(supportShips);
		
		enemyShipPriorityLog = enemyShips;
	}
	
	public void deploySelectionToTarget(Ship selection, Ship target)
	{
		Cell targetRef = target.getCellGroup().getReferenceCell();
		CellGroup possibleCG = new CellGroup(new Cell(targetRef.getRow() - 2, targetRef.getColumn() - 2), target.getSize() + 4);
		CellGroup innerCG = new CellGroup(new Cell(targetRef.getRow() - 1, targetRef.getColumn() - 1),  target.getSize() + 2);
		
		possibleCG.trim(innerCG);
		
		ArrayList<Cell> possibleCells = possibleCG.toArrayList();

		CellGroup cgTarget = null;
		for (Cell possibleCell : possibleCells)
		{
			CellGroup potentialTarget = new CellGroup(possibleCell, selection.getSize());
			if (selection.canMoveTo(potentialTarget) && displayReference.getMap().isValid(potentialTarget))
			{
				cgTarget = potentialTarget;
				break;
			}
		}
		if (cgTarget == null)
		{
			return;
		}
		displayReference.moveEntityTo(selection, cgTarget);
	}
	
	public ArrayList<Ship> getAllyShipPriorityLog() 
	{
		return allyShipPriorityLog;
	}

	public ArrayList<Ship> getEnemyShipPriorityLog() 
	{
		return enemyShipPriorityLog;
	}

	public int getMaxInputCount() 
	{
		return maxInputCount;
	}

}
