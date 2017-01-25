package com.yakushev.ie.siles.view;

import java.io.File;

import javax.swing.JFileChooser;

public class HistoryFileDialogWindow extends JFileChooser {
	private static final long serialVersionUID = -4990854526151139512L;

	public HistoryFileDialogWindow(String title) {
		super();
		
		this.setDialogTitle(title);
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setCurrentDirectory(new File("."));
		this.setFileFilter(new HistoryFileDialogFilter());
	}
}
