package com.hripsite.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hripsite.entity.SiteObject;
import com.hripsite.entity.SiteParameter;
import com.hripsite.service.SiteObjectService;
import com.hripsite.service.SiteParameterService;

import static com.hripsite.controller.MainControllerEnvironment.*;

@Controller
public class MainController {
	@Autowired
	SiteObjectService objectService;
	
	@Autowired
	SiteParameterService paramService;
	
	@RequestMapping(value="show.html", method=RequestMethod.GET)
	public String show(HttpServletRequest request) {
		try {
			Integer action_id = Integer.parseInt(request.getParameter(PARAM_ACTIONID));
			HttpSession session = request.getSession();
			
			if (session.getAttribute(ATTR_MENUITEMS) == null) {
				return index(request);
			}
			
			List<SiteParameter> listContentParams = paramService.findContentByMenuObjectId(action_id);
			session.setAttribute(ATTR_CONTENT, listContentParams);
			
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
		session.removeAttribute(ATTR_CONTENT);
		
		return SHOW_PAGE;
	}
}
