package com.iakushev.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

class IntroCanvas extends JPanel {
	private final static long serialVersionUID = 0001;
	private final int OFFSET_INTROX = 1;
	private final int OFFSET_INTROY = 1;
	private final int DIV_WIDTH_INTRO = 40;
	private final int MAX_NCOLORS = 6;
	private final int N_INTROXY = 108;
	private final int TEXT_INTROXY[][] ={
			//T
			{0,0},
			{1,0},
			{2,0},
			{3,0},
			{4,0},
			{5,0},
			{6,0},
			{3,0},
			{3,1},
			{3,2},
			{3,3},
			{3,4},
			{3,5},
			{3,6},
			{3,7},
			{3,8},
			//E
			{8,0},
			{9,0},
			{10,0},
			{11,0},
			{8,4},
			{9,4},
			{10,4},
			{8,8},
			{9,8},
			{10,8},
			{11,8},
			{8,1},
			{8,2},
			{8,3},
			{8,5},
			{8,6},
			{8,7},
			//T
			{13,0},
			{14,0},
			{15,0},
			{16,0},
			{17,0},
			{18,0},
			{19,0},
			{16,0},
			{16,1},
			{16,2},
			{16,3},
			{16,4},
			{16,5},
			{16,6},
			{16,7},
			{16,8},
			//R
			{22,0},
			{23,0},
			{24,0},
			{25,0},
			{26,1},
			{26,2},
			{26,3},
			{22,4},
			{23,4},
			{24,4},
			{25,4},
			{23,5},
			{24,6},
			{25,7},
			{26,8},
			{21,0},
			{21,1},
			{21,2},
			{21,3},
			{21,4},
			{21,5},
			{21,6},
			{21,7},
			{21,8},
			//i
			{28,0},
			{28,2},
			{28,3},
			{28,4},
			{28,5},
			{28,6},
			{28,7},
			{28,8},
			//S
			{30,0},
			{31,0},
			{32,0},
			{33,0},
			{34,0},
			{35,0},
			{36,0},
			{30,1},
			{30,2},
			{30,3},
			{30,4},
			{31,4},
			{32,4},
			{33,4},
			{34,4},
			{35,4},
			{36,4},
			{36,5},
			{36,6},
			{36,7},
			{30,8},
			{31,8},
			{32,8},
			{33,8},
			{34,8},
			{35,8},
			{36,8}
	};
	
	public IntroCanvas() {
		super();
	}
	
	private Color decodeColor(int cc) {
		switch (cc)	{
		case 0:return new Color(0xFF0000);
		case 1:return new Color(0x00FF00);
		case 2:return new Color(0x0000FF);
		case 3:return new Color(0xFF00FF);
		case 4:return new Color(0x00FFFF);
		case 5:return new Color(0x000000);
		default:return new Color(0xABABAB);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Random rnd = new Random();
		int maxWidth = getWidth();
		int maxHeight = getHeight();
		int sizeCub = maxWidth / DIV_WIDTH_INTRO;
		
		g.clearRect(0, 0, maxWidth, maxHeight);
		
		for (int i=0;i<N_INTROXY;i++) {
				g.setColor(decodeColor(rnd.nextInt(MAX_NCOLORS)));
				g.fillRect(OFFSET_INTROX*sizeCub+TEXT_INTROXY[i][0]*sizeCub-1, 
						OFFSET_INTROY*sizeCub+TEXT_INTROXY[i][1]*sizeCub-1, sizeCub-2, sizeCub-2);
		}
	}
}