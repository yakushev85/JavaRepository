package com.yakushev.ie.siles.observ;

/**
 * An interface is responsible for the class of observable.
 */

public interface Observable {
	/**
	 * Method register observer-class.
	 * @param o observer-class.
	 */
	public void registerObserver(Observer o);
	
	/**
	 * Method remove observer-class.
	 * @param o observer-class.
	 */
	public void removeObserver(Observer o);
	
	/**
	 * Method update all observer-classes.
	 */
	public void notifyObservers(String msg);
}
