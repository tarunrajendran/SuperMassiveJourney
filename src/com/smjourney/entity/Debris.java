package com.smjourney.entity;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.smjourney.audio.SFX;
import com.smjourney.map.Direction;
import com.smjourney.smjourney.Main;

/**
 * An inert Entity that serves no purpose other than to take up space in the battle sequence.
 * This can be destroyed.
 */
public class Debris extends Entity implements Destructible
{
	
	private BufferedImage sprite;
	
	/**
	 * Constructs Debris of a specific size and a moderate armor durability of 100.
	 * @param size the size of the debris
	 */
	public Debris(int size)
	{
		super(size);
		setEntityID(Entity.DEBRIS);
		setMaxArmorDurability(100);
		setArmorDurability(100);
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Constructs Debris of size 1 and a moderate armor durability of 100.
	 */
	public Debris()
	{
		super(1);
		setEntityID(Entity.DEBRIS);
		setMaxArmorDurability(100);
		setArmorDurability(100);
		updateSprite();
		Main.debugStream.println(this.toString() + " created.");
	}
	
	/**
	 * Returns string-form information on the object.
	 */
	public String toString()
	{
		String s = "";
		
		s += "Debris[";
		s += "size: " + getSize() + ", ";
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
			Main.errorStream.println(" Debris (Small) could not be loaded");
		}
	}
	
	/**
	 * Retrieves the DESTRYOED SFX source file path associated with the Entity.
	 * return the DESTROYED SFX
	 */
	public String getDestroyedSfxSrc()
	{
		return SFX.DEBRIS_DESTROY_SM;
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
		return SFX.DEBRIS_DAMAGE_SM;
	}

}
