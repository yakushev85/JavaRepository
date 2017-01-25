package com.yakushev.ie.siles.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.yakushev.ie.siles.controller.DataController;
import com.yakushev.ie.siles.log.LogSiles;
import com.yakushev.ie.siles.observ.Observer;

public class MainWindow extends JFrame implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	private static final String TXT_MSGINFOSTARTED = "UI initialized.";
	
	private final String TXT_TITLE = "SILES";
	private final String TXT_BTNSEND = "Send";
	private final String TXT_YOURNAMEINHISTORY = "You";
	private final String TXT_SILESNAMEINHISTORY = "Siles";
	
	private final String TXT_MENUFILE = "File";
	private final String TXT_MENUOPTIONS = "Options";
	private final String TXT_MENUHELP = "Help";
	private final String TXT_MENUFILE_ITEMOPEN = "open";
	private final String TXT_MENUFILE_ITEMSAVE = "save";
	private final String TXT_MENUFILE_ITEMEXIT = "exit";
	private final String TXT_MENUOPTIONS_ITEMEDITREACTIONS = "edit reactions";
	private final String TXT_MENUHELP_ITEMABOUT = "about";
	private final String TXT_OPENDIALOGTITLE = "Open history file";
	private final String TXT_SAVEDIALOGTITLE = "Save history file";
	private final String TXT_CREATEDBY = "Created by Oleksandr Iakushev.";
	
	private DataController dataController;
	private Logger logger;
	private JTextArea txaHistory;
	private HistoryTextField txtMessage;
	private JButton btnSend;
	private JMenuBar menuBar;
	private JMenu menuFile, menuOptions, menuHelp;
	private JMenuItem itemFileOpen, itemFileSave, itemFileExit;
	private JMenuItem itemOptionsEditReactions;
	private JMenuItem itemHelpAbout;

	public MainWindow(DataController dataController) {
		this.dataController = dataController;
		initializeLogging();
		initializeWindow();
		initializeComponents();
		this.setVisible(true);
		logger.info(TXT_MSGINFOSTARTED);
	}
	
	private void initializeLogging() {
		logger = LogSiles.getConfiguredLogger(MainWindow.class);
	}
	
	private void initializeWindow() {
		this.setSize(800, 430);
		this.setLocation(50, 50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(TXT_TITLE);
		this.setResizable(false);
	}
	
	private void initializeComponents() {
		menuBar = new JMenuBar();
		
		menuFile = new JMenu(TXT_MENUFILE);
		menuOptions = new JMenu(TXT_MENUOPTIONS);
		menuHelp = new JMenu(TXT_MENUHELP);
		
		itemFileOpen = new JMenuItem(TXT_MENUFILE_ITEMOPEN);
		itemFileSave = new JMenuItem(TXT_MENUFILE_ITEMSAVE);
		itemFileExit = new JMenuItem(TXT_MENUFILE_ITEMEXIT);
		
		itemOptionsEditReactions = new JMenuItem(TXT_MENUOPTIONS_ITEMEDITREACTIONS);
		
		itemHelpAbout = new JMenuItem(TXT_MENUHELP_ITEMABOUT);
		
		itemFileOpen.addActionListener(this);
		itemFileSave.addActionListener(this);
		itemFileExit.addActionListener(this);
		itemOptionsEditReactions.addActionListener(this);
		itemHelpAbout.addActionListener(this);
		
		menuBar.add(menuFile);
		menuBar.add(menuOptions);
		menuBar.add(menuHelp);
		
		menuFile.add(itemFileOpen);
		menuFile.add(itemFileSave);
		menuFile.add(itemFileExit);
		
		menuOptions.add(itemOptionsEditReactions);
		
		menuHelp.add(itemHelpAbout);
		
		txaHistory = new JTextArea(20,70);
		txaHistory.setEditable(false);
		txaHistory.setLineWrap(true);
		txtMessage = new HistoryTextField(62);
		btnSend = new JButton(TXT_BTNSEND);
		
		btnSend.addActionListener(this);
		
		JScrollPane scpHistory = new JScrollPane(txaHistory);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5,5,5,5);
		gbc.weighty = 1.0;
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(scpHistory, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(txtMessage, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(btnSend, gbc);
		
		this.setJMenuBar(menuBar);
		this.setLayout(gbl);
		this.add(scpHistory);
		this.add(txtMessage);
		this.add(btnSend);
		this.getRootPane().setDefaultButton(btnSend);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnSend) {
			txaHistory.append("[" + TXT_YOURNAMEINHISTORY + "] " + txtMessage.getText() + "\n");
			txtMessage.addHistoryData();
			dataController.getAnswer(txtMessage.getText());
			txtMessage.setText("");
			scrollToEndHistory();
		} else if (ae.getSource() == itemFileOpen) {
			HistoryFileDialogWindow dlgF = new HistoryFileDialogWindow(TXT_OPENDIALOGTITLE);
			if (dlgF.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				try {
					String selectedFileName = dlgF.getSelectedFile().toString();
					String history = dataController.openHistoryFromFile(selectedFileName);
					txaHistory.setText(history);
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage());
					JOptionPane.showMessageDialog(this, e.getMessage());
				}
				
			}
		} else if (ae.getSource() == itemFileSave) {
			HistoryFileDialogWindow dlgF = new HistoryFileDialogWindow(TXT_SAVEDIALOGTITLE);
			if (dlgF.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				try {
					String selectedFileName = dlgF.getSelectedFile().toString();
					String history = txaHistory.getText();
					dataController.saveHistoryToFile(selectedFileName, history);
				} catch (IOException e) {
					logger.error(e.getMessage());
					JOptionPane.showMessageDialog(this, e.getMessage());
				}
			}
		} else if (ae.getSource() == itemFileExit) {
			System.exit(0);
		} else if (ae.getSource() == itemOptionsEditReactions) {
			new EditReactionsWindow(dataController).setVisible(true);
		} else if (ae.getSource() == itemHelpAbout) {
			JOptionPane.showMessageDialog(this, TXT_CREATEDBY);
		}
	}

	public void updateObserver(String msg) {
		txaHistory.append("[" + TXT_SILESNAMEINHISTORY + "] " + msg + "\n");
		scrollToEndHistory();
	}
	
	private void scrollToEndHistory() {
		String strHistory = txaHistory.getText();
		
		if (strHistory != null && !strHistory.isEmpty()) {
			txaHistory.setCaretPosition(strHistory.length()-1);
		}
	}
}
