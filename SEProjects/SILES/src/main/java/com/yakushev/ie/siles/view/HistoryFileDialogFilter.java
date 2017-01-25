package com.yakushev.ie.siles.view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class HistoryFileDialogFilter extends FileFilter {
	public static String TXT_EXTHISTORY = "history";
	public static String TXT_DESCRHISTORY = "History file with Siles talks.";
	
	@Override
	public boolean accept(File f) {
		String FileName = f.getName();
		if (f.isDirectory()) return true;
		if (FileName.lastIndexOf('.') == FileName.length()) {
			return false;
		} else {
			return (FileName.substring(FileName.lastIndexOf('.')+1).equals(TXT_EXTHISTORY));
		}
	}

	@Override
	public String getDescription() {
		return TXT_DESCRHISTORY;
	}

}
