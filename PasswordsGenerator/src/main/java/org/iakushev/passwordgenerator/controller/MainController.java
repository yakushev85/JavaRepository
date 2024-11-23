package org.iakushev.passwordgenerator.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.iakushev.passwordgenerator.view.MainWindow;

public class MainController {
	static private final String ALPHAS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	static private final String DIGITS = "1234567890";
	
	private static String generateSinglePassword(int length, boolean hasAlphas, boolean hasDigits) {
		StringBuilder result = new StringBuilder();
		
		String alphabetic = (hasAlphas)?ALPHAS:"";
		alphabetic += (hasDigits)?DIGITS:"";
		
		for (int i=0; i<length; i++) {
			int countChar = (int) (Math.random() * alphabetic.length());
			
			result.append(alphabetic.charAt(countChar));
		}
		
		return result.toString();
	}
	
	public static List<String> generatePasswords(int length, int counts, boolean hasAlphas, boolean hasDigits) {
		List<String> results = new ArrayList<String> ();
		
		for (int i=0; i<counts; i++) {
			results.add(generateSinglePassword(length,hasAlphas,hasDigits));
		}
		
		return results;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				new MainWindow();
			}
		});
	}
}
