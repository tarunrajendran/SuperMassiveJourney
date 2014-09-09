package com.smjourney.gui;

import com.smjourney.smjourney.Galaxy;
import com.smjourney.smjourney.Main;
import com.smjourney.smjourney.Memory;

/**
 * LevelSelect 3 is a LevelSelectPanel3 from an Internal Package. Has been
 * incorporated to work with System Memory.
 * 
 * @author Advenio Entertainment
 */
public class LevelSelect3 extends LevelSelectPanel3{
	
	public LevelSelect3(){
		super();
		Main.debugStream.println("Solar System 3 has been Created!");
	}
	
	public void goBack(){
		Main.cardIncrement++;
		Galaxy g = Memory.getGalaxy();
		int shipLogs = g.getNumberOfPlanetsCompleted();
		int systems = g.getNumberOfSystemsCleared();
		Main.cards.add(new GalaxySelect(shipLogs, systems), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
	}
	/*
	 * This will load planet 1 in galaxy 3 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet31Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(3, 1) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(3, 1), BattleDisplay.AMATUER);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet31Done();
		}
	}
	/*
	 * This will load planet 2 in galaxy 3 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet32Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(3, 2) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(3, 2), BattleDisplay.MEDIUM);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet32Done();
		}
	}
	/*
	 * This will load planet 3 in galaxy 3 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet33Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(3, 3) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(3, 3), BattleDisplay.MEDIUM);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet33Done();
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
