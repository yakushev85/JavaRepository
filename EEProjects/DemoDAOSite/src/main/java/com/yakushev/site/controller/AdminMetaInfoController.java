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

import com.yakushev.site.entity.SiteAttribute;
import com.yakushev.site.entity.SiteObject_Type;
import com.yakushev.site.service.SiteAttributeService;
import com.yakushev.site.service.SiteObjectTypeService;

@Controller
public class AdminMetaInfoController {
	@Autowired
	SiteAttributeService attributeService;
	
	@Autowired
	SiteObjectTypeService objectTypeService;
	
	/* show meta info */
	@RequestMapping(value="admin/showmetainfo.html", method=RequestMethod.GET)
	public String showMetaInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		List<SiteAttribute> allAtributes = attributeService.findAll();
		List<SiteObject_Type> allObjectTypes = objectTypeService.findAll();
		
		session.setAttribute(ATTR_ALLATTRIBUTES, allAtributes);
		session.setAttribute(ATTR_ALLOBJECTTYPES, allObjectTypes);
		
		return SHOWMETAINFO_PAGE;
	}

}
