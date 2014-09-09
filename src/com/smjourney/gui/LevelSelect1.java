package com.smjourney.gui;

import com.smjourney.smjourney.*;


/**
 * LevelSelect 1 is a LevelSelectPanel1 from an Internal Package. Has been
 * incorporated to work with System Memory.
 * 
 * @author Advenio Entertainment
 */

public class LevelSelect1 extends LevelSelectPanel1 {

	/**
	 * Constructs a new LevelSelect1
	public LevelSelect1() {
		super();
		Main.debugStream.println("Solar System 1 has been Created!");
	}

	/**
	 * Goes back to the first panel in card layout, which is Galaxy Panel
	 */
	public void goBack() {
		Main.cardIncrement++;
		Galaxy g = Memory.getGalaxy();
		int shipLogs = g.getNumberOfPlanetsCompleted();
		int systems = g.getNumberOfSystemsCleared();
		Main.cards.add(new GalaxySelect(shipLogs, systems), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
	}
	/*
	 * This will load planet 1 in galaxy 1 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet11Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(1, 1) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(1, 1), BattleDisplay.EASY);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet11Done();
		}
	}
	/*
	 * This will load planet 2 in galaxy 1 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet12Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(1, 2) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(1, 2), BattleDisplay.EASY);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet12Done();
		}
	}
	/*This will update the screen every time the mouse moves
	 * @override
	 */
	public void updateUI(){
		repaint();
		revalidate();
		}
}
