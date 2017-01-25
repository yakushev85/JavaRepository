package com.yakushev.site.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yakushev.site.entity.SiteAttribute;

@Repository
public class SiteAttributeDAOImpl implements SiteAttributeDAO {
	@Autowired
	SessionFactory sessionFactory;

	public void add(SiteAttribute sa) {
		sessionFactory.getCurrentSession().save(sa);
	}

	public void delete(Integer id) {
		SiteAttribute sa = (SiteAttribute) sessionFactory.getCurrentSession().load(SiteAttribute.class, id);
		
		if (sa != null) {
			sessionFactory.getCurrentSession().delete(sa);
		}
	}

	public SiteAttribute find(Integer id) {
		return (SiteAttribute) sessionFactory.getCurrentSession().load(SiteAttribute.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<SiteAttribute> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from "+SiteAttribute.class.getName()).list();
	}

}
