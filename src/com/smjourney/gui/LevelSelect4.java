package com.smjourney.gui;

import com.smjourney.smjourney.Galaxy;
import com.smjourney.smjourney.Main;
import com.smjourney.smjourney.Memory;

/**
 * LevelSelect 4 is a LevelSelectPanel4 from an Internal Package. Has been
 * incorporated to work with System Memory.
 * 
 * @author Advenio Entertainment
 */
public class LevelSelect4 extends LevelSelectPanel4{
	
	public LevelSelect4(){
		super();
		Main.debugStream.println("Solar System 4 has been Created!");
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
	 * This will load planet 1 in galaxy 4 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet41Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(4, 1) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(4, 1), BattleDisplay.MEDIUM);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet41Done();
		}
	}
	/*
	 * This will load planet 2 in galaxy 4 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet42Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(4, 2) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(4, 2), BattleDisplay.ADVANCED);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet42Done();
		}
	}
	/*
	 * This will load planet 3 in galaxy 4 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet43Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(4, 3) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(4, 3), BattleDisplay.ADVANCED);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet43Done();
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
