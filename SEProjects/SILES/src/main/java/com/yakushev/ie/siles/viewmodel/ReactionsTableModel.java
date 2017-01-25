package com.yakushev.ie.siles.viewmodel;

import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.yakushev.ie.siles.datamodel.reaction.Reaction;

public class ReactionsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 6253484326782096317L;
	
	private static final String[] TXT_COLUMNNAMES = {"Pattern", "Reaction"};
	
	private List<Reaction> teachReactions;

	public ReactionsTableModel(List<Reaction> tr) {
		teachReactions = tr;
	}
	
	public int getColumnCount() {
		return 2;
	}

	public int getRowCount() {
		return teachReactions.size();
	}
	
	public String getColumnName(int col) {
		return TXT_COLUMNNAMES[col];
	}

	public Object getValueAt(int row, int col) {
		String cellValue = "";
		List<String> valueList;
		
		switch (col) {
		case 0:
			valueList = teachReactions.get(row).getPatternTags();
			break;
		case 1:
			valueList = teachReactions.get(row).getReactions();
			break;
		default:
			return cellValue;
		}
		
		for (String valueItem : valueList) {
			cellValue += valueItem + "\n";
		}
		
		return cellValue;
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}
	
	public void setValueAt(Object value, int row, int col) {
		if (value == null) {
			return;
		}
		
		List<String> valueItems = Arrays.asList(((String) value).split("\n"));
				
		switch (col) {
		case 0:
			teachReactions.get(row).setPatternTags(valueItems);
			break;
		case 1:
			teachReactions.get(row).setReactions(valueItems);
			break;
		}
	}
}
