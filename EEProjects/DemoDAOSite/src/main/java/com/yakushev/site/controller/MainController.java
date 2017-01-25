package com.yakushev.site.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yakushev.site.entity.SiteObject;
import com.yakushev.site.entity.SiteParameter;
import com.yakushev.site.service.AdminToolService;
import com.yakushev.site.service.SiteObjectService;
import com.yakushev.site.service.SiteParameterService;

import static com.yakushev.site.controller.MainControllerEnvironment.*;

@Controller
public class MainController {
	@Autowired
	SiteObjectService objectService;
	
	@Autowired
	SiteParameterService paramService;
	
	@Autowired
	AdminToolService adminToolService;
		
	@RequestMapping(value="show.html", method=RequestMethod.GET)
	public String show(HttpServletRequest request) {
		try {
			Integer action_id = Integer.parseInt(request.getParameter(PARAM_ACTIONID));
			HttpSession session = request.getSession();
			
			if (session.getAttribute(ATTR_MENUITEMS) == null) {
				return index(request);
			}
			
			session.setAttribute(ATTR_CONTENT, paramService.findContentByMenuObjectId(action_id));
			session.setAttribute(ATTR_CONTENT_OBJECTS, 
					adminToolService.getContentObjectsByMenuObjectId(action_id));
			
			return SHOW_PAGE;
		} catch (NumberFormatException e) {
			return index(request);
		}
	}
	
	@RequestMapping(value="index.html")
	public String index(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		List<SiteObject> listMenuItems = objectService.findAllMenuItems();
		List<SiteParameter> listResMenuItems = new ArrayList<SiteParameter> ();
		
		for (SiteObject siteObject: listMenuItems) {
			List<SiteParameter> listParamMenuItem = 
					paramService.findNameByObject_Id(siteObject.getObject_id());
			if (!listParamMenuItem.isEmpty()) {
				listResMenuItems.add(listParamMenuItem.get(0));
			}
		}
		
		session.setAttribute(ATTR_MENUITEMS, listResMenuItems);
		if (!listResMenuItems.isEmpty()) {
			Integer defaultId = listResMenuItems.get(0).getObject_id();
			
			session.setAttribute(ATTR_CONTENT, 
					paramService.findContentByMenuObjectId(defaultId));
			session.setAttribute(ATTR_CONTENT_OBJECTS, 
					adminToolService.getContentObjectsByMenuObjectId(defaultId));
		}
		
		return SHOW_PAGE;
	}
}
