package com.waveditor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WavPanel extends JPanel {
	final static long serialVersionUID = 0001;
	final private double OFFSET_GRAPH = 0.01;
	
	int Xmax,Ymin,Ymax,widthPanM,heightPanM,offsetPanM;
	double XPointerBeg,XPointerEnd;
	boolean prizWork;
	WavProc wavD;
	
	WavPanel() {
		prizWork = false;
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me)	{
				if (!prizWork) return;
				
				if (me.isShiftDown()) XPointerEnd = 1.0*me.getX()/widthPanM; else XPointerBeg = 1.0*me.getX()/widthPanM;
				
				if (XPointerEnd<0) XPointerEnd = 0;
				if (XPointerEnd>1) XPointerEnd = 1;
				if (XPointerBeg<0) XPointerBeg = 0;
				if (XPointerBeg>1) XPointerBeg = 1;
			}
			
			public void mouseReleased(MouseEvent me) {
				XPointerEnd = 1.0*me.getX()/widthPanM;
				
				if (XPointerEnd<0) XPointerEnd = 0;
				if (XPointerEnd>1) XPointerEnd = 1;
				
				repaint();
			}
		});	
	}
	
	private int getPanelX(double XX) {
		return (int)(1.0*widthPanM*(XX/Xmax)+(offsetPanM/2));
	}
	
	private int getPanelY(double YY) {
		return (int)(1.0*heightPanM*((Ymax-YY)/(Ymax-Ymin))+(offsetPanM/2));
	}
	
	public void setDataWav(WavProc w) {
		wavD = w;
		Xmax = wavD.getMaxSamples();
		Ymax = wavD.getMaxAmp();
		Ymin = -Ymax;
		prizWork = true;
		this.repaint();
	}
	
	protected void paintComponent(Graphics g) {
		if (!prizWork) return;
		widthPanM = this.getWidth();
		heightPanM = this.getHeight();
		offsetPanM = (widthPanM>heightPanM)? (int)(OFFSET_GRAPH*heightPanM):(int)(OFFSET_GRAPH*widthPanM);
		
		g.clearRect(0, 0, widthPanM, heightPanM);
		
		widthPanM -= (offsetPanM);
		heightPanM -= (offsetPanM);
		
		g.setColor(Color.green);
		g.drawLine((int) (XPointerEnd*widthPanM), getPanelY(Ymax), (int) (XPointerEnd*widthPanM), getPanelY(Ymin));
		
		g.setColor(Color.red);
		g.drawLine((int) (XPointerBeg*widthPanM), getPanelY(Ymax), (int) (XPointerBeg*widthPanM), getPanelY(Ymin));
		
		g.setColor(Color.DARK_GRAY);
		g.drawLine((int) (XPointerBeg*widthPanM), getPanelY(Ymax), (int) (XPointerEnd*widthPanM), getPanelY(Ymax));
		g.drawLine((int) (XPointerEnd*widthPanM), getPanelY(Ymin), (int) (XPointerBeg*widthPanM), getPanelY(Ymin));
		
		if (wavD.getChanels() == 1)	{
			int[] wavDataMono = wavD.getDataAmplitudeMono();
			
			Xmax = wavDataMono.length;
			
			g.setColor(Color.BLACK);
			for (int i=1;i<Xmax;i++) {
				g.drawLine(getPanelX(i-1), getPanelY(wavDataMono[i-1]), getPanelX(i), getPanelY(wavDataMono[i]));
			}
			
			g.setColor(Color.BLUE);
			g.drawLine(getPanelX(0), getPanelY(0), getPanelX(Xmax), getPanelY(0));
		}
		if (wavD.getChanels() == 2)	{
			int[] wavDataLeft = wavD.getDataAmplitudeLeft();
			int[] wavDataRight = wavD.getDataAmplitudeRight();
			
			Xmax = wavDataLeft.length;
			
			g.setColor(Color.BLACK);
			for (int i=1;i<Xmax;i++) {
				g.drawLine(getPanelX(i-1), getPanelY((wavDataLeft[i-1]-Ymin)/2), getPanelX(i), getPanelY((wavDataLeft[i]-Ymin)/2));
				g.drawLine(getPanelX(i-1), getPanelY((wavDataRight[i-1]-Ymax)/2), getPanelX(i), getPanelY((wavDataRight[i]-Ymax)/2));
			}
			
			g.setColor(Color.BLUE);
			g.drawLine(getPanelX(0), getPanelY((-Ymin)/2), getPanelX(Xmax), getPanelY((-Ymin)/2));
			g.drawLine(getPanelX(0), getPanelY((-Ymax)/2), getPanelX(Xmax), getPanelY((-Ymax)/2));
		}
	}
	
	public double getXPointerBegin() {
		return (XPointerBeg<XPointerEnd)?XPointerBeg:XPointerEnd;
	}
	
	public double getXPointerEnd() {
		return (XPointerBeg>XPointerEnd)?XPointerBeg:XPointerEnd;
	}
}
