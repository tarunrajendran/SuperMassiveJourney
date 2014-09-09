package com.smjourney.gui;

import com.smjourney.smjourney.Galaxy;
import com.smjourney.smjourney.Main;
import com.smjourney.smjourney.Memory;

/**
 * LevelSelect 5 is a LevelSelectPanel5 from an Internal Package. Has been
 * incorporated to work with System Memory.
 * 
 * @author Advenio Entertainment
 */
public class LevelSelect5 extends LevelSelectPanel5{
	
	public LevelSelect5(){
		super();
		Main.debugStream.println("Solar System 5 has been Created!");
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
	 * This will load planet 1 in galaxy 5 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet51Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(5, 1) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(5, 1), BattleDisplay.HARD);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet51Done();
		}
	}
	/*
	 * This will load planet 2 in galaxy 5 if it is not complete
	 * If it is complete, the orbital will be covered up and be inaccessable
	 * @override
	 */
	public void Planet52Loader() {
		Galaxy g = Memory.getGalaxy();
		if(g.getCompletionStatus(5, 2) == false){
		BattleDisplay display = new BattleDisplay(g.getPlanet(5, 2), BattleDisplay.HARD);
		Main.cardIncrement++;
		Main.cards.add(display, "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
		}
		else{
			Planet52Done();
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
