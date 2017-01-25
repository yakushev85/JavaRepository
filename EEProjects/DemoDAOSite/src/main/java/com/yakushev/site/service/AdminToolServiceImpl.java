package com.yakushev.site.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yakushev.site.bean.IdAndValue;
import com.yakushev.site.entity.SiteObject;
import com.yakushev.site.entity.SiteParameter;

@Component
public class AdminToolServiceImpl implements AdminToolService {	
	@Autowired
	SiteObjectService objectService;
	
	@Autowired
	SiteParameterService paramService;

	public String getAdminUserPassword() {
		List<SiteObject> adminList = objectService.findAdminUsers();
		
		if (!adminList.isEmpty()) {
			List<SiteParameter> adminParamList = 
					paramService.findAllParamByObject_Id(adminList.get(0).getObject_id());
			
			if (!adminParamList.isEmpty()) {
				return adminParamList.get(0).getData_value();
			}
		}
		
		return null;
	}

	public void setAdminUserPassword(String password) {
		List<SiteObject> adminList = objectService.findAdminUsers();
		
		if (!adminList.isEmpty()) {
			List<SiteParameter> adminParamList = 
					paramService.findAllParamByObject_Id(adminList.get(0).getObject_id());
			
			if (!adminParamList.isEmpty()) {
				SiteParameter paramAdminPassword = adminParamList.get(0);
				paramAdminPassword.setData_value(password);
				
				paramService.update(paramAdminPassword);
			}
		}
	}
	
	public String getAdminUserName() {
		List<SiteObject> adminList = objectService.findAdminUsers();
		
		if (!adminList.isEmpty()) {
			return adminList.get(0).getName();
		} else {
			return null;
		}
	}

	
	private SiteParameter getValueById(Integer idObject, Integer idAttribute) {
		List<SiteParameter> listParams = paramService.findAllParamByObject_Id(idObject);
		
		for (SiteParameter param : listParams) {
			if (param.getAttribute_id() == idAttribute) {
				return param;
			}
		}
		
		return null;
	}
	
	public SiteParameter getAuthorByMaterialId(Integer id) {
		return getValueById(id, 3);
	}
	
	public SiteParameter getContentByMaterialId(Integer id) {
		return getValueById(id, 4);
	}
	
	public List<IdAndValue> getMenuNames() {
		List<SiteObject> listMenuItems = objectService.findAllMenuItems();
		List<IdAndValue> resList = new ArrayList<IdAndValue> ();
		
		for (SiteObject siteObj : listMenuItems) {
			List<SiteParameter> listNames = 
					paramService.findNameByObject_Id(siteObj.getObject_id());
			
			if (!listNames.isEmpty()) {
				IdAndValue idAndName = new IdAndValue();
				idAndName.setId(siteObj.getObject_id());
				idAndName.setValue(listNames.get(0).getData_value());
				resList.add(idAndName);
			}			
		}
		
		return resList;
	}

	public List<IdAndValue> getLinkedContentNamesWithMenuItems() {
		List<SiteObject> listMenuItems = objectService.findAllMenuItems();
		List<IdAndValue> resList = new ArrayList<IdAndValue> ();
		
		for (SiteObject siteObj : listMenuItems) {
			List<SiteParameter> listContents = 
					paramService.findContentByMenuObjectId(siteObj.getObject_id());
			
			for (SiteParameter content : listContents) {
				SiteObject contentObj = objectService.find(content.getObject_id());
				
				IdAndValue idAndName = new IdAndValue();
				idAndName.setId(siteObj.getObject_id());
				idAndName.setValue(contentObj.getName());
				resList.add(idAndName);
			}
		}
		
		return resList;
	}

	public List<IdAndValue> getMaterialAuthors() {
		List<SiteObject> listMaterialsItems = objectService.findAllMaterials();
		List<IdAndValue> resList = new ArrayList<IdAndValue> ();
		
		for (SiteObject siteObj : listMaterialsItems) {
			List<SiteParameter> listAuthors = paramService.findAuthorByObject_Id(siteObj.getObject_id());
			
			if (!listAuthors.isEmpty()) {
				IdAndValue idAndName = new IdAndValue();
				idAndName.setId(siteObj.getObject_id());
				idAndName.setValue(listAuthors.get(0).getData_value());
				resList.add(idAndName);
			}
		}
		
		return resList;
	}

	public List<IdAndValue> getMaterialDates() {
		List<SiteObject> listMaterialsItems = objectService.findAllMaterials();
		List<IdAndValue> resList = new ArrayList<IdAndValue> ();
		
		for (SiteObject siteObj : listMaterialsItems) {
			List<SiteParameter> listDates = paramService.findDateByObject_Id(siteObj.getObject_id());
			
			if (!listDates.isEmpty()) {
				IdAndValue idAndName = new IdAndValue();
				idAndName.setId(siteObj.getObject_id());
				idAndName.setValue(listDates.get(0).getDate_value().toString());
				resList.add(idAndName);
			}
			
		}
		
		return resList;
	}
	
	public List<SiteObject> getContentObjectsByMenuObjectId(Integer id) {
		List<SiteParameter> listContentParams = paramService.findContentByMenuObjectId(id);
		
		List<SiteObject> contentObjects = new ArrayList<SiteObject> ();
		for (SiteParameter itemParam : listContentParams) {
			contentObjects.add(objectService.find(itemParam.getObject_id()));
		}
		
		return contentObjects;
	}

}
