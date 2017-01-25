package com.yakushev.ie.siles.datamodel.history;

import java.util.ArrayList;
import java.util.List;

public class HistoryModel {
	private List<HistoryItem> historyList;

	public HistoryModel() {
		historyList = new ArrayList<HistoryItem> ();
	}
	
	public String getLastSilesMessage() {
		String lastSilesMsg = "";
		
		for (HistoryItem hItem : historyList) {
			if (hItem.isSilesSay()) {
				lastSilesMsg = hItem.getMessage();
			}
		}
		
		return lastSilesMsg;
	}
	
	public String getLastUserMessage() {
		String lastUserMsg = "";
		
		for (HistoryItem hItem : historyList) {
			if (!hItem.isSilesSay()) {
				lastUserMsg = hItem.getMessage();
			}
		}
		
		return lastUserMsg;
	}
	
	public List<HistoryItem> getHistoryItems() {
		return historyList;
	}
}
