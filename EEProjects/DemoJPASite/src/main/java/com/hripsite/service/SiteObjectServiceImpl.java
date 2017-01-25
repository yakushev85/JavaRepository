package com.hripsite.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Component;

import com.hripsite.entity.SiteObject;

@Component
public class SiteObjectServiceImpl implements SiteObjectService {
	private EntityManagerFactory factory;
	
	public SiteObjectServiceImpl() {
		factory = SiteObjectEntityFactory.getInstance();
	}

	public void add(SiteObject so) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(so);
		
		em.getTransaction().commit();
		em.close();
	}

	public void update(SiteObject so) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.merge(so);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public void del(Integer id) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		SiteObject so = em.find(SiteObject.class, id);
		if (so != null) {
			em.remove(so);
		}
		
		em.getTransaction().commit();
		em.close();
	}

	public SiteObject find(Integer id) {
		EntityManager em = factory.createEntityManager();
		
		SiteObject so = em.find(SiteObject.class, id);
		
		em.close();
		
		return so;
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject> findAllMaterials() {
		EntityManager em = factory.createEntityManager();

		List<SiteObject> resList = (List<SiteObject>) 
				em.createQuery("from "+SiteObject.class.getName()+" where object_type_id=2 order by order_num").getResultList();
		
		em.close();
		
		return resList;
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject> findAllMenuItems() {
		EntityManager em = factory.createEntityManager();

		List<SiteObject> resList = (List<SiteObject>) 
				em.createQuery("from "+SiteObject.class.getName()+" where object_type_id=1 order by order_num").getResultList();
		
		em.close();
		
		return resList;
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject> findAdminUsers() {
		EntityManager em = factory.createEntityManager();

		List<SiteObject> resList = (List<SiteObject>) 
				em.createQuery("from "+SiteObject.class.getName()+" where object_type_id=3 order by order_num").getResultList();
		
		em.close();
		
		return resList;
	}

}
