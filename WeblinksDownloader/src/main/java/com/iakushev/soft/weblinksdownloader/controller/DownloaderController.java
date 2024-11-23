package com.iakushev.soft.weblinksdownloader.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.iakushev.soft.weblinksdownloader.datamodel.DownloaderModel;
import com.iakushev.soft.weblinksdownloader.observ.Observable;
import com.iakushev.soft.weblinksdownloader.observ.Observer;

public class DownloaderController implements Observable, Runnable {
	private List<Observer> observers;
	private DownloaderModel downloaderModel;
	
	public DownloaderController() {
		observers = new ArrayList<Observer>();
		downloaderModel = null;
	}
	
	public void setURL(String url) {
		downloaderModel = new DownloaderModel(url);
	}
	
	public void setSettingsForDownload(String regEx, int level) {
		downloaderModel.setPatternRegEx(regEx);
		downloaderModel.setLevel(level);
	}
		
	public void startThread() {
		new Thread(this).start();
	}

	public void registerObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	public void notifyObservers(Object obj) {
		for (Observer observer : observers) {
			observer.updateObserver(obj);
		}
	}

	public void run() {
		if (downloaderModel != null) {
			int count = 0;
			notifyObservers("Collecting URLs, please wait!");
			List<String> allWebLinks = downloaderModel.getWebLinks();
			notifyObservers("Found "+allWebLinks.size()+" URLs");
				
			for (String webLink : allWebLinks) {
				if (downloaderModel.isWebLinkForDownload(webLink) && downloaderModel.isWebLinkMatchPattern(webLink)) {
					notifyObservers(webLink+ " ... scanning");
					try {
						notifyObservers(downloaderModel.getFileFromWebLink(webLink));
						count++;
					} catch (IOException e) {
						notifyObservers("Fail to download "+webLink+": "+e.getMessage());
					}
				}
			}
					
			notifyObservers("Downloaded "+count+" files.");
			notifyObservers(null);
		}
	}
}
