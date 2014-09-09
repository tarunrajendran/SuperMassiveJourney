package com.smjourney.gui;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowCloser extends WindowAdapter{
	public void windowClosing(WindowEvent wevnt){
		Window twindow = wevnt.getWindow();
		twindow.setVisible(false);
		twindow.dispose();
		System.exit(0);
	}

}
