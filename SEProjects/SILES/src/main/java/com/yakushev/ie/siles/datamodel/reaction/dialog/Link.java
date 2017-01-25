package com.yakushev.ie.siles.datamodel.reaction.dialog;

import java.util.ArrayList;
import java.util.List;

public class Link {
	private List<String> regexps;
	private List<String> pointIDs;
	
	public Link() {
		regexps = new ArrayList<String> ();
		pointIDs = new ArrayList<String> (); 
	}
	
	public List<String> getRegexps() {
		return regexps;
	}
	public void addRegexp(String ritem) {
		this.regexps.add(ritem);
	}
	
	public List<String> getPointIDs() {
		return pointIDs;
	}
	public void addPointID(String pointID) {
		this.pointIDs.add(pointID);
	}
	public String getRandomPointID() {
		return pointIDs.get((int) (Math.random()*pointIDs.size()));
	}

	@Override
	public String toString() {
		return "Link [regexps=" + regexps + ", pointID=" + pointIDs + "]";
	}
}
