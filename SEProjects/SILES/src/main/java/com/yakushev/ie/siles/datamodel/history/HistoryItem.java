package com.yakushev.ie.siles.datamodel.history;

public class HistoryItem {
	private boolean silesSay;
	private String message;
	
	public HistoryItem(String message, boolean isSiles) {
		silesSay = isSiles;
		this.message = message;
	}	
	
	public boolean isSilesSay() {
		return silesSay;
	}
	
	public void setSilesSay(boolean silesSay) {
		this.silesSay = silesSay;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
