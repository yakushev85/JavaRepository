package com.yakushev.site.dao;

import java.util.List;

import com.yakushev.site.entity.SiteParameter;

public interface SiteParameterDAO {
	void add(SiteParameter sp);
	void update(SiteParameter sp);
	void del(Integer id);
	void delAllParamByObject_Id(Integer id);
	SiteParameter find(Integer id);
	List<SiteParameter> findContentByObjectId(Integer id);
	List<SiteParameter> findContentByMenuObjectId(Integer id);
	List<SiteParameter> findNameByObject_Id(Integer id);
	List<SiteParameter> findAuthorByObject_Id(Integer id);
	List<SiteParameter> findDateByObject_Id(Integer id);
	List<SiteParameter> findAllParamByObject_Id(Integer id);
}
