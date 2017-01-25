package com.yakushev.ie.siles.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;

import com.yakushev.ie.siles.controller.DataController;
import com.yakushev.ie.siles.log.LogSiles;

public class EditReactionsWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -2446089707584360884L;
	
	private static final String TXT_TITLE = "Edit reactions dialog";
	private static final String TXT_BTNOK = "OK";
	private static final String TXT_BTNCANCEL = "Cancel";
	private static final String TXT_BTNADD = "Add";
	private static final String TXT_BTNDEL = "Delete";
	private static final String TXT_MSGSURE = "Are you sure?";
	private static final String TXT_DELETING = "Deleting:";
	private static final String TXT_TITLE_DELDIALOG = "Deleting reaction"; 
	private static final String TXT_SELECT_ROW = "Please select row.";
	
	private DataController dataController;
	private Logger logger;
	private JTable tblReactions;
	private JButton btnOk, btnCancel;
	private JButton btnAdd, btnDel;
	
	public EditReactionsWindow(DataController dataController) {
		this.dataController = dataController;
		initializeLogging();
		initializeWindow();
		initializeComponents();
	}

	public void initializeLogging() {
		logger = LogSiles.getConfiguredLogger(EditReactionsWindow.class);
	}
	
	public void initializeWindow() {
		this.setSize(600, 430);
		this.setLocation(90, 90);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle(TXT_TITLE);
		this.setResizable(false);
	}
	
	public void initializeComponents() {
		tblReactions = new JTable(dataController.getTableModel());
		JScrollPane scpReactions = new JScrollPane(tblReactions);
		
		btnOk = new JButton(TXT_BTNOK);
		btnOk.addActionListener(this);
		
		btnCancel = new JButton(TXT_BTNCANCEL);
		btnCancel.addActionListener(this);
		
		btnAdd = new JButton(TXT_BTNADD);
		btnAdd.addActionListener(this);
		
		btnDel = new JButton(TXT_BTNDEL);
		btnDel.addActionListener(this);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.add(btnOk);
		pnlButtons.add(btnCancel);
		
		JPanel pnlAction = new JPanel();
		pnlAction.add(btnAdd);
		pnlAction.add(btnDel);
		
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout gbl = new GridBagLayout();
		
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbl.setConstraints(scpReactions, gbc);
		
		gbc.weightx = 0.2;
		gbc.weighty = 0.033;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.NONE;
		gbl.setConstraints(pnlAction, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(pnlButtons, gbc);
		
		this.setLayout(gbl);
		this.add(scpReactions);
		this.add(pnlAction);
		this.add(pnlButtons);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnOk) {
			try {
				dataController.saveReactions();
				logger.info("Reactions has been changed.");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			
			this.setVisible(false);
		} else if (ae.getSource() == btnCancel) {
			try {
				dataController.reloadReactions();
			} catch (IOException e) {
				logger.error(e.getMessage());
			} catch (XMLStreamException e) {
				logger.error(e.getMessage());
			}
			
			this.setVisible(false);
		} else if (ae.getSource() == btnAdd) {
			dataController.addDefaultTeachReaction();
			tblReactions.setModel(dataController.getTableModel());
		} else if (ae.getSource() == btnDel) {
			int selectedRow = tblReactions.getSelectedRow();
			
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, TXT_SELECT_ROW);
				return;
			}
			
			String nameToDelete = (String) tblReactions.getValueAt(selectedRow, 0);
			int optionDialog = JOptionPane.showConfirmDialog(this,
					TXT_DELETING + nameToDelete + "\n" +
					TXT_MSGSURE, TXT_TITLE_DELDIALOG, 
					JOptionPane.YES_NO_OPTION);
			if (optionDialog == JOptionPane.YES_OPTION) {
				dataController.deleteTeachReaction(selectedRow);
				tblReactions.setModel(dataController.getTableModel());
			}
		}
	}
}
