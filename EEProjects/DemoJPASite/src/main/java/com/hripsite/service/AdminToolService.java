package com.hripsite.service;

import java.util.List;

import com.hripsite.bean.IdAndValue;
import com.hripsite.entity.SiteObject;
import com.hripsite.entity.SiteParameter;

public interface AdminToolService {
	String getAdminUserName();
	String getAdminUserPassword();
	void setAdminUserPassword(String password);
	SiteParameter getAuthorByMaterialId(Integer id);
	SiteParameter getContentByMaterialId(Integer id);
	List<IdAndValue> getMenuNames();
	List<IdAndValue> getLinkedContentNamesWithMenuItems();
	List<IdAndValue> getMaterialAuthors();
	List<IdAndValue> getMaterialDates();
	void addLinkToMaterial(Integer menu_id, Integer material_id);
	SiteObject createNewMenuItem();
	SiteObject createNewMaterial();
	void delObjectAndAllParamsByObjectId(Integer id);
}
