package com.hripsite.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SiteObjectEntityFactory {
	private static EntityManagerFactory factory = null;
	public static String NAMEENTITY_OBJECT = "hsobjet"; 

	public static EntityManagerFactory getInstance() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(NAMEENTITY_OBJECT);
		}
		
		return factory;
	}

}
