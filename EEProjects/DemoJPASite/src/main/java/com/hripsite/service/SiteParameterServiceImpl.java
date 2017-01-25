package com.hripsite.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Component;

import com.hripsite.entity.SiteParameter;

@Component
public class SiteParameterServiceImpl implements SiteParameterService {
	private EntityManagerFactory factory;
	
	public SiteParameterServiceImpl() {
		factory = SiteParameterEntityFactory.getInstance();
	}

	public void add(SiteParameter sp) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(sp);
		
		em.getTransaction().commit();
		em.close();
	}
	
	public void update(SiteParameter sp) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.merge(sp);
		
		em.getTransaction().commit();
		em.close();		
	}
	
	public void del(Integer id) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		SiteParameter sp = em.find(SiteParameter.class, id);
		if (sp != null) {
			em.remove(sp);
		}
		
		em.getTransaction().commit();
		em.close();
	}

	public void delAllParamByObject_Id(Integer id) {
		List<SiteParameter> listSP = findAllParamByObject_Id(id);
		
		for (SiteParameter sp : listSP) {
			del(sp.getParameter_id());
		}
	}

	public SiteParameter find(Integer id) {
		EntityManager em = factory.createEntityManager();
		
		SiteParameter resTest = em.find(SiteParameter.class, id);
		
		em.close();
		
		return resTest;
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findContentByObjectId(Integer id) {
		EntityManager em = factory.createEntityManager();

		List<SiteParameter> resList = (List<SiteParameter>) 
				em.createQuery("from "+SiteParameter.class.getName() +
						" where object_id="+id+" and attribute_id=4").getResultList();
		
		em.close();
		
		return resList;
	}
	
	@SuppressWarnings("unchecked")
	public List<SiteParameter> findContentByMenuObjectId(Integer id) {
		EntityManager em = factory.createEntityManager();
		List<SiteParameter> listRes = new ArrayList<SiteParameter>();
		
		List<SiteParameter> listContent = (List<SiteParameter>)
				em.createQuery("from "+SiteParameter.class.getName() +
						" where object_id="+id+" and attribute_id=5").getResultList();
		
		for (SiteParameter paramRef : listContent) {
			List<SiteParameter> listTmpRef = (List<SiteParameter>)
					em.createQuery("from "+SiteParameter.class.getName() +
							" where object_id="+paramRef.getReference_id() +
							" and attribute_id=4").getResultList();
			
			if (!listTmpRef.isEmpty()) {
				listRes.add(listTmpRef.get(0));
			}
		}
		
		em.close();
		
		return listRes;
	}


	@SuppressWarnings("unchecked")
	public List<SiteParameter> findNameByObject_Id(Integer id) {
		EntityManager em = factory.createEntityManager();

		List<SiteParameter> resList = (List<SiteParameter>) 
				em.createQuery("from "+SiteParameter.class.getName() +
						" where object_id="+id+" and attribute_id=1").getResultList();
		
		em.close();
		
		return resList;
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findAllParamByObject_Id(Integer id) {
		EntityManager em = factory.createEntityManager();

		List<SiteParameter> resList = (List<SiteParameter>) 
				em.createQuery("from "+SiteParameter.class.getName() + " where object_id="+id).getResultList();
		
		em.close();
		
		return resList;
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findAuthorByObject_Id(Integer id) {
		EntityManager em = factory.createEntityManager();

		List<SiteParameter> resList = (List<SiteParameter>) 
				em.createQuery("from "+SiteParameter.class.getName() +
						" where object_id="+id+" and attribute_id=3").getResultList();
		
		em.close();
		
		return resList;
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findDateByObject_Id(Integer id) {
		EntityManager em = factory.createEntityManager();

		List<SiteParameter> resList = (List<SiteParameter>) 
				em.createQuery("from "+SiteParameter.class.getName() +
						" where object_id="+id+" and attribute_id=2").getResultList();
		
		em.close();
		
		return resList;
	}
}
