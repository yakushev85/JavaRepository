package org.oiakushev.neuralnetworks.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;
import org.oiakushev.neuralnetworks.entity.Perceptron;
import org.oiakushev.neuralnetworks.entity.TeachDataEntity;
import org.oiakushev.neuralnetworks.learning.BasicPerceptronLearningProcess;
import org.oiakushev.neuralnetworks.logger.PerceptronModelingLogger;

public class MainLauncher {
	private static final String CONFIGURATION_FILE_NAME = "configuration.xml";
	private static final String INCOUNT_TAG = "incount";
	private static final String NEURONCOUNT_TAG = "neuroncount";
	private static final String INVECTOR_TAG = "invector";
	private static final String OUTPUT_TAG = "output";
	private static final String TESTVECTOR_TAG = "testvector";
	private static final String ANSWER_TAG = "answer";
	private static Logger logger;
	
	public static void main(String args[]) throws XMLStreamException, IOException {
		PerceptronModelingLogger.cleanLogFile();
		logger = PerceptronModelingLogger.getConfiguredLogger(MainLauncher.class);
		
		logger.warn("MainLauncher.main()");
		System.out.println("Initialization...");
		XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
		xmlFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);
		
		XMLStreamReader xmlReader = xmlFactory.createXMLStreamReader(new FileInputStream(CONFIGURATION_FILE_NAME));
		
		int evenType = xmlReader.getEventType();
		boolean priz = true;
		String currentElemName = "";
		Perceptron perceptron = null;

		int incount = 0;
		int neuroncount = 0;
		List<TeachDataEntity> teachData = new ArrayList<TeachDataEntity> ();
		List<TeachDataEntity> examData = new ArrayList<TeachDataEntity> ();
		int[] inVector = null;
		int[] output = null;
		
		System.out.println("Start reading data from "+CONFIGURATION_FILE_NAME+" ...");
		try {
			while (priz) {
				switch (evenType) {
				case XMLStreamConstants.START_ELEMENT:
					currentElemName = xmlReader.getName().toString();
					System.out.println("Start XML element. "+currentElemName);
					break;
				case XMLStreamConstants.END_ELEMENT:
					if (perceptron == null && incount > 0 && neuroncount > 0) {
						perceptron = new Perceptron(incount, neuroncount);
					}
					
					if (inVector != null && output != null) {
						if (currentElemName.equals(INVECTOR_TAG) || currentElemName.equals(OUTPUT_TAG) ) {
							teachData.add(new TeachDataEntity(inVector, output));
						} else if (currentElemName.equals(TESTVECTOR_TAG) || currentElemName.equals(ANSWER_TAG)) { 
							examData.add(new TeachDataEntity(inVector, output));
						}
						inVector = null;
						output = null;
					}
					System.out.println("End XML element. "+currentElemName);
					break;
				case XMLStreamConstants.CHARACTERS:
					if (xmlReader.isWhiteSpace()) {
						break;
					}
					
					if (currentElemName.equals(INCOUNT_TAG)) {
						incount = Integer.parseInt(xmlReader.getText());
					} else if (currentElemName.equals(NEURONCOUNT_TAG)) {
						neuroncount = Integer.parseInt(xmlReader.getText());
					} else if (currentElemName.equals(INVECTOR_TAG) || currentElemName.equals(TESTVECTOR_TAG)) {
						String inDataStr = xmlReader.getText().replaceAll("\\s", "");
						inVector = new int[incount];
						for (int i=0;i<incount;i++) {
							inVector[i] = (inDataStr.charAt(i) == '0')?0:1;
						}
					} else if (currentElemName.equals(OUTPUT_TAG) || currentElemName.equals(ANSWER_TAG)) {
						String inDataStr = xmlReader.getText().replaceAll("\\s", "");
						output = new int[neuroncount];
						for (int i=0;i<neuroncount;i++) {
							output[i] = (inDataStr.charAt(i) == '0')?0:1;
						}
					}
					System.out.println("Charset XML. "+currentElemName);
					break;
				}
				
				if (xmlReader.hasNext()) {
					evenType = xmlReader.next();
				} else {
					priz = false;
				}
			}
			
			perceptron.setDataForTeaching(teachData.toArray(new TeachDataEntity[teachData.size()]));
			
			logger.warn(perceptron);
			System.out.println("Learning...");
			BasicPerceptronLearningProcess learningProcess = new BasicPerceptronLearningProcess(perceptron);
			learningProcess.start();
			
			System.out.println("Examing..");
			for (TeachDataEntity examItem : teachData) {
				String outputStr = vectorToString(perceptron.execute(examItem.getVector()));
				String answerStr = vectorToString(examItem.getOutput());
				String mark = (outputStr.equals(answerStr))? "PASSED": "FAILED";
				
				logger.warn("Network("+vectorToString(examItem.getVector())+")="+outputStr+
						" Expected:"+answerStr+". Test is "+mark+".");
			}
			
			logger.warn("Done.");
			System.out.println("Done.");
		} finally {
			xmlReader.close();
		}
	}
	
	public static String vectorToString(int[] x) {
		StringBuilder result = new StringBuilder();
		for (int i=0;i<x.length;i++) {
			result.append(x[i]);
		}
		return result.toString();
	}
}
