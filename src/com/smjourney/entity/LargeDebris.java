package com.smjourney.entity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.smjourney.audio.SFX;
import com.smjourney.smjourney.Main;

/**
 * Large-sized debris with a size of 3.
 */
public class LargeDebris extends Debris
{
	
	private BufferedImage sprite;
	
	/**
	 * Large-sized debris with a size of 3 and an armor durability of 150.
	 */
	public LargeDebris()
	{
		super(3);
		setEntityID(Entity.LARGEDEBRIS);
		setMaxArmorDurability(150);
		setArmorDurability(150);
		updateSprite();
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "LargeDebris[";
		s += "armor: " + getArmorDurability();
		s += "]";
		
		return s;
	}

	/**
	 * Retrieves the sprite image associated with the Entity
	 * @return sprite the sprite image
	 */
	public BufferedImage getSprite()
	{
		updateSprite();
		return sprite;
	}
	
	/**
	 * Updates the sprite image associated with the Entity
	 */
	public void updateSprite()
	{
		try { sprite = ImageIO.read(new File("res/Debris (Large) (Scaled).png")); } catch (Exception e) {
			Main.errorStream.println("Debris (large) could not be loaded");
		}
	}
	
	/**
	 * Retrieves the DESTRYOED SFX source file path associated with the Entity.
	 * return the DESTROYED SFX
	 */
	public String getDestroyedSfxSrc()
	{
		return SFX.DEBRIS_DESTROY_MD;
	}
	
	/**
	 * Retrieves the FIRE SFX source file path associated with the Entity.
	 * return the FIRE SFX
	 */
	public String getFireSfxSrc()
	{
		return SFX.NULL;
	}
	
	/**
	 * Retrieves the DAMAGED SFX source file path associated with the Entity.
	 * return the DAMAGED SFX
	 */
	public String getDamagedSfxSrc()
	{
		return SFX.DEBRIS_DAMAGE_MD;
	}
}
