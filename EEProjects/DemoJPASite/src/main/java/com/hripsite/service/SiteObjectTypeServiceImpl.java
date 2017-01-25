package com.hripsite.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Component;

import com.hripsite.entity.SiteObject_Type;

@Component
public class SiteObjectTypeServiceImpl implements SiteObjectTypeService {
	private EntityManagerFactory factory;
	
	public SiteObjectTypeServiceImpl() {
		factory = SiteObjectTypeEntityFactory.getInstance();
	}

	public void add(SiteObject_Type sot) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(sot);
		
		em.getTransaction().commit();
		em.close();
	}

	public void del(Integer id) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		SiteObject_Type sot = em.find(SiteObject_Type.class, id);
		if (sot != null) {
			em.remove(sot);
		}
		
		em.getTransaction().commit();
		em.close();
	}

	public SiteObject_Type find(Integer id) {
		EntityManager em = factory.createEntityManager();
		
		SiteObject_Type sot = em.find(SiteObject_Type.class, id);
		
		em.close();
		
		return sot;
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject_Type> findAll() {
		EntityManager em = factory.createEntityManager();

		List<SiteObject_Type> resList = (List<SiteObject_Type>) 
				em.createQuery("from "+SiteObject_Type.class.getName()).getResultList();
		
		em.close();
		
		return resList;
	}

}
