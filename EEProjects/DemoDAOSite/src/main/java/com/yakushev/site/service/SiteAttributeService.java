package com.yakushev.site.service;

import java.util.List;

import com.yakushev.site.entity.SiteAttribute;

public interface SiteAttributeService {
	void add(SiteAttribute sa);
	void delete(Integer id);
	SiteAttribute find(Integer id);
	List<SiteAttribute> findAll();
}
