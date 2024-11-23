package com.yakushev.ie.siles.controller;

import javax.swing.SwingUtilities;

import com.yakushev.ie.siles.datamodel.MainModel;
import com.yakushev.ie.siles.view.MainWindow;

public class RunController {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				MainModel mainModel = new MainModel();
				DataController dataController = new DataController(mainModel);
				MainWindow mainWindow = new MainWindow(dataController);

				mainModel.registerObserver(mainWindow);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		});
	}
}
