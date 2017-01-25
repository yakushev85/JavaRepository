package com.yakushev.ie.siles.datamodel.reaction.dialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class DialogService {
	private static final String BEGIN_FILE_TEMPLATE = "dialog_";
	// TAG ELEMENTS FROM DILAOG XML
	private static final String TAG_STARTS = "starts";
	private static final String TAG_START = "start";
	private static final String TAG_POINT_ID = "point_id";
	private static final String TAG_REGEXP = "regexp";
	private static final String TAG_POINTS = "points";
	private static final String TAG_POINT = "point";
	private static final String TAG_ID = "id";
	private static final String TAG_REACTION = "reaction";
	private static final String TAG_CLASS = "class";
	private static final String TAG_INPUT = "input";
	private static final String TAG_LINKS = "links";
	private static final String TAG_LINK = "link";
	
	private List<Dialog> dialogs;
	private Point currentPoint;
	private Dialog currentDialog;
	private boolean isStart;
	
	public DialogService() throws FileNotFoundException, XMLStreamException {
		isStart = false;
		loadDialogs();
	}

	public String changePoint(String userMsg) {
		if (isStart) {
			isStart = false;
			return currentPoint.getRandomPointReaction();
		}
		
		String nextPointID = "";
		for (Link link : currentPoint.getLinks()) {
			if (isMatchForRegexpList(userMsg, link.getRegexps())) {
				nextPointID = link.getRandomPointID();
				break;
			}
		}
		
		if (nextPointID.isEmpty()) {
			currentPoint = null;
			currentDialog = null;
		} else {
			currentPoint = findPointByID(nextPointID);
			
			if (currentPoint.getLinks().isEmpty()) {
				currentDialog = null;
			}
			
			return currentPoint.getRandomPointReaction();
		}
		
		return "";
	}
	
	public boolean checkDialogStart(String userMsg) {
		for (Dialog dialog : dialogs) {
			List<Start> startList = dialog.getStarts();
			for (Start start : startList) {
				if (isMatchForRegexpList(userMsg, start.getRegexps())) {
					currentDialog = dialog;
					currentPoint = findPointByID(start.getPointID());
					isStart = true;
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isDialogActive() {
		return currentPoint != null && currentDialog != null;
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}
	
	private Point findPointByID(String id) {
		for (Point point : currentDialog.getPoints()) {
			if (point.getId().equals(id)) {
				return point;
			}
		}
		return null;
	}
	
	private static boolean isMatchForRegexpList(String userMsg, List<String> regexpList) {
		for (String regexp : regexpList) {
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = pattern.matcher(userMsg);
			
			if (matcher.matches()) {
				return true;
			}
		}
		
		return false;
	}
	
	private void loadDialogs() throws FileNotFoundException, XMLStreamException {
		File currentFolder = new File(".");
		File[] listOfFiles = currentFolder.listFiles();
		dialogs = new ArrayList<Dialog> ();
		
		for (File file : listOfFiles) {
			if (file.isFile() && file.getName().toLowerCase().startsWith(BEGIN_FILE_TEMPLATE)) {
				dialogs.add(loadDialogFromFile(file.getName()));
			}
		}		
	}
	
	private static Dialog loadDialogFromFile(String fileName) throws FileNotFoundException, XMLStreamException {
		Dialog dialog = new Dialog();
		
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		xmlFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		
		XMLStreamReader xmlReader = xmlFactory.createXMLStreamReader(new FileInputStream(fileName));
		
		int evenType = xmlReader.getEventType();
		boolean priz = true;
		String subSection = "";
		String currentElemName = "";
		
		Start currentStart = new Start();;
		Point currentPoint = new Point();
		Link currentLink = new Link();
		
		try {
			while (priz) {
				switch (evenType) {
				case XMLStreamConstants.START_ELEMENT:
					currentElemName = xmlReader.getName().toString().toLowerCase();
					
					if (currentElemName.equals(TAG_STARTS) 
							|| currentElemName.equals(TAG_POINTS)
							|| currentElemName.equals(TAG_LINKS)) {
						subSection = currentElemName;
					} else if (currentElemName.equals(TAG_START)) {
						currentStart = new Start();
					} else if (currentElemName.equals(TAG_POINT)) {
						currentPoint = new Point();
					} else if (currentElemName.equals(TAG_LINK)) {
						currentLink = new Link();
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					currentElemName = xmlReader.getName().toString().toLowerCase();
					
					if (currentElemName.equals(TAG_STARTS) 
							|| currentElemName.equals(TAG_POINTS)
							|| currentElemName.equals(TAG_LINKS)) {
						subSection = "";
					} else if (currentElemName.equals(TAG_START)) {
						dialog.addStart(currentStart);
					} else if (currentElemName.equals(TAG_POINT)) {
						dialog.addPoint(currentPoint);
					} else if (currentElemName.equals(TAG_LINK)) {
						currentPoint.addLink(currentLink);
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (xmlReader.isWhiteSpace()) {
						break;
					}
					
					String text = xmlReader.getText();
					
					if (currentElemName.equals(TAG_POINT_ID) && subSection.equals(TAG_STARTS)) {
						currentStart.setPointID(text);
					} else if (currentElemName.equals(TAG_POINT_ID) && subSection.equals(TAG_LINKS)) {
						currentLink.addPointID(text);
					} else if (currentElemName.equals(TAG_REGEXP) && subSection.equals(TAG_STARTS)) {
						currentStart.addRegexp(text);
					} else if (currentElemName.equals(TAG_REGEXP) && subSection.equals(TAG_LINKS)) {
						currentLink.addRegexp(text);
					} else if (currentElemName.equals(TAG_ID)) {
						currentPoint.setId(text);
					} else if (currentElemName.equals(TAG_CLASS)) {
						currentPoint.setCustomClass(text);
					} else if (currentElemName.equals(TAG_INPUT)) {
						currentPoint.setCustomInput(text);
					} else if (currentElemName.equals(TAG_REACTION)) {
						currentPoint.addPointReaction(text);
					}
					break;
				}
				
				if (xmlReader.hasNext()) {
					evenType = xmlReader.next();
				} else {
					priz = false;
				}
			}
		} finally {
			xmlReader.close();
		}
		
		return dialog;
	}
}
