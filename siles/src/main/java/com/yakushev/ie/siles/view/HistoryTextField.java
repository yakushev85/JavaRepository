package com.yakushev.ie.siles.view;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

public class HistoryTextField extends JTextField {
	private static final long serialVersionUID = -5136689984518543669L;

	private int pointToHistory;
	private List<String> historyList;
	
	public HistoryTextField(int size) {
		super(size);
		historyList = new ArrayList<>();
		pointToHistory = 0;
	}
	
	public void addHistoryData() {
		addHistoryData(this.getText());
	}
	
	public void addHistoryData(String data) {
		if (data == null || data.isEmpty()) return;
		
		historyList.add(data);
		pointToHistory = historyList.size();
	}
	
	public void setHistoryList(List<String> dataList) {
		historyList = dataList;
	}
	
	public List<String> getHistoryList() {
		return historyList;
	}
	
	private void validatePointToHistory() {
		if (pointToHistory >= historyList.size()) pointToHistory = historyList.size() - 1;
		if (pointToHistory < 0) pointToHistory = 0;
	}
	
	private String getItemHistory(boolean isUp) {
		if (historyList.size() <= 0) return null;
		
		pointToHistory = (isUp)?pointToHistory-1:pointToHistory+1;
		validatePointToHistory();
		
		return historyList.get(pointToHistory);
	}
	
	protected void processKeyEvent(KeyEvent ke) {
		super.processKeyEvent(ke);
		
		int keyCode = ke.getKeyCode();
		
		if (ke.getID() != KeyEvent.KEY_PRESSED) return;
		
		String historyItem = null;
		
		if (keyCode == KeyEvent.VK_UP) {
			historyItem = getItemHistory(true);
		} else if (keyCode == KeyEvent.VK_DOWN) {
			historyItem = getItemHistory(false);
		}
	
		if (historyItem != null) this.setText(historyItem);
	}
}
