package com.smjourney.gui;

import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class AnimatedImage 
{
	
	public static int DAMAGED_SM;
	public static int DAMAGED_MD;
	public static int DAMAGED_LG;
	
	public static int DESTROYED_SM;
	public static int DESTROYED_MD;
	public static int DESTROYED_LG;
	
	private ArrayList<BufferedImage> frames;
	
	public AnimatedImage(int image)
	{
		frames = new ArrayList<BufferedImage>();
		
		if (image == DAMAGED_SM)
		{
			try
			{
				frames.add(ImageIO.read(new File("res/damagedSM/damagedSM1.png")));
				frames.add(ImageIO.read(new File("res/damagedSM/damagedSM2.png")));
				frames.add(ImageIO.read(new File("res/damagedSM/damagedSM3.png")));
				frames.add(ImageIO.read(new File("res/damagedSM/damagedSM4.png")));
				frames.add(ImageIO.read(new File("res/damagedSM/damagedSM5.png")));
			}
			catch (Exception e) {}
		}
		if (image == DAMAGED_MD)
		{
			try
			{
				frames.add(ImageIO.read(new File("res/damagedMD/damagedMD1.png")));
				frames.add(ImageIO.read(new File("res/damagedMD/damagedMD2.png")));
				frames.add(ImageIO.read(new File("res/damagedMD/damagedMD3.png")));
				frames.add(ImageIO.read(new File("res/damagedMD/damagedMD4.png")));
				frames.add(ImageIO.read(new File("res/damagedMD/damagedMD5.png")));
			}
			catch (Exception e) {}
		}
		if (image == DAMAGED_LG)
		{
			try
			{
				frames.add(ImageIO.read(new File("res/damagedLG/damagedLG1.png")));
				frames.add(ImageIO.read(new File("res/damagedLG/damagedLG2.png")));
				frames.add(ImageIO.read(new File("res/damagedLG/damagedLG3.png")));
				frames.add(ImageIO.read(new File("res/damagedLG/damagedLG4.png")));
				frames.add(ImageIO.read(new File("res/damagedLG/damagedLG5.png")));
			}
			catch (Exception e) {}
		}
		if (image == DESTROYED_SM)
		{
			try
			{
				frames.add(ImageIO.read(new File("res/destroyedSM/destroyedSM1.png")));
				frames.add(ImageIO.read(new File("res/destroyedSM/destroyedSM2.png")));
				frames.add(ImageIO.read(new File("res/destroyedSM/destroyedSM3.png")));
				frames.add(ImageIO.read(new File("res/destroyedSM/destroyedSM4.png")));
				frames.add(ImageIO.read(new File("res/destroyedSM/destroyedSM5.png")));
			}
			catch (Exception e) {}
		}
		if (image == DESTROYED_MD)
		{
			try
			{
				frames.add(ImageIO.read(new File("res/destroyedMD/destroyedMD1.png")));
				frames.add(ImageIO.read(new File("res/destroyedMD/destroyedMD2.png")));
				frames.add(ImageIO.read(new File("res/destroyedMD/destroyedMD3.png")));
				frames.add(ImageIO.read(new File("res/destroyedMD/destroyedMD4.png")));
				frames.add(ImageIO.read(new File("res/destroyedMD/destroyedMD5.png")));
			}
			catch (Exception e) {}
		}
		if (image == DESTROYED_LG)
		{
			try
			{
				frames.add(ImageIO.read(new File("res/destroyedLG/destroyedLG1.png")));
				frames.add(ImageIO.read(new File("res/destroyedLG/destroyedLG2.png")));
				frames.add(ImageIO.read(new File("res/destroyedLG/destroyedLG3.png")));
				frames.add(ImageIO.read(new File("res/destroyedLG/destroyedLG4.png")));
				frames.add(ImageIO.read(new File("res/destroyedLG/destroyedLG5.png")));
			}
			catch (Exception e) {}
		}
	}
	
	public BufferedImage getFrame(int frameNumber)
	{
		return frames.get(frameNumber - 1);
	}

}
