package com.smjourney.entity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.smjourney.audio.SFX;
import com.smjourney.smjourney.Main;

/**
 * Small-sized Debris with a size of 1.
 */
public class SmallDebris extends Debris
{
	
	private BufferedImage sprite;
	
	/**
	 * Constructs small-sized Debris with a size of 1 and an armor durability of 50.
	 */
	public SmallDebris()
	{
		super(1);
		setEntityID(Entity.SMALLDEBRIS);
		setMaxArmorDurability(50);
		setArmorDurability(50);
		updateSprite();
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "SmallDebris[";
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
		try { sprite = ImageIO.read(new File("res/Debris (Small) (Scaled).png")); } catch (Exception e) {
			Main.errorStream.println("Debris (Small) Image could not be found");
		}
	}
	
	/**
	 * Retrieves the DESTRYOED SFX source file path associated with the Entity.
	 * return the DESTROYED SFX
	 */
	public String getDestroyedSfxSrc()
	{
		return SFX.SHIP_DESTROY_SM;
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
		return SFX.SHIP_DAMAGE_SM;
	}

}
