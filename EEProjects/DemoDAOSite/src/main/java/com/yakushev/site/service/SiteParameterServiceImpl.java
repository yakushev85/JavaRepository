package com.yakushev.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yakushev.site.dao.SiteParameterDAO;
import com.yakushev.site.entity.SiteParameter;

@Service
public class SiteParameterServiceImpl implements SiteParameterService {
	@Autowired
	SiteParameterDAO siteParameterDAO;

	@Transactional
	public void add(SiteParameter sp) {
		siteParameterDAO.add(sp);
	}

	@Transactional
	public void update(SiteParameter sp) {
		siteParameterDAO.update(sp);
	}

	@Transactional
	public void del(Integer id) {
		siteParameterDAO.del(id);
	}

	@Transactional
	public void delAllParamByObject_Id(Integer id) {
		siteParameterDAO.delAllParamByObject_Id(id);
	}

	@Transactional
	public SiteParameter find(Integer id) {
		return siteParameterDAO.find(id);
	}

	@Transactional
	public List<SiteParameter> findContentByObjectId(Integer id) {
		return siteParameterDAO.findContentByObjectId(id);
	}

	@Transactional
	public List<SiteParameter> findContentByMenuObjectId(Integer id) {
		return siteParameterDAO.findContentByMenuObjectId(id);
	}

	@Transactional
	public List<SiteParameter> findNameByObject_Id(Integer id) {
		return siteParameterDAO.findNameByObject_Id(id);
	}

	@Transactional
	public List<SiteParameter> findAuthorByObject_Id(Integer id) {
		return siteParameterDAO.findAuthorByObject_Id(id);
	}

	@Transactional
	public List<SiteParameter> findDateByObject_Id(Integer id) {
		return siteParameterDAO.findDateByObject_Id(id);
	}

	@Transactional
	public List<SiteParameter> findAllParamByObject_Id(Integer id) {
		return siteParameterDAO.findAllParamByObject_Id(id);
	}
}
