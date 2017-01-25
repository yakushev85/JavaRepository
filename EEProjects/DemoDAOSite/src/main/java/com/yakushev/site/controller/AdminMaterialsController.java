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
public class AdminMaterialsController {
	@Autowired
	AdminToolService adminToolService;
	
	@Autowired
	AdminCreateDeleteService adminCreateDeleteService;
	
	@Autowired
	SiteObjectService objectService;
	
	@Autowired
	SiteParameterService paramService;
	
	@RequestMapping(value="admin/showmaterials.html", method=RequestMethod.GET)
	public String showMaterials(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		List<SiteObject> listObjMaterialItems = objectService.findAllMaterials();
		List<IdAndValue> listAuthorMaterials = adminToolService.getMaterialAuthors();
		List<IdAndValue> listDateMaterials = adminToolService.getMaterialDates();
		
		session.setAttribute(ATTR_MATERIALITEMS, listObjMaterialItems);
		session.setAttribute(ATTR_MATERIALAUTHORS, listAuthorMaterials);
		session.setAttribute(ATTR_MATERIALDATES, listDateMaterials);
		
		return SHOWMATERIALS_PAGE;
	}
	
	/* operations with material*/
	
	@RequestMapping(value="admin/newmaterial.html", method=RequestMethod.GET)
	public String newMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		adminCreateDeleteService.createNewMaterial();
		
		return showMaterials(request);
	}
	
	@RequestMapping(value="admin/editmaterial.html", method=RequestMethod.GET)
	public String editMaterial(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
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
			return REDIRECT_LOGIN;
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
			return REDIRECT_LOGIN;
		}
		
		Integer id = Integer.parseInt(request.getParameter(PARAM_ID));
		
		adminCreateDeleteService.delObjectAndAllParamsByObjectId(id);
		
		return showMaterials(request);
	}	
}
