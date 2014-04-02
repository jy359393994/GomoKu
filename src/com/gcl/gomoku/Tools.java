package com.gcl.gomoku;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Tools {

	public static final int getScreenWidth(){
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension d = toolkit.getScreenSize();
		return d.width;
	}
	
	public static final int getScreenHeight(){
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension d = toolkit.getScreenSize();
		return d.height;
	}




}
