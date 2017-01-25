package com.hripsite.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SiteAttributeEntityFactory {
	private static EntityManagerFactory factory = null;
	public static String NAMEENTITY_ATTR = "hsattribute"; 

	public static EntityManagerFactory getInstance() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(NAMEENTITY_ATTR);
		}
		
		return factory;
	}
}
