package com.hripsite.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hripsite.bean.IdAndValue;
import com.hripsite.entity.SiteObject;
import com.hripsite.entity.SiteParameter;

@Component
public class AdminToolServiceImpl implements AdminToolService {
	private static String STR_NEWMATERIAL = "New material";
	private static String STR_NEWMENUITEM = "New menu item";
	private static String STR_NONE = "None";
	
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

	public SiteParameter getAuthorByMaterialId(Integer id) {
		List<SiteParameter> listParams = paramService.findAllParamByObject_Id(id);
		
		for (SiteParameter param : listParams) {
			if (param.getAttribute_id() == 3) {
				return param;
			}
		}
		
		return null;
	}
	
	public SiteParameter getContentByMaterialId(Integer id) {
		List<SiteParameter> listParams = paramService.findAllParamByObject_Id(id);
		
		for (SiteParameter param : listParams) {
			if (param.getAttribute_id() == 4) {
				return param;
			}
		}
		
		return null;
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

	public void addLinkToMaterial(Integer menu_id, Integer material_id) {
		SiteParameter linkParam = new SiteParameter();
		linkParam.setObject_id(menu_id);
		linkParam.setAttribute_id(5);
		linkParam.setReference_id(material_id);
		paramService.add(linkParam);
	}
	
	public SiteObject createNewMenuItem() {
		SiteObject menuItem = new SiteObject();
		menuItem.setName(STR_NEWMENUITEM);
		menuItem.setObject_type_id(1);
		menuItem.setOrder_num(0);
		objectService.add(menuItem);
		
		SiteParameter nameParam = new SiteParameter();
		nameParam.setObject_id(menuItem.getObject_id());
		nameParam.setAttribute_id(1);
		nameParam.setReference_id(0);
		nameParam.setData_value(STR_NEWMENUITEM);
		paramService.add(nameParam);
		
		return menuItem;
	}
	
	public SiteObject createNewMaterial() {
		SiteObject newMaterial = new SiteObject();
		newMaterial.setName(STR_NEWMATERIAL);
		newMaterial.setObject_type_id(2);
		newMaterial.setOrder_num(0);
		objectService.add(newMaterial);
		
		SiteParameter authorParam = new SiteParameter();
		authorParam.setObject_id(newMaterial.getObject_id());
		authorParam.setAttribute_id(3);
		authorParam.setReference_id(0);
		authorParam.setData_value(STR_NONE);
		paramService.add(authorParam);
		
		SiteParameter dateParam = new SiteParameter();
		dateParam.setObject_id(newMaterial.getObject_id());
		dateParam.setAttribute_id(2);
		dateParam.setReference_id(0);
		dateParam.setDate_value(new Date());
		paramService.add(dateParam);
		
		SiteParameter contentParam = new SiteParameter();
		contentParam.setObject_id(newMaterial.getObject_id());
		contentParam.setAttribute_id(4);
		contentParam.setReference_id(0);
		contentParam.setData_value(STR_NONE);
		paramService.add(contentParam);
		
		return newMaterial;
	}

	public void delObjectAndAllParamsByObjectId(Integer id) {
		paramService.delAllParamByObject_Id(id);
		objectService.del(id);
	}
}
