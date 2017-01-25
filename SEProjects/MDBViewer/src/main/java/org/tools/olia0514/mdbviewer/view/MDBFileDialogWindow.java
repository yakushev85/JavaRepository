package org.tools.olia0514.mdbviewer.view;

import java.io.File;

import javax.swing.JFileChooser;

public class MDBFileDialogWindow extends JFileChooser {
	private static final long serialVersionUID = -5538707640346017123L;
	private static final String TXT_TITLE = "Open MDB File Dialog";
	
	public static final String EXT_MDB_FILE = "mdb";
	public static final String EXT_XLS_FILE = "xls";
	
	public MDBFileDialogWindow() {
		super();
		initialization(EXT_MDB_FILE);
	}
	
	public MDBFileDialogWindow(String extension) {
		super();
		initialization(extension);
	}
	
	public void initialization(String ext) {
		this.setDialogTitle(TXT_TITLE);
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setCurrentDirectory(new File("."));
		this.setFileFilter(new MDBFileDialogFilter(ext));
	}
}
