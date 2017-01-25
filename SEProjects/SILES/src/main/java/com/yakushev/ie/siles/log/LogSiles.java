package com.yakushev.ie.siles.log;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class LogSiles {
	public static final String TXT_NAMEFILELOGGING = "main.log";
	public static final String TXT_PATTERNLAYOUT = "[%d] %p %c: %m%n";
	
	public static void cleanLogFile() {
		File logFile = new File(TXT_NAMEFILELOGGING);
		if (logFile.exists()) {
			logFile.delete();
		}
	}
	
	public static Logger getConfiguredLogger(@SuppressWarnings("rawtypes") Class clazz) {
		Logger logger = Logger.getLogger(clazz);
		PatternLayout patternLayout = new PatternLayout();
		patternLayout.setConversionPattern(TXT_PATTERNLAYOUT);
		try {
			logger.addAppender(new FileAppender(patternLayout, TXT_NAMEFILELOGGING));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.setLevel(Level.DEBUG);
		return logger;
	}
}
