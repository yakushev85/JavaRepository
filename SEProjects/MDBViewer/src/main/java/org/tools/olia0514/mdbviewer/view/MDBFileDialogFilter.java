package org.tools.olia0514.mdbviewer.view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MDBFileDialogFilter extends FileFilter {
	private String ext;
	
	public MDBFileDialogFilter() {
		this.ext = MDBFileDialogWindow.EXT_MDB_FILE;
	}
	
	public MDBFileDialogFilter(String ext) {
		this.ext = ext;
	}

	@Override
	public boolean accept(File f) {
		String fileName = f.getName();
		if (f.isDirectory()) return true;
		if (fileName.lastIndexOf('.') == fileName.length()) {
			return false;
		} else {
			return (fileName.substring(fileName.lastIndexOf('.')+1)).equalsIgnoreCase(ext);
		}
	}

	@Override
	public String getDescription() {
		return "*."+ext;
	}
	
}
