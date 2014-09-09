package com.smjourney.gui;

import com.smjourney.smjourney.Galaxy;
import com.smjourney.smjourney.Main;
import com.smjourney.smjourney.Memory;

/**
 * LevelSelect 2 is a LevelSelectPanel2 from an Internal Package. Has been
 * incorporated to work with System Memory.
 * 
 * @author Advenio Entertainment
 */

public class LevelSelect2 extends LevelSelectPanel2{
	
	/**
	 * Constructs a new LevelSelect2
	 */
	public LevelSelect2(){
		super();
		Main.debugStream.println("Solar System 2 has been Created!");
	}
	/**
	 * Goes back to the first panel in card layout, which is Galaxy Panel
	 */
	public void goBack(){
		Main.cardIncrement++;
		Galaxy g = Memory.getGalaxy();
		int shipLogs = g.getNumberOfPlanetsCompleted();
		int systems = g.getNumberOfSystemsCleared();
		Main.cards.add(new GalaxySelect(shipLogs, systems), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
	}
	/*
	 * This will load planet 1 in galaxy 2 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet21Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(2, 1) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(2, 1), BattleDisplay.AMATUER);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet21Done();
		}
	}
	/*
	 * This will load planet 2 in galaxy 2 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet22Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(2, 2) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(2, 2), BattleDisplay.AMATUER);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet22Done();
		}
	}
	/*
	 * This will load planet 3 in galaxy 2 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet23Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(2, 3) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(2, 3), BattleDisplay.MEDIUM);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet23Done();
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
