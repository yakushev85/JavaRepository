package org.tools.olia0514.mdbviewer.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public class MDBTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -9083347794833081870L;
	private static final int MAX_ROW_COUNT = 1000;

	private Table mdbTable;
	private List<? extends Column> listColumn;
	
	public MDBTableModel(Table mdbTable) {
		this.mdbTable = mdbTable;
		listColumn = mdbTable.getColumns();
	}
	
	public int getColumnCount() {
		return mdbTable.getColumnCount();
	}

	public int getRowCount() {
		int curMaxRowCount = mdbTable.getRowCount();
		
		return (curMaxRowCount > MAX_ROW_COUNT)? MAX_ROW_COUNT:curMaxRowCount;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		int indexRow = 0; 
		
		for (Row row : mdbTable) {
			if (indexRow == rowIndex) {
				Object cellValue = row.get(listColumn.get(columnIndex).getName());
				return (cellValue == null)?"":cellValue.toString();
			}
			
			indexRow++;
		}
		
		return "";
	}

	
	public String getColumnName(int columnIndex) {
		return listColumn.get(columnIndex).getName();
	}
}
