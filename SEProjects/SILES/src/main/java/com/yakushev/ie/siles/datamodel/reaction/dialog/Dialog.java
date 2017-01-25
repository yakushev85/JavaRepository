package com.yakushev.ie.siles.datamodel.reaction.dialog;

import java.util.ArrayList;
import java.util.List;

public class Dialog {
	private List<Start> starts;
	private List<Point> points;
	
	public Dialog() {
		starts = new ArrayList<Start> ();
		points = new ArrayList<Point> ();
	}
	
	public List<Start> getStarts() {
		return starts;
	}
	public void addStart(Start start) {
		starts.add(start);
	}
	
	public List<Point> getPoints() {
		return points;
	}
	public void addPoint(Point point) {
		points.add(point);
	}

	@Override
	public String toString() {
		return "Dialog [starts=" + starts + ", points=" + points + "]";
	}
}
