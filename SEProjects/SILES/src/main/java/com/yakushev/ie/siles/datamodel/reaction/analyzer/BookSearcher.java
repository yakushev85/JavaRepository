package com.yakushev.ie.siles.datamodel.reaction.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookSearcher {
	private static int RESULT_LIMITATION = 20;
	
	private String bookFileName;
	
	public BookSearcher(String bookFileName) {
		this.bookFileName = bookFileName;
	}

	private List<String> searchByListOfWords(List<String> searchWords, boolean isEqual) throws IOException {
		List<String> resList = new ArrayList<String> ();
		
		Scanner fileScanner = new Scanner(new File(bookFileName));
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine().toLowerCase();
			
			int equalsCount = 0;
			for (String itemWord : searchWords) {
				if (line.indexOf(itemWord) >= 0) {
					equalsCount++;
				}
			}
			
			if ((isEqual && equalsCount == searchWords.size()) || (!isEqual && equalsCount > 0)) {
				resList.add(line);
			}
			
			if (resList.size() >= RESULT_LIMITATION) {
				break;
			}
		}
		fileScanner.close();
		
		return resList;
	}

	public List<String> searchEqualsByListOfWords(List<String> searchWords) throws IOException {
		return searchByListOfWords(searchWords, true);
	}
	
	public List<String> searchContainsByListOfWords(List<String> searchWords) throws IOException {
		return searchByListOfWords(searchWords, false);
	}	
}
