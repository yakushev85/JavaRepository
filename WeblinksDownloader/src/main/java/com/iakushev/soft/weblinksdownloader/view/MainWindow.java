package com.iakushev.soft.weblinksdownloader.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import com.iakushev.soft.weblinksdownloader.controller.DownloaderController;
import com.iakushev.soft.weblinksdownloader.observ.Observer;

public class MainWindow extends JFrame implements ActionListener, Observer {
	private static final long serialVersionUID = 7092403853426883272L;
	private static final String TXT_TITLEWINDOW = "Web-Links Downloader";
	private static final String TXT_BTNDOWNLOAD = "Download";
	private static final String TXT_LBLURL = "URL for download:";
	private static final String TXT_LBLLEVEL = "Level for search:";
	private static final String TXT_LBLREGEX = "RegEx for downloading files:";
	private static final String TXT_DEFALTURL = "https://www.google.com/";
	private static final int INT_DEFAULTLEVEL = 1;
	
	private DownloaderController downloaderController;
	
	private JLabel lblURL;
	private JTextField txfURL;
	private JButton btnDownload;
	
	private JLabel lblLevel;
	private JTextField txfLevel;
	private JLabel lblRegEx;
	private JTextField txfRegEx;
	
	private JTextArea txaResults;
	
	
	public MainWindow(DownloaderController downloaderController) {
		this.downloaderController = downloaderController;
		initWindow();
		initComponents();
		setVisible(true);
	}

	private void initWindow() {
		setTitle(TXT_TITLEWINDOW);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocation(150, 150);
		setResizable(false);
	}
	
	private void initComponents() {
		lblURL = new JLabel(TXT_LBLURL);
		txfURL = new JTextField(35);
		txfURL.setText(TXT_DEFALTURL);
		btnDownload = new JButton(TXT_BTNDOWNLOAD);
		btnDownload.addActionListener(this);
		JPanel pnlURL = new JPanel();
		pnlURL.add(lblURL);
		pnlURL.add(txfURL);
		pnlURL.add(btnDownload);
		this.getRootPane().setDefaultButton(btnDownload);
		
		lblLevel = new JLabel(TXT_LBLLEVEL);
		txfLevel = new JTextField(5);
		txfLevel.setText(Integer.toString(INT_DEFAULTLEVEL));
		lblRegEx = new JLabel(TXT_LBLREGEX);
		txfRegEx = new JTextField(20);
		JPanel pnlSettings = new JPanel();
		pnlSettings.add(lblLevel);
		pnlSettings.add(txfLevel);
		pnlSettings.add(lblRegEx);
		pnlSettings.add(txfRegEx);
		
		txaResults = new JTextArea(20, 45);
		txaResults.setEditable(false);
		DefaultCaret caret = (DefaultCaret) txaResults.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane scpResults = new JScrollPane(txaResults);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5,5,5,5);
		gbc.weighty = 1.0;
		
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(pnlURL, gbc);
		gbl.setConstraints(pnlSettings, gbc);
		gbl.setConstraints(scpResults, gbc);		
		
		this.setLayout(gbl);
		this.add(pnlURL);
		this.add(pnlSettings);
		this.add(scpResults);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnDownload) {
			btnDownload.setEnabled(false);
			txaResults.setText("");
			downloaderController.setURL(txfURL.getText());
			try {
				downloaderController.setSettingsForDownload(txfRegEx.getText(), Integer.parseInt(txfLevel.getText()));
			} catch (NumberFormatException e) {
				downloaderController.setSettingsForDownload(txfRegEx.getText(), INT_DEFAULTLEVEL);
			}
			
			downloaderController.startThread();
		}
	}

	public void updateObserver(Object obj) {
		if (obj instanceof File) {
			File resFile = (File) obj;
			txaResults.append(resFile.toString()+" ... downloaded\n");
		} else if (obj instanceof String) {
			String msg = (String) obj;
			txaResults.append(msg+"\n");
		} else if (obj == null) {
			btnDownload.setEnabled(true);
		}
	}

}
