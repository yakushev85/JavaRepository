package com.yakushev.site.service;

import java.util.List;

import com.yakushev.site.entity.SiteObject_Type;

public interface SiteObjectTypeService {
	void add(SiteObject_Type sot);
	void del(Integer id);
	SiteObject_Type find(Integer id);
	List<SiteObject_Type> findAll();
}
