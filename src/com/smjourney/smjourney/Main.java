/**
 * 
 */
package com.smjourney.smjourney;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.smjourney.entity.*;
import com.smjourney.gui.*;
import com.smjourney.map.*;
import com.smjourney.audio.*;

import javax.swing.*;

/**
 * @author Advenio Entertainment
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		
	try{
		
		debugStream = new PrintWriter(logFileName);
		errorStream = new PrintWriter(errorFileName);
		debugStream.println("----------LOG FILE HAS BEEN CREATED-----------"); //Writes to Log File 
		errorStream.println("----------Error Log---------");
		debugStream.println("Error Log File has been Created");
		
	} catch (FileNotFoundException e){
	       e.printStackTrace();
	}
	//This will loadup the music, sfx, and cheating status in the options.txt file
	try{
		Scanner optionReader = new Scanner(new FileReader(options));
		Main.debugStream.println("The options.txt was found");
		String musicLine = optionReader.nextLine();
		String sfxLine = optionReader.nextLine();
		String cheatwinLine = optionReader.nextLine(); //Allows instant completion of levels
		
		String musicLevel = musicLine.substring(6);
		String sfxLevel = sfxLine.substring(4);
		String cheatWinStatus = cheatwinLine.substring(9);
		Main.debugStream.println("Parsed Settings");
		
		int music = Integer.parseInt(musicLevel);
		int sfx = Integer.parseInt(sfxLevel);
		Main.debugStream.println("Music set to " + music + "\nSFX set to " + sfx);
		if(cheatWinStatus.equals("true")){
			cheatwin = true;
			Main.debugStream.println("Cheatwin set to true");
		}
		MusicPlayer.setVolume(music);
		SFX.setVolume(sfx);
	}catch(Exception e){
		Main.errorStream.println("The options.txt could not be loaded!\nDefaulting music to 50\nDefaulting SFX to 50\nDefaulting cheatwin to false");
		Main.debugStream.println("Music set to 50\nSFX set to 50\nCheatwin set to false");
		MusicPlayer.setVolume(50);
		SFX.setVolume(50);
		cheatwin = false;
	}
		Memory.loadGalaxy();
		Galaxy g = Memory.getGalaxy();
		

		MusicPlayer.play(BGM.MAIN_THEME);
		
		frame = new JFrame("Battle Display Demo");
        frame.setSize(1200,700);
        cards.setLayout(cl);
        Main.cardIncrement++;
		Galaxy ga = Memory.getGalaxy();
		int shipLogs = ga.getNumberOfPlanetsCompleted();
		int systems = ga.getNumberOfSystemsCleared();
		Main.cards.add(new GalaxySelect(shipLogs, systems), "" + Main.cardIncrement);
		Main.cl.show(Main.cards, "" + Main.cardIncrement);
        cards.add(new GalaxySelect(shipLogs, systems), "" + 1);
        Main.debugStream.println("Galaxy Select has been Initialized, Card 1");
        cards.add(new TitleScreen(), "" + 2);
        Main.debugStream.println("TitleScreen has been Initialized, Card 2");
        cards.add(new LevelSelect1(), "3");
 /**       Main.debugStream.println("Level Select 1 has been Initialized, Card 3");
        cards.add(new LevelSelect2(), "" + 4);
        Main.debugStream.println("Level Select 2 has been Initialized, Card 4");
        cards.add(new LevelSelect3(), "" + 5);
        Main.debugStream.println("Level Select 3 has been Initialized, Card 5");
        cards.add(new LevelSelect4(), "" + 6);
        Main.debugStream.println("Level Select 4 has been Initialized, Card 6");
        cards.add(new LevelSelect5(), "" + 7);
        Main.debugStream.println("Level Select 5 has been Initialized, Card 7"); */
        cl.show(cards, "" + 2);
        frame.getContentPane().add(cards, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); 
		frame.setResizable(false);
			
		debugStream.close();
		errorStream.close();
		
	}
	
	
	
	private static String logFileName = "debugLog.txt";
	private static String errorFileName = "errorLog.txt";
	private static String options = "options.txt";
	
	public static PrintWriter debugStream;
	public static PrintWriter errorStream;
	
	public static JFrame frame;
    public static JPanel cards = new JPanel();
    public static CardLayout cl = new CardLayout();
    public static int currentCard = 1;
    public static int cardIncrement = 3;
    
    protected static boolean cheatwin = false;
    private static int music = 0;
    private static int sfx = 0;
	

}
