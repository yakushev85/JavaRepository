package com.yakushev.site.service;

import com.yakushev.site.entity.SiteObject;

public interface AdminCreateDeleteService {
	void addLinkToMaterial(Integer menu_id, Integer material_id);
	SiteObject createNewMenuItem();
	SiteObject createNewMaterial();
	void delObjectAndAllParamsByObjectId(Integer id);
}
