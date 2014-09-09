package com.smjourney.gui;


import com.smjourney.smjourney.Memory;
import com.smjourney.smjourney.Main;
/**
 * 
 * TitleScreen is an extension of the TitleScreen Panel class, that is integrated with Memory.
 * Creating a new TitleScreen, will automatically create a TitleScreen according to the Save File.
 * 
 * @author Advenio Entertainment
 *
 */

public class TitleScreen extends TitleScreenPanel{

	/**
	 * Creates a new TitleScreen
	 * 
	 */
	public TitleScreen(){
		super(false);
		Main.debugStream.println("TitleScreen has been Created!");
	}
	
	/**
	 * 
	 * Method to change to a new Galaxy Select
	 * 
	 */
	
	public void showGS(){
		
		Main.cl.first(Main.cards);				
	}
		
	//private static Boolean saveFileIsPresent = Memory.isSaveFilePresent();
	
}
