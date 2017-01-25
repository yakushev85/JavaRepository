package org.tools.olia0514.mdbviewer.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.util.ExportUtil;

public class ViewerMainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -5882925366588789017L;
	private static final String TXT_TITLE = "MDB Viewer";
	
	private JComboBox<String> cmbTableNames;
	private JMenuItem itemFileOpen, itemFileExport, itemFileExit, itemEditSettings, itemHelpAbout;
	private JTable tblData;
	private Database dataBase;
	private Set<String> tableNames;
	
	public ViewerMainWindow() {
		dataBase = null;
		tableNames = null;
		
		initializeWindow();
		initializeComponents();
		this.setVisible(true);
	}

	public void initializeWindow() {
		this.setSize(800, 600);
		this.setLocation(50, 50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(TXT_TITLE);
		//this.setResizable(false);
	}
	
	public void initializeComponents() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		JMenu menuEdit = new JMenu("Edit");
		JMenu menuHelp = new JMenu("Help");
		
		itemFileOpen = new JMenuItem("open");
		itemFileOpen.addActionListener(this);
		itemFileExport = new JMenuItem("export");
		itemFileExport.addActionListener(this);
		itemFileExit = new JMenuItem("exit");
		itemFileExit.addActionListener(this);
		
		JMenuItem itemEditSettings = new JMenuItem("settings");
		itemEditSettings.addActionListener(this);
		
		JMenuItem itemHelpAbout = new JMenuItem("about");
		itemHelpAbout.addActionListener(this);
		
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuHelp);
		
		menuFile.add(itemFileOpen);
		menuFile.add(itemFileExport);
		menuFile.add(itemFileExit);
		
		menuEdit.add(itemEditSettings);
		
		menuHelp.add(itemHelpAbout);
		
		cmbTableNames = new JComboBox<String>();
		cmbTableNames.addActionListener(this);
		
		tblData = new JTable();
		JScrollPane scpData = new JScrollPane(tblData);
		
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbl.setConstraints(cmbTableNames, gbc);
		
		gbc.weightx = 1.0;
		gbl.setConstraints(scpData, gbc);
		
		this.setJMenuBar(menuBar);
		this.setLayout(gbl);
		this.add(cmbTableNames);
		this.add(scpData);
	}

	private void loadMDBTable() {
		if (dataBase == null) {
			JOptionPane.showMessageDialog(this, "Please select MDB file!");
			return;
		}
		
		try {
			Table curTable = dataBase.getTable((String) cmbTableNames.getSelectedItem());
			
			tblData.setModel(new MDBTableModel(curTable));
			tblData.repaint();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object srcObject = ae.getSource();
		if (srcObject == itemFileOpen) {
			MDBFileDialogWindow mdbFileDialogWindow = new MDBFileDialogWindow(MDBFileDialogWindow.EXT_MDB_FILE);
			if (mdbFileDialogWindow.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				try {
					String selFileName = mdbFileDialogWindow.getSelectedFile().toString();
					dataBase = DatabaseBuilder.open(new File(selFileName));
					tableNames = dataBase.getTableNames();
					cmbTableNames.removeAllItems();
					for (String itemName : tableNames) {
						cmbTableNames.addItem(itemName);
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
				}
			}
		} else if (srcObject == cmbTableNames) {
			loadMDBTable();
		} else if (srcObject == itemFileExport) {
			if (dataBase == null) {
				JOptionPane.showMessageDialog(this, "Please select MDB file!");
				return;
			}
			
			MDBFileDialogWindow mdbFileDialogWindow = new MDBFileDialogWindow(MDBFileDialogWindow.EXT_XLS_FILE);
			if (mdbFileDialogWindow.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				String selFileName = mdbFileDialogWindow.getSelectedFile().toString();
				
				try {
					new ExportUtil.Builder(dataBase, (String) cmbTableNames.getSelectedItem())
							.setHeader(true)
							.setDelimiter("\t")
							.exportFile(new File(selFileName+"."+MDBFileDialogWindow.EXT_XLS_FILE));
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, e.getMessage());
				}
			}
		} else if (srcObject == itemFileExit) {
			System.exit(0);
		} else if (srcObject == itemEditSettings) {
			// TODO
		} else if (srcObject == itemHelpAbout) {
			JOptionPane.showMessageDialog(this, "Created by Oleksandr Iakushev @ 2016!");
		}
	}
}





