package com.iakushev.soft.weblinksdownloader.datamodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DownloaderModel {
	private String url;
	private String patternRegEx;
	private int level;

	public DownloaderModel(String url) {
		this.url = url;
		this.level = 1;
		this.patternRegEx = "";
	}
	
	public String getPatternRegEx() {
		return patternRegEx;
	}

	public void setPatternRegEx(String patternRegEx) {
		this.patternRegEx = patternRegEx;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	private List<String> getWebLinksByLevel(int currentLevel) {
		List<String> resultURL = new ArrayList<String> ();
		if (currentLevel <= level) {
			try {
				Document doc = Jsoup.connect(url).get();
				Elements elementsWebLink = doc.getElementsByTag("a");
				
				for (Element elementWebLink : elementsWebLink) {
					resultURL.add(elementWebLink.absUrl("href"));
					resultURL.addAll(getWebLinksByLevel(currentLevel+1));
				}
			} catch (IOException e) {
				return resultURL;
			}
		}
		return resultURL;
	}
	
	public List<String> getWebLinks() {
		return getWebLinksByLevel(1);
	}
	
	public File getFileFromWebLink(String webLink) throws MalformedURLException, IOException {
		URL website = new URL(webLink);
		ReadableByteChannel readChannel = Channels.newChannel(website.openStream());
		
		File fileRes = new File(getPathToSave()+getFileNameForWebLink(webLink));
		FileOutputStream outputStream = new FileOutputStream(fileRes);
		outputStream.getChannel().transferFrom(readChannel, 0, Long.MAX_VALUE);
		outputStream.close();
		
		return fileRes;
	}
	
	public boolean isWebLinkForDownload(String webLink) {
		return webLink.lastIndexOf('/') < webLink.length()-1 && webLink.lastIndexOf('/') >= 0;
	}
	
	public boolean isWebLinkMatchPattern(String webLink) {
		if (patternRegEx.isEmpty()) {
			return true;
		}
		
		Pattern pattern = Pattern.compile(patternRegEx);
		Matcher matcher = pattern.matcher(webLink);
		return matcher.matches();
	}
	
	public String getFileNameForWebLink(String webLink) {
		String resFileName = webLink.substring(webLink.lastIndexOf('/'));
		return resFileName.replaceAll("[^a-zA-Z0-9_\\.]", "_");
	}
	
	public String getPathToSave() {
		String pathToSave = url.replaceAll("[^a-zA-Z0-9_]", "_")+"/";
		File pathFile = new File(pathToSave);
		
		if (!pathFile.exists()) {
			pathFile.mkdir();
		}
		
		return pathToSave;
	}
}
