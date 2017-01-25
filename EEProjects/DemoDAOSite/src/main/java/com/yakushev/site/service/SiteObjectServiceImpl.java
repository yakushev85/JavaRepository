package com.yakushev.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yakushev.site.dao.SiteObjectDAO;
import com.yakushev.site.entity.SiteObject;

@Service
public class SiteObjectServiceImpl implements SiteObjectService {
	@Autowired
	SiteObjectDAO siteObjectDAO;

	@Transactional
	public void add(SiteObject so) {
		siteObjectDAO.add(so);
	}

	@Transactional
	public void update(SiteObject so) {
		siteObjectDAO.update(so);
	}

	@Transactional
	public void del(Integer id) {
		siteObjectDAO.del(id);
	}

	@Transactional
	public SiteObject find(Integer id) {
		return siteObjectDAO.find(id);
	}

	@Transactional
	public List<SiteObject> findAllMaterials() {
		return siteObjectDAO.findAllMaterials();
	}

	@Transactional
	public List<SiteObject> findAllMenuItems() {
		return siteObjectDAO.findAllMenuItems();
	}

	@Transactional
	public List<SiteObject> findAdminUsers() {
		return siteObjectDAO.findAdminUsers();
	}
}
