package com.yakushev.site.controller;

import static com.yakushev.site.controller.AdminControllerEnvironment.*;
import static com.yakushev.site.controller.SecurityAccountTool.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yakushev.site.bean.IdAndValue;
import com.yakushev.site.entity.SiteObject;
import com.yakushev.site.entity.SiteParameter;
import com.yakushev.site.service.AdminCreateDeleteService;
import com.yakushev.site.service.AdminToolService;
import com.yakushev.site.service.SiteObjectService;
import com.yakushev.site.service.SiteParameterService;

@Controller
public class AdminMenuItemsController {
	@Autowired
	AdminToolService adminToolService;
	
	@Autowired
	AdminCreateDeleteService adminCreateDeleteService;
	
	@Autowired
	SiteObjectService objectService;
	
	@Autowired
	SiteParameterService paramService;
	
	@RequestMapping(value="admin/showmenuitems.html", method=RequestMethod.GET)
	public String showMenuItems(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(ATTR_ISLOGGED) == null) {
			return REDIRECT_LOGIN;
		}
		
		List<SiteObject> listObjMenuItems = objectService.findAllMenuItems();
		List<IdAndValue> listNameMenuItems = adminToolService.getMenuNames();
		
		session.setAttribute(ATTR_MENUITEMS, listObjMenuItems);
		session.setAttribute(ATTR_NAMEMENUITEMS, listNameMenuItems);
		
		return SHOWMENUITEMS_PAGE;
	}

	/* operations with menu item */
	
	@RequestMapping(value="admin/newmenuitem.html", method=RequestMethod.GET)
	public String newMenuItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		adminCreateDeleteService.createNewMenuItem();
		return showMenuItems(request);
	}

	@RequestMapping(value="admin/editmenuitem.html", method=RequestMethod.GET)
	public String editMenuItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		Integer id = Integer.parseInt(request.getParameter(PARAM_ID));
		
		SiteObject objMenuItem = objectService.find(id);
		
		String nameMenuItem = "";
		List<SiteParameter> listName = paramService.findNameByObject_Id(id);
		if (!listName.isEmpty()) {
			nameMenuItem = listName.get(0).getData_value();
		}
		
		List<SiteParameter> listMaterialsByMenuItem = paramService.findContentByMenuObjectId(id);
		List<SiteObject> listObjMaterialItems = objectService.findAllMaterials();
		
		session.setAttribute(ATTR_EDITMENUITEM, objMenuItem);
		session.setAttribute(ATTR_EDITMENUNAME, nameMenuItem);
		session.setAttribute(ATTR_MATERIALSBYMENUITEM, listMaterialsByMenuItem);
		session.setAttribute(ATTR_MATERIALITEMS, listObjMaterialItems);
		
		return EDITMENUITEM_PAGE;
	}
	
	@RequestMapping(value="admin/savemenuitem.html", method=RequestMethod.POST)
	public String saveMenuItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		Integer idMenuItem = Integer.parseInt(request.getParameter(PARAM_MENUITEMID));
		String nameMenuItem = request.getParameter(PARAM_NAME);
		String internalNameMenuItem = request.getParameter(PARAM_INTERNALNAME);
		Integer newMaterialId = Integer.parseInt(request.getParameter(PARAM_NEWMATERIALID));
		Integer orderMenuItem = Integer.parseInt(request.getParameter(PARAM_ORDER));
		
		SiteObject menuItem = objectService.find(idMenuItem);
		menuItem.setName(internalNameMenuItem);
		menuItem.setOrder_num(orderMenuItem);
		objectService.update(menuItem);
		
		List<SiteParameter> listName = paramService.findNameByObject_Id(idMenuItem);
		
		if (!listName.isEmpty()) {
			SiteParameter paramName = listName.get(0);
			paramName.setData_value(nameMenuItem);
			paramService.update(paramName);
		}
		
		if (newMaterialId > 0) {
			adminCreateDeleteService.addLinkToMaterial(idMenuItem, newMaterialId);
		}
		
		return showMenuItems(request);
	}
	
	@RequestMapping(value="admin/dellinkmaterial.html", method=RequestMethod.GET)
	public String delLinkMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		Integer menuid = Integer.parseInt(request.getParameter(PARAM_MENUID));
		Integer contentid = Integer.parseInt(request.getParameter(PARAM_MATERIALID));
		
		List<SiteParameter> allParamMenuItem = paramService.findAllParamByObject_Id(menuid);
		
		for (SiteParameter paramMenuItem : allParamMenuItem) {
			if (paramMenuItem.getReference_id() == contentid) {
				paramService.del(paramMenuItem.getParameter_id());
				return showMenuItems(request);
			}
		}
		
		return showMenuItems(request);
	}
	
	@RequestMapping(value="admin/delmenuitem.html", method=RequestMethod.GET)
	public String delMenuItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		Integer id = Integer.parseInt(request.getParameter(PARAM_ID));
		
		adminCreateDeleteService.delObjectAndAllParamsByObjectId(id);
		
		return showMenuItems(request);
	}
}
