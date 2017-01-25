package com.yakushev.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yakushev.site.dao.SiteAttributeDAO;
import com.yakushev.site.entity.SiteAttribute;

@Service
public class SiteAttributeServiceImpl implements SiteAttributeService {
	@Autowired
	SiteAttributeDAO siteAttributeDAO;

	@Transactional
	public void add(SiteAttribute sa) {
		siteAttributeDAO.add(sa);
	}

	@Transactional
	public void delete(Integer id) {
		siteAttributeDAO.delete(id);
	}

	@Transactional
	public SiteAttribute find(Integer id) {
		return siteAttributeDAO.find(id);
	}

	@Transactional
	public List<SiteAttribute> findAll() {
		return siteAttributeDAO.findAll();
	}
}
