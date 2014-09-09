package com.smjourney.audio;

import javax.sound.sampled.*;
import java.io.*;
import com.smjourney.smjourney.*;;

public class MusicPlayer 
{
	
	private static Clip soundClip = null;
	private static double gain = 0.0;
	private static boolean stopped = true;
	private static int framePosition = 0;
	
	/**
	 * Loads a sound file into the MusicPlayer
	 * @param fileName the sound file path
	 */
	public static void load(String fileName)
	{
		if (!(soundClip == null))
		{
			unload();
		}
		try
		{
			File soundFile = new File(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			soundClip = AudioSystem.getClip();
			soundClip.open(ais);
			
		} catch (Exception e) {}
		Main.debugStream.println("MusicPlayer has been loaded.");
	}
	
	/**
	 * Unloads the current sound file from the MusicPlayer
	 */
	public static void unload()
	{
		if (soundClip == null)
		{
			return;
		}
		if (!stopped)
		{
			stop();
		}
		soundClip = null;
		Main.debugStream.println("MusicPlayer has been unloaded.");
	}
	
	/**
	 * Plays the sound file
	 */
	public static void play()
	{
		if (soundClip == null)
		{
			return;
		}
		if (stopped)
		{
			soundClip.setFramePosition(framePosition);
			soundClip.loop(Clip.LOOP_CONTINUOUSLY);
			stopped = false;
		}
		Main.debugStream.println("MusicPlayer is playing.");
	}
	
	/**
	 * Loads a sound file then plays it 
	 */
	public static void play(String fileName)
	{
		load(fileName);
		play();
		Main.debugStream.println("MusicPlayer is playing.");
	}
	
	/**
	 * Pauses the sound file
	 */
	public static void pause()
	{
		if (soundClip == null)
		{
			return;
		}
		if (!stopped)
		{
			framePosition = soundClip.getFramePosition();
			soundClip.stop();
			stopped = true;
		}
		Main.debugStream.println("MusicPlayer is paused.");
	}
	
	/**
	 * Stops the sound file (rewinding it to the beginning)
	 */
	public static void stop()
	{
		if (soundClip == null)
		{
			return;
		}
		soundClip.stop();
		soundClip.setFramePosition(0);
		framePosition = 0;
		stopped = true;
		Main.debugStream.println("MusicPlayer is stopped.");
	}
	
	/**
	 * Retrieves the volume set for sound effects to play at.
	 * @return the SFX volume
	 */
	public static double getVolume()
	{
		return (gain + 80) / 0.8;
	}
	
	/**
	 * Sets a new volume for SFX to be played at (maximum: 100, minimum: 0).
	 * @param newVolume the new SFX volume
	 */
	public static void setVolume(double newVolume)
	{
		if (newVolume > 100)
		{
			newVolume = 100;
		}
		if (newVolume < 0)
		{
			newVolume = 0;
		}
		double newGain = (0.8 * newVolume) - 80;
		gain = newGain;
		
		if (soundClip != null)
		{
			FloatControl gainControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue((float) gain);	
		}
	}
	
	/**
	 * Increases the volume by 20 (gain increases by 1.6 dB)
	 */
	public static void increaseVolume()
	{
		increaseVolume(20);
	}
	
	/**
	 * Decreases the volume by 20 (gain decreases by 1.6 dB)
	 */
	public static void decreaseVolume()
	{
		decreaseVolume(20);
	}
	
	/**
	 * Increases the volume by a quantity (gain increases by [0.8 * quantity] dB)
	 * @param quantity how much the volume should be increased (max: 100)
	 */
	public static void increaseVolume(double quantity)
	{
		setVolume(getVolume() + quantity);
	}
	
	/**
	 * Decreases the volume by a quantity (gain decreases by [0.8 * quantity] dB)
	 * @param quantity how much the volume should be decreased (min: 0)
	 */
	public static void decreaseVolume(int quantity)
	{
		setVolume(getVolume() - quantity);
	}
	
	/**
	 * Retrieves whether the sound file is stopped/paused or not
	 * @return whether the sound file is stopped or not
	 */
	public static boolean isStopped()
	{
		return stopped;
	}
	
	/**
	 * Retrieves whether the sound file is playing or not
	 * @return whether the sound file is playing or not
	 */
	public static boolean isPlaying()
	{
		return !stopped;
	}
}
