package org.iakushev.passwordgenerator.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.iakushev.passwordgenerator.controller.MainController;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -8824568895243693196L;
	
	private static final int DEFAULT_LENGTH = 8;
	private static final int DEFAULT_COUNTS = 10;
	
	private static final int MAX_LENGTH = 1000;
	private static final int MAX_COUNTS = 1000;
	
	private static final String TXT_TITLE = "Password Generator";
	private static final String TXT_LENGTH = "Length:";
	private static final String TXT_COUNTS = "Counts:";
	private static final String TXT_CBALPHAS = "Use alphas";
	private static final String TXT_CBDIGITS = "Use digits";
	private static final String TXT_BTNGENERATE = "Generate";
	private static final String TXT_MSG_CHOOSE_ONE = "Please choose alphas or digits!";
	private static final String TXT_MSG_OVERMAXLENGTH = "Too much value for length!";
	private static final String TXT_MSG_OVERMAXCOUNTS = "Too much value for counts!";
	
	private JTextField txtLength, txtCounts;
	private JCheckBox cbAlphas, cbDigits;
	private JTextArea txaResults;
	private JButton btnGenerate;

	public MainWindow() {
		initializeWindow();
		initializeComponents();
		this.setVisible(true);
	}
	
	private void initializeWindow() {
		this.setSize(600, 430);
		this.setLocation(50, 50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(TXT_TITLE);
		this.setResizable(false);
	}
	
	private void initializeComponents() {
		JLabel lblLength = new JLabel(TXT_LENGTH);
		JLabel lblCounts = new JLabel(TXT_COUNTS);
		
		txtLength = new JTextField(20);
		txtLength.setText(String.valueOf(DEFAULT_LENGTH));
		txtCounts = new JTextField(20);
		txtCounts.setText(String.valueOf(DEFAULT_COUNTS));
		
		cbAlphas = new JCheckBox(TXT_CBALPHAS);
		cbAlphas.setSelected(true);
		cbDigits = new JCheckBox(TXT_CBDIGITS);
		cbDigits.setSelected(true);
		
		txaResults = new JTextArea(16,50);
		JScrollPane scpResults = new JScrollPane(txaResults);
		
		btnGenerate = new JButton(TXT_BTNGENERATE);
		btnGenerate.addActionListener(this);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5,5,5,5);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(lblLength, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(txtLength, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(lblCounts, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(txtCounts, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(cbAlphas, gbc);
		
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(cbDigits, gbc);
		
		gbl.setConstraints(scpResults, gbc);
		
		gbc.anchor = GridBagConstraints.EAST;
		gbl.setConstraints(btnGenerate, gbc);
		
		this.setLayout(gbl);
		this.add(lblLength);
		this.add(txtLength);
		this.add(lblCounts);
		this.add(txtCounts);
		this.add(cbAlphas);
		this.add(cbDigits);
		this.add(scpResults);
		this.add(btnGenerate);
		this.getRootPane().setDefaultButton(btnGenerate);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnGenerate) {
			try {
				int length = Integer.parseInt(txtLength.getText());
				int counts = Integer.parseInt(txtCounts.getText());
				boolean hasAlphas = cbAlphas.isSelected();
				boolean hasDigits = cbDigits.isSelected();
				
				if (!hasAlphas && !hasDigits) {
					JOptionPane.showMessageDialog(this, TXT_MSG_CHOOSE_ONE);
					return;
				}
				
				if (length > MAX_LENGTH) {
					JOptionPane.showMessageDialog(this, TXT_MSG_OVERMAXLENGTH);
					txtLength.setText(String.valueOf(DEFAULT_LENGTH));
					return;
				}
				
				if (counts > MAX_COUNTS) {
					JOptionPane.showMessageDialog(this, TXT_MSG_OVERMAXCOUNTS);
					txtCounts.setText(String.valueOf(DEFAULT_COUNTS));
					return;
				}
				
				List<String> passwords = MainController.generatePasswords(length, counts, hasAlphas, hasDigits);
				
				txaResults.setText("");
				for (String password : passwords) {
					txaResults.append(password + "\n");
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
			
		}
	}
}
