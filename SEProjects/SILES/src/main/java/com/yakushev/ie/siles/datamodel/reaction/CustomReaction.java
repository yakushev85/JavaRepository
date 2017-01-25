package com.yakushev.ie.siles.datamodel.reaction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CustomReaction {
	protected Reaction customReaction;
	private boolean skipFilreringSubValue;
	
	public CustomReaction(Reaction creaction) {
		skipFilreringSubValue = false;
		customReaction = creaction;
	}
	
	public String getSubValue(String m) {
		if (skipFilreringSubValue) {
			return m.trim().toLowerCase();
		} else {
			Pattern pattern = Pattern.compile(customReaction.getPatternFromMsg(m));
			Matcher matcher = pattern.matcher(m.trim().toLowerCase());
			matcher.matches();
			return matcher.group(matcher.groupCount());
		}
	}
	
	abstract public String getCustomAnswer(String msg);

	public boolean isSkipFilreringSubValue() {
		return skipFilreringSubValue;
	}

	public void setSkipFilreringSubValue(boolean skipFilreringSubValue) {
		this.skipFilreringSubValue = skipFilreringSubValue;
	}
}
