package com.yakushev.site.dao;

import java.util.List;

import com.yakushev.site.entity.SiteAttribute;

public interface SiteAttributeDAO {
	void add(SiteAttribute sa);
	void delete(Integer id);
	SiteAttribute find(Integer id);
	List<SiteAttribute> findAll();
}
