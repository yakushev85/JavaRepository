package com.yakushev.ie.siles.datamodel.reaction.dialog;

import java.util.ArrayList;
import java.util.List;

public class Point {
	private String id;
	private List<String> pointReactions;
	private String customClass;
	private String customInput;
	private List<Link> links;
	
	public Point() {
		this.id = "";
		pointReactions = new ArrayList<String> ();
		links = new ArrayList<Link> ();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getPointReactions() {
		return pointReactions;
	}
	public void addPointReaction(String pointReaction) {
		pointReactions.add(pointReaction);
	}
	public String getRandomPointReaction() {
		return pointReactions.get((int) (Math.random()*pointReactions.size()));
	}
	
	public String getCustomClass() {
		return customClass;
	}
	public void setCustomClass(String customClass) {
		this.customClass = customClass;
	}
	
	public String getCustomInput() {
		return customInput;
	}
	public void setCustomInput(String customInput) {
		this.customInput = customInput;
	}
	
	public List<Link> getLinks() {
		return links;
	}
	public void addLink(Link link) {
		links.add(link);
	}

	@Override
	public String toString() {
		return "Point [id=" + id + ", pointReactions=" + pointReactions
				+ ", customClass=" + customClass + ", customInput="
				+ customInput + ", links=" + links + "]";
	}
}
