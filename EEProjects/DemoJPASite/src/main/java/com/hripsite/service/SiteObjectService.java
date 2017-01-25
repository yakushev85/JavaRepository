package com.hripsite.service;

import java.util.List;

import com.hripsite.entity.SiteObject;

public interface SiteObjectService {
	void add(SiteObject so);
	void update(SiteObject so);
	void del(Integer id);
	SiteObject find(Integer id);
	List<SiteObject> findAllMaterials();
	List<SiteObject> findAllMenuItems();
	List<SiteObject> findAdminUsers();
}
