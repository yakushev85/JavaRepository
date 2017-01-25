package com.yakushev.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yakushev.site.dao.SiteObjectTypeDAO;
import com.yakushev.site.entity.SiteObject_Type;

@Service
public class SiteObjectTypeServiceImpl implements SiteObjectTypeService {
	@Autowired
	SiteObjectTypeDAO siteObjectTypeDAO;

	@Transactional
	public void add(SiteObject_Type sot) {
		siteObjectTypeDAO.add(sot);
	}

	@Transactional
	public void del(Integer id) {
		siteObjectTypeDAO.del(id);
	}

	@Transactional
	public SiteObject_Type find(Integer id) {
		return siteObjectTypeDAO.find(id);
	}

	@Transactional
	public List<SiteObject_Type> findAll() {
		return siteObjectTypeDAO.findAll();
	}
}
