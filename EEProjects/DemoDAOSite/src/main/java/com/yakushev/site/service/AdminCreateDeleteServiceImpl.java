package com.yakushev.site.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yakushev.site.entity.SiteObject;
import com.yakushev.site.entity.SiteParameter;

@Component
public class AdminCreateDeleteServiceImpl implements AdminCreateDeleteService{
	private static String STR_NEWMATERIAL = "New material";
	private static String STR_NEWMENUITEM = "New menu item";
	private static String STR_NONE = "None";
	
	@Autowired
	SiteObjectService objectService;
	
	@Autowired
	SiteParameterService paramService;

	
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
