package com.iakushev.soft.weblinksdownloader.controller;

import javax.swing.SwingUtilities;

import com.iakushev.soft.weblinksdownloader.view.MainWindow;

public class MainRun {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DownloaderController downloaderController = new DownloaderController();
				MainWindow mainWindow = new MainWindow(downloaderController);
				downloaderController.registerObserver(mainWindow);
			}
		});
	}

}
