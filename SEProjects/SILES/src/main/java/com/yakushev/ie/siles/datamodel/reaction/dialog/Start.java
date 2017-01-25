package com.yakushev.ie.siles.datamodel.reaction.dialog;

import java.util.ArrayList;
import java.util.List;

public class Start {
	private String pointID;
	private List<String> regexps;
	
	public Start() {
		this.pointID = "";
		regexps = new ArrayList<String> ();
	}
	
	public String getPointID() {
		return pointID;
	}
	public void setPointID(String pointID) {
		this.pointID = pointID;
	}
	
	public List<String> getRegexps() {
		return regexps;
	}
	public void addRegexp(String ritem) {
		this.regexps.add(ritem);
	}

	@Override
	public String toString() {
		return "Start [pointID=" + pointID + ", regexps=" + regexps + "]";
	}
}
