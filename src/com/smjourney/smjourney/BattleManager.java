package com.smjourney.smjourney;

import com.smjourney.audio.SFX;
import com.smjourney.entity.*;
import com.smjourney.map.*;
import com.smjourney.gui.*;
import com.smjourney.ai.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import java.awt.*;
import javax.swing.*;

public class BattleManager extends JFrame implements ActionListener
{

	private Map<EntityPartition> map;
	private boolean isYourTurn;
	private boolean youWin;
	
	private Planet planet;
	private BattleDisplay displayReference;
	
	private Timer aiInputTimer;
	private int nAiInputs;
	private int maxAiInputs;
	
	private AI ai;

	public BattleManager(Planet planet, BattleDisplay displayReference, int difficulty) 
	{
		super();
		setVisible(false);
		this.displayReference = displayReference;
		this.map = this.displayReference.getMap();
		this.planet = planet;
		
		int aiDifficulty = 0;
		
		if (difficulty == BattleDisplay.EASY)
		{
			aiDifficulty = AI.EASY;
		}
		if (difficulty == BattleDisplay.AMATUER)
		{
			aiDifficulty = AI.AMATUER;
		}
		if (difficulty == BattleDisplay.MEDIUM)
		{
			aiDifficulty = AI.MEDIUM;
		}
		if (difficulty == BattleDisplay.ADVANCED)
		{
			aiDifficulty = AI.ADVANCED;
		}
		if (difficulty == BattleDisplay.HARD)
		{
			aiDifficulty = AI.HARD;
		}
		
		this.ai = new AI(displayReference, aiDifficulty);
		
		this.aiInputTimer = new Timer(250, this);
		this.nAiInputs = 0;
		this.maxAiInputs = ai.getMaxInputCount();

		isYourTurn = true;
		Main.debugStream.println("BattleManager created");

	}

	public boolean isBattleFinished()
	{
		if (EntityTracker.getAlliedMothershipCount(map) <= 0)
		{
			youWin = false;
			return true;
		}
		if (EntityTracker.getEnemyShipCount(map) <= 0)
		{
			youWin = true;
			return true;
		}
		return false;
	}
	
	public boolean youWin()
	{
		return youWin;
	}
	
	public boolean isYourTurn()
	{
		return isYourTurn;
	}
	
	public void toggleTurn()
	{
		isYourTurn = !isYourTurn;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if (nAiInputs >= maxAiInputs || isBattleFinished())
		{
			aiInputTimer.stop();
			ai.goToSleep();
			displayReference.setYourTurn(true);
			isYourTurn = true;
			this.nAiInputs = -1;
		}
		if (nAiInputs <= 0 && aiInputTimer.isRunning())
		{
			ai.wakeUp();
		}
		if (aiInputTimer.isRunning())
		{
			ai.runOnce();
			//System.out.println("AI Move #" + (nAiInputs + 1) + " out of " + maxAiInputs);
		}
		nAiInputs++;
	}
	
	public void startAiTurn()
	{
		isYourTurn = false;
		aiInputTimer.restart();
	}
}

// REFERENCE CODE

/*Memory.loadGalaxy();
Galaxy g = Memory.getGalaxy();
g.setCompletionStatus(planet.getPlanetID(), planet.getSolarSystemReference().getSystemID(), true);
Memory.setGalaxy(g);
Memory.saveGalaxy(g);

while (getEntityIDs().contains(Entity.MOTHERSHIP)) {

if (turn = false) {
	changePanel(lgs); // Changes GUI to the Level Game Screen
	turn = true;
} else {
	// AI CODE goes here
}*/

