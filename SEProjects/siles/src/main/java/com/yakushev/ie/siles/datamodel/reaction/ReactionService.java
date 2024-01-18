package com.yakushev.ie.siles.datamodel.reaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class ReactionService {
	public static final String TXT_FILENAMEUSERREACTIONS = "ureactions.xml";
	public static final String TXT_FILENAMETEACHREACTIONS = "treactions.xml";
	
	private static final String TXT_ELEMREACTION = "reaction";
	private static final String TXT_ELEMPATTERN = "pattern";
	private static final String TXT_ELEMOUTREACTION = "outreaction";
	private static final String TXT_ELEMCUSTOMCLASS = "customclass";
		
	private static List<Reaction> loadReactionsFromXML(String filename) throws IOException, XMLStreamException  {
		List<Reaction> reactionList = new ArrayList<>();
		
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		xmlFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		
		XMLStreamReader xmlReader = xmlFactory.createXMLStreamReader(new FileInputStream(filename));
		
		int evenType = xmlReader.getEventType();
		boolean priz = true;
		Reaction currentReaction = new Reaction();
		String currentElemName = "";
		
		try {
			while (priz) {
				switch (evenType) {
					case XMLStreamConstants.START_ELEMENT -> {
						currentElemName = xmlReader.getName().toString();
						if (currentElemName.equals(TXT_ELEMREACTION)) {
							currentReaction = new Reaction();
						}
					}
					case XMLStreamConstants.END_ELEMENT -> {
						currentElemName = xmlReader.getName().toString();
						if (currentElemName.equals(TXT_ELEMREACTION)) {
							reactionList.add(currentReaction);
							currentElemName = "";
						}
					}
					case XMLStreamConstants.CHARACTERS -> {
						if (xmlReader.isWhiteSpace()) {
							break;
						}
						switch (currentElemName) {
							case TXT_ELEMPATTERN -> currentReaction.addPatternTag(xmlReader.getText());
							case TXT_ELEMOUTREACTION -> currentReaction.addReaction(xmlReader.getText());
							case TXT_ELEMCUSTOMCLASS -> currentReaction.setCustomClass(xmlReader.getText());
						}
					}
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
		
		return reactionList;		
	}
	
	public static List<Reaction> loadUserReactions() throws IOException, XMLStreamException {
		return loadReactionsFromXML(TXT_FILENAMEUSERREACTIONS);
	}
	
	public static List<Reaction> loadTeachReaction() throws IOException, XMLStreamException {
		return loadReactionsFromXML(TXT_FILENAMETEACHREACTIONS);
	}
}
