package com.yakushev.site.service;

import java.util.List;

import com.yakushev.site.bean.IdAndValue;
import com.yakushev.site.entity.SiteObject;
import com.yakushev.site.entity.SiteParameter;

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
	List<SiteObject> getContentObjectsByMenuObjectId(Integer id);
}
