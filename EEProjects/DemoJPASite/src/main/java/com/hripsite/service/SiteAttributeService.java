package com.hripsite.service;

import java.util.List;

import com.hripsite.entity.SiteAttribute;

public interface SiteAttributeService {
	void add(SiteAttribute sa);
	void delete(Integer id);
	SiteAttribute find(Integer id);
	List<SiteAttribute> findAll();
}
