package com.hripsite.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Component;

import com.hripsite.entity.SiteAttribute;

@Component
public class SiteAttributeServiceImpl implements SiteAttributeService {
	private EntityManagerFactory factory;
	
	public SiteAttributeServiceImpl() {
		factory = SiteAttributeEntityFactory.getInstance();
	}

	public void add(SiteAttribute sa) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(sa);
		
		em.getTransaction().commit();
		em.close();
	}

	public void delete(Integer id) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		SiteAttribute sa = em.find(SiteAttribute.class, id);
		if (sa != null) {
			em.remove(sa);
		}
		
		em.getTransaction().commit();
		em.close();
	}

	public SiteAttribute find(Integer id) {
		EntityManager em = factory.createEntityManager();
		
		SiteAttribute sa = em.find(SiteAttribute.class, id);
		
		em.close();
		
		return sa;
	}

	@SuppressWarnings("unchecked")
	public List<SiteAttribute> findAll() {
		EntityManager em = factory.createEntityManager();

		List<SiteAttribute> resList = (List<SiteAttribute>) 
				em.createQuery("from "+SiteAttribute.class.getName()).getResultList();
		
		em.close();
		
		return resList;
	}

}
