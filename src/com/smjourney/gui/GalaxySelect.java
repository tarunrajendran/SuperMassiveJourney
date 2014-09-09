package com.smjourney.gui;

import com.smjourney.audio.MusicPlayer;
import com.smjourney.audio.SFX;
import com.smjourney.smjourney.Galaxy;
import com.smjourney.smjourney.Main;
import com.smjourney.smjourney.Memory;

/**
 * GalaxySelect is a GalaxyPanel that has been integrated with the save file,
 * and memory.
 * 
 * @author Advenio Entertainment
 */

public class GalaxySelect extends GalaxyPanel {

	/**
	 * Constructs a new GalaxySelect
	 */
	public GalaxySelect(int shipLogs, int systems) {

		super(shipLogs, systems);
		Main.debugStream.println("GalaxySelect has successfully read data.");
		Main.debugStream.println("GalaxySelect Console GUI has been Created!");

	}

	/**
	 * Changes Music Volume based off value in parameter.
	 * 
	 * @param MsInput
	 *            is the new volume
	 */

	public void changeMVolume(int msInput) {
		MusicPlayer.setVolume(msInput);
		Main.debugStream
				.println("Music Volume has been changed to: " + msInput);
	}

	/**
	 * Changes SFX Volume based off value in parameter.
	 * 
	 * @param sfxInput
	 *            is the new volume
	 */
	public void changeSVolume(int sfxInput) {
		SFX.setVolume(sfxInput);
		Main.debugStream.println("SFX Volume has been changed to: " + sfxInput);
	}

	/**
	 * Method to change CardLayout to SolarSytem 1
	 * 
	 */
	public void goToSS1() {

		Main.cardIncrement++;
		Main.cards.add(new LevelSelect1(), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);

	}

	/**
	 * Method to change CardLayout to SolarSytem 2
	 * 
	 */
	public void goToSS2() {
		Main.cardIncrement++;
		Main.cards.add(new LevelSelect2(), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
	}

	/**
	 * Method to change CardLayout to SolarSytem 3
	 * 
	 */
	public void goToSS3() {
		Main.cardIncrement++;
		Main.cards.add(new LevelSelect3(), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
	}

	/**
	 * Method to change CardLayout to SolarSytem 4
	 * 
	 */
	public void goToSS4() {
		Main.cardIncrement++;
		Main.cards.add(new LevelSelect4(), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
	}

	/**
	 * Method to change CardLayout to SolarSytem 5
	 * 
	 */
	public void goToSS5() {

		Main.cardIncrement++;
		Main.cards.add(new LevelSelect5(), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
	}

	/**
	 * Deletes Save File and starts a new game
	 * 
	 */

	public void deleteSave() {
		Memory.resetGalaxy();
		Main.frame.dispose();
		Main.main(null);
	}

	/**
	 * Updates, Repaints and Revalidates the Galaxy Select UI based on Memory
	 * Changes whenever the mouse moves.
	 * 
	 */

	public void updateMemory() {

	    g = Memory.getGalaxy();
		shipLogs = g.getNumberOfPlanetsCompleted();
		systems = g.getNumberOfSystemsCleared();
		setNumberOfShipLogs(shipLogs);
		setNumberOfSolarSystems(systems);
		repaint();
		revalidate();

	}

	private static Galaxy g = Memory.getGalaxy();
	public static int shipLogs = g.getNumberOfPlanetsCompleted();
	public static int systems = g.getNumberOfSystemsCleared();
	private int x = 0;
}
