package com.smjourney.audio;

import javax.sound.sampled.*;

import com.smjourney.smjourney.Main;

import java.io.*;

/**
 * This class contains simple audio processing functions,
 * along with constants referencing different sound file paths,
 * to play SFX in Supermassive Journey
 * @author Ashwin Chakilum
 *
 */
public class SFX 
{
	/**
	 * Sound file reference - res/shipFireSM.wav
	 */
	public static String SHIP_FIRE_SM = "res/shipFireSM.wav";
	/**
	 * Sound file reference - res/shipFireMD.wav
	 */
	public static String SHIP_FIRE_MD = "res/shipFireMD.wav";
	/**
	 * Sound file reference - res/shipFireLG.wav
	 */
	public static String SHIP_FIRE_LG = "res/shipFireLG.wav";
	
	/**
	 * Sound file reference - res/shipDestroySM.wav
	 */
	public static String SHIP_DESTROY_SM = "res/shipDestroySM.wav";
	/**
	 * Sound file reference - res/shipDestroyMD.wav
	 */
	public static String SHIP_DESTROY_MD = "res/shipDestroyMD.wav";
	/**
	 * Sound file reference - res/shipDestroyLG.wav
	 */
	public static String SHIP_DESTROY_LG = "res/shipDestroyLG.wav";
	
	/**
	 * Sound file reference - res/shipDamageSM.wav
	 */
	public static String SHIP_DAMAGE_SM = "res/shipDamageSM.wav";
	/**
	 * Sound file reference - res/shipDamageMD.wav
	 */
	public static String SHIP_DAMAGE_MD = "res/shipDamageMD.wav";
	/**
	 * Sound file reference - res/shipDamageLG.wav
	 */
	public static String SHIP_DAMAGE_LG = "res/shipDamageLG.wav";
	
	/**
	 * Sound file reference - res/shipBeam.wav
	 */
	public static String SHIP_BEAM = "res/shipBeam.wav";
	
	/**
	 * Sound file reference - res/mouseOver.wav
	 */
	public static String MOUSE_OVER = "res/mouseOver.wav";
	/**
	 * Sound file reference - res/mouseForward.wav
	 */
	public static String MOUSE_FORWARD = "res/mouseForward.wav";
	/**
	 * Sound file reference - res/mouseBack.wav
	 */
	public static String MOUSE_BACK = "res/mouseBack.wav";
	
	/**
	 * Sound file reference - res/debrisDestroySM.wav
	 */
	public static String DEBRIS_DESTROY_SM = "res/debrisDestroySM.wav";
	/**
	 * Sound file reference - res/debrisDestroyMD.wav
	 */
	public static String DEBRIS_DESTROY_MD = "res/debrisDestroyMD.wav";
	/**
	 * Sound file reference - res/debrisDestroyLG.wav
	 */
	public static String DEBRIS_DESTROY_LG = "res/debrisDestroyLG.wav";
	
	/**
	 * Sound file reference - res/debrisDamageSM.wav
	 */
	public static String DEBRIS_DAMAGE_SM = "res/debrisDamageSM.wav";
	/**
	 * Sound file reference - res/debrisDamageMD.wav
	 */
	public static String DEBRIS_DAMAGE_MD = "res/debrisDamageMD.wav";
	/**
	 * Sound file reference - res/debrisDamageLG.wav
	 */
	public static String DEBRIS_DAMAGE_LG = "res/debrisDamageLG.wav";
	/**
	 * Sound file reference - res/shipMove.wav
	 */
	public static String SHIP_MOVE = "res/shipMove.wav";
	
	/**
	 * Sound file reference - res/battleWin.wav
	 */
	public static String WIN = "res/battleWin.wav";
	/**
	 * Sound file reference - res/battleLose.wav
	 */
	public static String LOSE = "res/battleLose.wav";
	/**
	 * Sound file reference - res/turnSwitch.wav
	 */
	public static String TURN_SWITCH = "res/battleLose.wav";

	
	/**
	 * Sound file reference - res/silence.wav
	 */
	public static String NULL = "res/silence.wav";
	
	private static double gain = 0.0;
	private static Clip soundClip = null;
	
	/**
	 * Pre-loads a sound effect from the source file and plays it once.
	 * @param soundClipSrc the source file name
	 */
	public static void play(String soundClipSrc)
	{
		try
		{
			File soundFile = new File(soundClipSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			soundClip = AudioSystem.getClip();
			soundClip.open(ais);
			
			FloatControl gainControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue((float) gain);
			
			soundClip.start();
			
		} catch (Exception e) {e.printStackTrace();}
		Main.debugStream.println("SFX is playing.");
	}
	
	/**
	 * Pre-loads a sound effect from the source file, plays it once, then loops it.
	 * @param soundClipSrc the source file name
	 * @param nTimes the number of times the sound should be looped
	 */
	public static void loop(String soundClipSrc, int nTimes)
	{
		try
		{
			File soundFile = new File(soundClipSrc);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			Clip soundClip = AudioSystem.getClip();
			soundClip.open(ais);
			
			FloatControl gainControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue((float) gain);
			
			soundClip.loop(nTimes);
			
		} catch (Exception e) {}
		Main.debugStream.println("SFX is looping.");
	}
	
	/**
	 * Stops the currently playing SFX, if there is any SFX playing.
	 */
	public static void stop()
	{
		try
		{
			if (soundClip == null)
			{
				return;
			}
			soundClip.stop();
			soundClip.setFramePosition(0);
		} catch (Exception e) {}
		Main.debugStream.println("SFX is stopped.");
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
	}
}
