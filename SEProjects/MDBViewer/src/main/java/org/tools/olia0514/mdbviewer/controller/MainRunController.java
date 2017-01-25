package org.tools.olia0514.mdbviewer.controller;

import javax.swing.SwingUtilities;

import org.tools.olia0514.mdbviewer.view.ViewerMainWindow;

public class MainRunController {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new ViewerMainWindow();
			}
		});
	}
}
