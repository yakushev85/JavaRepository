package com.hripsite.service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SiteParameterEntityFactory {
	private static EntityManagerFactory factory = null;
	public static String NAMEENTITY_PARAM = "hsparameter"; 

	public static EntityManagerFactory getInstance() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory(NAMEENTITY_PARAM);
		}
		
		return factory;
	}
}
