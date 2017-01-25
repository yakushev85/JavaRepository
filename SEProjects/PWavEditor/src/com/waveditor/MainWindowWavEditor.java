package com.waveditor;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.awt.event.*;
import java.awt.*;
import java.io.File;

public class MainWindowWavEditor extends JFrame implements ActionListener {
	final static long serialVersionUID = 0001;
	private String TEXT_TITLEFRM = "MiniWavEditor";
	private JMenuBar menuBar;
	private JMenu menuFile,menuEdit,menuHelp;
	private JMenuItem itemFileOpen,itemFileSave,itemFileExit;
	private JMenuItem itemEditCopy,itemEditCut,itemEditPaste;
	private JMenuItem itemHelpAbout;
	private WavProc wavP;
	private WavPanel pnlWav;
	private boolean isLoadedWav;
	
	class MFileFilter extends FileFilter {
		private String extIEI,desIEI;
		
		MFileFilter(String ext, String des)	{
			extIEI = ext;
			desIEI = des;
		}
		
		public boolean accept(File f) {
			 String FileName = f.getName();
			 if (f.isDirectory()) return true;
			 if (FileName.lastIndexOf('.') == FileName.length()) return false;
			 else
			 return (FileName.substring(FileName.lastIndexOf('.')+1).equals(extIEI));
		}
		
		public String getDescription() {
			return desIEI;
		}
	}
	
	MainWindowWavEditor() {	
		isLoadedWav = false;
		
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuEdit = new JMenu("Edit");
		menuEdit.setMnemonic(KeyEvent.VK_E);
		menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		
		
		itemFileOpen = new JMenuItem("open");
		itemFileOpen.setMnemonic(KeyEvent.VK_O);
		itemFileOpen.addActionListener(this);
		menuFile.add(itemFileOpen);
		
		itemFileSave = new JMenuItem("save");
		itemFileSave.setMnemonic(KeyEvent.VK_S);
		itemFileSave.addActionListener(this);
		menuFile.add(itemFileSave);
		
		itemFileExit = new JMenuItem("exit");
		itemFileExit.setMnemonic(KeyEvent.VK_X);
		itemFileExit.addActionListener(this);
		menuFile.add(itemFileExit);
		
		itemEditCopy = new JMenuItem("copy");
		itemEditCopy.setMnemonic(KeyEvent.VK_C);
		itemEditCopy.addActionListener(this);
		menuEdit.add(itemEditCopy);
		
		itemEditCut = new JMenuItem("cut");
		itemEditCut.setMnemonic(KeyEvent.VK_U);
		itemEditCut.addActionListener(this);
		menuEdit.add(itemEditCut);
		
		itemEditPaste = new JMenuItem("paste");
		itemEditPaste.setMnemonic(KeyEvent.VK_P);
		itemEditPaste.addActionListener(this);
		menuEdit.add(itemEditPaste);
		
		itemHelpAbout = new JMenuItem("about");
		itemHelpAbout.setMnemonic(KeyEvent.VK_A);
		itemHelpAbout.addActionListener(this);
		menuHelp.add(itemHelpAbout);
		
		menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuHelp);
		
		this.setJMenuBar(menuBar);
		
		pnlWav = new WavPanel();
		pnlWav.setPreferredSize(new Dimension(500,400));
		
		this.add(pnlWav);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(640,480));
		this.setTitle(TEXT_TITLEFRM);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == itemFileOpen)	{
			JFileChooser dlgF = new JFileChooser();
			dlgF.setDialogTitle("Choose wav file");
			dlgF.setFileSelectionMode(JFileChooser.FILES_ONLY);
			dlgF.setCurrentDirectory(new File("."));
			dlgF.setFileFilter(new MFileFilter("wav","*.wav"));
			if (dlgF.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try	{
					wavP = new WavProc(dlgF.getSelectedFile().toString());
					wavP.ReadDataWav();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Invalid file format!");
					return;
				}
				
				pnlWav.setDataWav(wavP);
				this.setTitle(TEXT_TITLEFRM+" Opened file:"+dlgF.getSelectedFile().toString());
				isLoadedWav = true;
				
				//System.out.println(dlgF.getSelectedFile().toString()+" "+wavP);
			}
		}
		if ((ae.getSource() == itemFileSave)&&(isLoadedWav)) {
			JFileChooser dlgF = new JFileChooser();
			dlgF.setDialogTitle("Choose wav file");
			dlgF.setFileSelectionMode(JFileChooser.FILES_ONLY);
			dlgF.setCurrentDirectory(new File("."));
			dlgF.setFileFilter(new MFileFilter("wav","*.wav"));
			if (dlgF.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				wavP.writeToFile(dlgF.getSelectedFile().toString());
			}
		}
		if (ae.getSource() == itemFileExit)	{
			System.exit(0);
		}
		if ((ae.getSource() == itemEditCopy)&&(isLoadedWav)) {
			wavP.copyToBuf((int)(1.0*pnlWav.getXPointerBegin()*(wavP.getMaxSamples()-1)), 
					(int)(1.0*pnlWav.getXPointerEnd()*(wavP.getMaxSamples()-1)));
			pnlWav.setDataWav(wavP);
		}
		if ((ae.getSource() == itemEditCut)&&(isLoadedWav))	{
			if (pnlWav.getXPointerBegin()!=pnlWav.getXPointerEnd())
				wavP.cutToBuf((int)(1.0*pnlWav.getXPointerBegin()*(wavP.getMaxSamples()-1)),
				(int)(1.0*pnlWav.getXPointerEnd()*(wavP.getMaxSamples()-1)));
			pnlWav.setDataWav(wavP);
		}
		if ((ae.getSource() == itemEditPaste)&&(isLoadedWav)) {
			wavP.pasteFromBuf((int)(1.0*pnlWav.getXPointerBegin()*(wavP.getMaxSamples()-1)));
			pnlWav.setDataWav(wavP);
		}
		if (ae.getSource() == itemHelpAbout) {
			JOptionPane.showMessageDialog(null, "Program produced by Alexandr Jakushev.");
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainWindowWavEditor();
			}
		});
	}
}
