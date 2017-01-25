package com.yakushev.site.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yakushev.site.entity.SiteObject;

@Repository
public class SiteObjectDAOImpl implements SiteObjectDAO {
	@Autowired
	SessionFactory sessionFactory;

	public void add(SiteObject so) {
		sessionFactory.getCurrentSession().save(so);
	}

	public void update(SiteObject so) {
		sessionFactory.getCurrentSession().update(so);
	}

	public void del(Integer id) {
		SiteObject so = (SiteObject) sessionFactory.getCurrentSession().load(SiteObject.class, id);
		
		if (so != null) {
			sessionFactory.getCurrentSession().delete(so);
		}
	}

	public SiteObject find(Integer id) {
		return (SiteObject) sessionFactory.getCurrentSession().load(SiteObject.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject> findAllMaterials() {
		return sessionFactory.getCurrentSession().
				createQuery("from "+SiteObject.class.getName()+" where object_type_id=2 order by order_num").list();
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject> findAllMenuItems() {
		return sessionFactory.getCurrentSession().
				createQuery("from "+SiteObject.class.getName()+" where object_type_id=1 order by order_num").list();
	}

	@SuppressWarnings("unchecked")
	public List<SiteObject> findAdminUsers() {
		return sessionFactory.getCurrentSession().
				createQuery("from "+SiteObject.class.getName()+" where object_type_id=3 order by order_num").list();
	}

}
