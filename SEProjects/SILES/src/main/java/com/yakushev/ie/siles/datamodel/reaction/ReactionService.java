package com.yakushev.ie.siles.datamodel.reaction;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.yakushev.ie.siles.log.LogSiles;

public class ReactionService {
	private static final String TXT_FILENAMEUSERREACTIONS = "ureactions.xml";
	private static final String TXT_FILENAMETEACHREACTIONS = "treactions.dat";
	
	private static final String TXT_ELEMREACTION = "reaction";
	private static final String TXT_ELEMPATTERN = "pattern";
	private static final String TXT_ELEMOUTREACTION = "outreaction";
	private static final String TXT_ELEMCUSTOMCLASS = "customclass";
		
	private static List<Reaction> loadReactionsFromXML(String filename) throws IOException, XMLStreamException  {
		List<Reaction> reactionList = new ArrayList<Reaction>();
		
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
				case XMLStreamConstants.START_ELEMENT:
					currentElemName = xmlReader.getName().toString();
					
					if (currentElemName.equals(TXT_ELEMREACTION)) {
						currentReaction = new Reaction();
					}
					
					break;
				case XMLStreamConstants.END_ELEMENT:
					currentElemName = xmlReader.getName().toString();
					if (currentElemName.equals(TXT_ELEMREACTION)) {
						reactionList.add(currentReaction);
						currentElemName = "";
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					if (xmlReader.isWhiteSpace()) {
						break;
					}
					
					if (currentElemName.equals(TXT_ELEMPATTERN)) {
						currentReaction.addPatternTag(xmlReader.getText());
					} else if (currentElemName.equals(TXT_ELEMOUTREACTION)) {
						currentReaction.addReaction(xmlReader.getText());
					} else if (currentElemName.equals(TXT_ELEMCUSTOMCLASS)) {
						currentReaction.setCustomClass(xmlReader.getText());
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
		
		return reactionList;		
	}
	
	public static List<Reaction> loadUserReactions() throws IOException, XMLStreamException {
		return loadReactionsFromXML(TXT_FILENAMEUSERREACTIONS);
	}
	
	public static List<Reaction> loadTeachReaction() throws IOException {
		List<Reaction> listRes = new ArrayList<Reaction> ();
		File fileTeachReactions = new File(TXT_FILENAMETEACHREACTIONS);
		
		if (!fileTeachReactions.exists()) {
			return listRes;
		}
		
		ObjectInputStream reactionInputStream = new ObjectInputStream(new FileInputStream(fileTeachReactions));
		
		try {
			boolean priz = true;
			while (priz) {
				Object oItem = reactionInputStream.readObject();
				
				if (oItem != null && oItem instanceof Reaction) {
					Reaction rItem = (Reaction) oItem;
					listRes.add(rItem);
				} else {
					priz = false;
				}
			}
		} catch (ClassNotFoundException e) {
			throw new IOException(e.getMessage());
		} catch (EOFException e) { 
			LogSiles.getConfiguredLogger(ReactionService.class).warn(e.getMessage());
		} finally {
			reactionInputStream.close();
		}
		
		return listRes;
	}
	
	public static void updateTeachReactions(List<Reaction> rList) throws IOException {
		File fileTeachReaction = new File(TXT_FILENAMETEACHREACTIONS);
		ObjectOutputStream reactionOutputStream = new ObjectOutputStream(new FileOutputStream(fileTeachReaction));
		
		try {
			for (Reaction ri : rList) {
				reactionOutputStream.writeObject(ri);
				reactionOutputStream.flush();
			}
		} finally {
			reactionOutputStream.close();
		}
	}
}
