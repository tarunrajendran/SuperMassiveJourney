package com.smjourney.smjourney;

import java.io.*;
import java.util.*;

/**
 * The Memory class contains static methods pertaining to loading and saving progress in Supermassive Journey's Galaxy.
 * Progress is monitored in the Galaxy, so nearly all methods will have a parameter or return type for Galaxy.
 * @author Ashwin Chakilum
 *
 */
public class Memory 
{
	
	private static Galaxy currentGalaxy;
	
	/**
	 * Loads the Galaxy data from a text file containing save data.
	 * @return g the Galaxy translated from the save data.
	 */
	public static void loadGalaxy()
	{
		String code = "";
		
		try
		{
			File file = new File("res/saveData.txt");
			Scanner reader = new Scanner(file);
			code = reader.nextLine();
		}
		catch (Exception e)
		{
			currentGalaxy = new Galaxy();
			return;
		}
		
		Galaxy g = new Galaxy(code);
		currentGalaxy = g;
		
		Main.debugStream.println("Galaxy has been loaded from save file.");
	}
	
	/**
	 * Translates the Galaxy data into a save data file.
	 * @param galaxy the Galaxy to be saved.
	 */
	public static void saveGalaxy(Galaxy galaxy)
	{
		galaxy.generateCompletionCode();
		try
		{
			FileWriter writer = new FileWriter("res/saveData.txt");
			writer.write(galaxy.getCompletionCode());
			writer.close();
		}
		catch (Exception e) 
		{
			
		}
		
		Main.debugStream.println("Galaxy has been saved.");
	}
	
	/**
	 * Resets the Galaxy data so that every Planet is incomplete
	 */
	public static void resetGalaxy()
	{
		currentGalaxy = new Galaxy();
		saveGalaxy();
		Main.debugStream.println("Galaxy has been reset.");

	}
	
	/**
	 * Translates the Galaxy data into a save data file.
	 * @param galaxy the Galaxy to be saved.
	 */
	public static void saveGalaxy()
	{
		currentGalaxy.generateCompletionCode();
		try
		{
			FileWriter writer = new FileWriter("res/saveData.txt");
			writer.write(currentGalaxy.getCompletionCode());
			writer.close();
		}
		catch (Exception e) 
		{
			
		}
		Main.debugStream.println("Galaxy has been saved.");
	}
	
	public static Galaxy getGalaxy()
	{
		return currentGalaxy;
	}
	
	public static void setGalaxy(Galaxy newGalaxy)
	{
		currentGalaxy = newGalaxy;
	}
	
	public static boolean isFirstTimePlaying()
	{
		loadGalaxy();
		Galaxy g = getGalaxy();
		if (g.getCompletionCode().equals("1ff2fff3fff4fff5fff6ff"))
		{
			Main.debugStream.println("First time playing; should prompt a new game.");
			return true;
		}
		else
		{
			Main.debugStream.println("Not first time playing; should prompt to resume game.");
			return false;
		}

	}
}
