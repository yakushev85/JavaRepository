package com.yakushev.ie.siles.controller;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;

import com.yakushev.ie.siles.datamodel.MainModel;
import com.yakushev.ie.siles.log.LogSiles;
import com.yakushev.ie.siles.view.MainWindow;

public class RunController {
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				LogSiles.cleanLogFile();
				Logger logger = LogSiles.getConfiguredLogger(RunController.class);
				
				try {
					MainModel mainModel = new MainModel();
					DataController dataController = new DataController(mainModel);
					MainWindow mainWindow = new MainWindow(dataController);
					
					mainModel.registerObserver(mainWindow);
				} catch (IOException e) {
					logger.error(e.getMessage());
				} catch (XMLStreamException e) {
					logger.error(e.getMessage());
				}
			}
		});
	}
}
