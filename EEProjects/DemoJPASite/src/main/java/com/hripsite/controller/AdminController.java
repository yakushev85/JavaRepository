package com.hripsite.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hripsite.bean.IdAndValue;
import com.hripsite.entity.SiteObject;
import com.hripsite.entity.SiteParameter;
import com.hripsite.service.AdminToolService;
import com.hripsite.service.SiteObjectService;
import com.hripsite.service.SiteParameterService;
import static com.hripsite.controller.AdminControllerEnvironment.*;

@Controller
public class AdminController {
	private int attemptsToLogin = MAX_ATTEMPTS_TOLOGIN;
	
	@Autowired
	AdminToolService adminToolService;
	
	@Autowired
	SiteObjectService objectService;
	
	@Autowired
	SiteParameterService paramService;
	
	/* login and show */
	public boolean isLogin(HttpSession session) {
		return session.getAttribute(ATTR_ISLOGGED) != null;
	}
	
	@RequestMapping(value="admin/login.html", method=RequestMethod.GET)
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(ATTR_ISLOGGED);
		
		return LOGIN_PAGE;
	}
	
	@RequestMapping(value="admin/show.html", method=RequestMethod.POST)
	public String checkCredential(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = request.getParameter(PARAM_USER);
		String password = request.getParameter(PARAM_PASSWORD);
		
		if (username == null || password == null) {
			return login(request);
		}
		
		if (attemptsToLogin < 1) {
			session.setAttribute(ATTR_ERRORMSG, MSG_NULLATTEMPTS);
			return login(request);
		}
		
		if (adminToolService.getAdminUserName().equals(username) &&
				adminToolService.getAdminUserPassword().equals(password)) {
			session.setAttribute(ATTR_ISLOGGED, true);
			session.removeAttribute(ATTR_ERRORMSG);
			attemptsToLogin = MAX_ATTEMPTS_TOLOGIN;
			return showMenuItems(request);
		} else {
			session.setAttribute(ATTR_ERRORMSG, MSG_WRONGPASS);
			attemptsToLogin--;
		}
		
		return login(request);
	}
	
	@RequestMapping(value="admin/showmenuitems.html", method=RequestMethod.GET)
	public String showMenuItems(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(ATTR_ISLOGGED) == null) {
			return login(request);
		}
		
		List<SiteObject> listObjMenuItems = objectService.findAllMenuItems();
		List<IdAndValue> listNameMenuItems = adminToolService.getMenuNames();
		
		session.setAttribute(ATTR_MENUITEMS, listObjMenuItems);
		session.setAttribute(ATTR_NAMEMENUITEMS, listNameMenuItems);
		
		return SHOWMENUITEMS_PAGE;
	}
	
	@RequestMapping(value="admin/showmaterials.html", method=RequestMethod.GET)
	public String showMaterials(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		List<SiteObject> listObjMaterialItems = objectService.findAllMaterials();
		List<IdAndValue> listAuthorMaterials = adminToolService.getMaterialAuthors();
		List<IdAndValue> listDateMaterials = adminToolService.getMaterialDates();
		
		session.setAttribute(ATTR_MATERIALITEMS, listObjMaterialItems);
		session.setAttribute(ATTR_MATERIALAUTHORS, listAuthorMaterials);
		session.setAttribute(ATTR_MATERIALDATES, listDateMaterials);
		
		return SHOWMATERIALS_PAGE;
	}

	/* operations with menu item */
	
	@RequestMapping(value="admin/newmenuitem.html", method=RequestMethod.GET)
	public String newMenuItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		adminToolService.createNewMenuItem();
		return showMenuItems(request);
	}

	@RequestMapping(value="admin/editmenuitem.html", method=RequestMethod.GET)
	public String editMenuItem(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
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
			return login(request);
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
			adminToolService.addLinkToMaterial(idMenuItem, newMaterialId);
		}
		
		return showMenuItems(request);
	}
	
	@RequestMapping(value="admin/dellinkmaterial.html", method=RequestMethod.GET)
	public String delLinkMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
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
			return login(request);
		}
		
		Integer id = Integer.parseInt(request.getParameter(PARAM_ID));
		
		adminToolService.delObjectAndAllParamsByObjectId(id);
		
		return showMenuItems(request);
	}	

	/* operations with material*/
	
	@RequestMapping(value="admin/newmaterial.html", method=RequestMethod.GET)
	public String newMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		adminToolService.createNewMaterial();
		
		return showMaterials(request);
	}
	
	@RequestMapping(value="admin/editmaterial.html", method=RequestMethod.GET)
	public String editMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		Integer id = Integer.parseInt(request.getParameter(PARAM_ID));
		
		SiteObject material = objectService.find(id);
		String authorMaterial = adminToolService.getAuthorByMaterialId(id).getData_value();
		String contentMaterial = adminToolService.getContentByMaterialId(id).getData_value();
		
		session.setAttribute(ATTR_MATERIAL, material);
		session.setAttribute(ATTR_AUTHOR, authorMaterial);
		session.setAttribute(ATTR_CONTENT, contentMaterial);
		
		return EDITMATERIAL_PAGE;
	}
	
	@RequestMapping(value="admin/savematerial.html", method=RequestMethod.POST)
	public String saveMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		Integer idMaterial = Integer.parseInt(request.getParameter(PARAM_MATERIALID));
		String nameMaterial = request.getParameter(PARAM_NAME);
		String authorMaterial = request.getParameter(PARAM_AUTHOR);
		String contentMaterial = request.getParameter(PARAM_CONTENT);
		
		SiteObject material = objectService.find(idMaterial);
		material.setName(nameMaterial);
		objectService.update(material);
		
		SiteParameter authorParam = adminToolService.getAuthorByMaterialId(idMaterial);
		authorParam.setData_value(authorMaterial);
		paramService.update(authorParam);
		
		SiteParameter contentParam = adminToolService.getContentByMaterialId(idMaterial);
		contentParam.setData_value(contentMaterial);
		paramService.update(contentParam);
		
		return showMaterials(request);
	}

	@RequestMapping(value="admin/delmaterial.html", method=RequestMethod.GET)
	public String delMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		Integer id = Integer.parseInt(request.getParameter(PARAM_ID));
		
		adminToolService.delObjectAndAllParamsByObjectId(id);
		
		return showMaterials(request);
	}
	
	/* Administrator account */
	
	@RequestMapping(value="admin/editadminaccount.html", method=RequestMethod.GET)
	public String editAdminAccount(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		String adminUserName = adminToolService.getAdminUserName();
		
		session.setAttribute("adminusername", adminUserName);
		
		return EDITADMINACCOUNT_PAGE;
	}
	
	@RequestMapping(value="admin/saveadminaccount.html", method=RequestMethod.POST)
	public String saveAdminAccount(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return login(request);
		}
		
		String oldPassword = request.getParameter(PARAM_OLDPASSWORD);
		String newPassword = request.getParameter(PARAM_NEWPASSWORD);
		String repeatPassword = request.getParameter(PARAM_REPEATPASSWORD);
		
		if (!oldPassword.equals(adminToolService.getAdminUserPassword())) {
			session.setAttribute(ATTR_ERRORADMINACCOUNT, MSG_WRONGOLDPASS);
			return editAdminAccount(request);
		}
		
		if (!newPassword.equals(repeatPassword)) {
			session.setAttribute(ATTR_ERRORADMINACCOUNT, MSG_WRONGREPATPASS);
			return editAdminAccount(request);
		}
		
		session.removeAttribute(ATTR_ERRORADMINACCOUNT);
		
		adminToolService.setAdminUserPassword(newPassword);
		
		return showMenuItems(request);
	}
}
