package com.hripsite.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SiteObjectTypeEntityFactory {
	private static EntityManagerFactory factory = null;
	public static String NAMEENTITY_OT = "hsobjet_type"; 

	public static EntityManagerFactory getInstance() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(NAMEENTITY_OT);
		}
		
		return factory;
	}
}
