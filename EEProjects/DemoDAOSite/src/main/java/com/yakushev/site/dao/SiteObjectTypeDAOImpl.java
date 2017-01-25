package com.yakushev.site.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yakushev.site.entity.SiteObject_Type;

@Repository
public class SiteObjectTypeDAOImpl implements SiteObjectTypeDAO {
	@Autowired
	SessionFactory sessionFactory;

	public void add(SiteObject_Type sot) {
		sessionFactory.getCurrentSession().save(sot);
	}

	public void del(Integer id) {
		SiteObject_Type sot = (SiteObject_Type) sessionFactory.getCurrentSession().load(SiteObject_Type.class, id);
		
		if (sot != null) {
			sessionFactory.getCurrentSession().delete(sot);
		}
	}

	public SiteObject_Type find(Integer id) {
		return (SiteObject_Type) sessionFactory.getCurrentSession().load(SiteObject_Type.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject_Type> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from "+SiteObject_Type.class.getName()).list();
	}
}
