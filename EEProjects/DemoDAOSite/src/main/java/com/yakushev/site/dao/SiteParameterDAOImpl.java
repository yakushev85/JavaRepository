package com.yakushev.site.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yakushev.site.entity.SiteParameter;

@Repository
public class SiteParameterDAOImpl implements SiteParameterDAO {
	@Autowired
	SessionFactory sessionFactory;

	public void add(SiteParameter sp) {
		sessionFactory.getCurrentSession().save(sp);
	}

	public void update(SiteParameter sp) {
		sessionFactory.getCurrentSession().update(sp);
	}

	public void del(Integer id) {
		SiteParameter sp = (SiteParameter) sessionFactory.getCurrentSession().load(SiteParameter.class, id);
		
		if (sp != null) {
			sessionFactory.getCurrentSession().delete(sp);
		}
	}

	public void delAllParamByObject_Id(Integer id) {
		List<SiteParameter> listSP = findAllParamByObject_Id(id);
		
		for (SiteParameter sp : listSP) {
			del(sp.getParameter_id());
		}
	}

	public SiteParameter find(Integer id) {
		return (SiteParameter) sessionFactory.getCurrentSession().load(SiteParameter.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findContentByObjectId(Integer id) {
		return sessionFactory.getCurrentSession().createQuery("from "+SiteParameter.class.getName() +
				" where object_id="+id+" and attribute_id=4").list();
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findContentByMenuObjectId(Integer id) {
		List<SiteParameter> listRes = new ArrayList<SiteParameter>();
		
		List<SiteParameter> listContent = (List<SiteParameter>)
				sessionFactory.getCurrentSession().createQuery("from "+SiteParameter.class.getName() +
						" where object_id="+id+" and attribute_id=5").list();
		
		for (SiteParameter paramRef : listContent) {
			List<SiteParameter> listTmpRef = (List<SiteParameter>)
					sessionFactory.getCurrentSession().createQuery("from "+SiteParameter.class.getName() +
							" where object_id="+paramRef.getReference_id() +
							" and attribute_id=4 order by parameter_id").list();
			
			if (!listTmpRef.isEmpty()) {
				listRes.add(listTmpRef.get(0));
			}
		}
		
		return listRes;
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findNameByObject_Id(Integer id) {
		return sessionFactory.getCurrentSession().createQuery("from "+SiteParameter.class.getName() +
				" where object_id="+id+" and attribute_id=1").list();
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findAuthorByObject_Id(Integer id) {
		return sessionFactory.getCurrentSession().createQuery("from "+SiteParameter.class.getName() +
				" where object_id="+id+" and attribute_id=3").list();
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findDateByObject_Id(Integer id) {
		return sessionFactory.getCurrentSession().createQuery("from "+SiteParameter.class.getName() +
				" where object_id="+id+" and attribute_id=2").list();
	}

	@SuppressWarnings("unchecked")
	public List<SiteParameter> findAllParamByObject_Id(Integer id) {
		return sessionFactory.getCurrentSession().
				createQuery("from "+SiteParameter.class.getName() + " where object_id="+id).list();
	}

}
