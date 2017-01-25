package com.yakushev.site.controller;

import static com.yakushev.site.controller.AdminControllerEnvironment.*;
import static com.yakushev.site.controller.SecurityAccountTool.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yakushev.site.service.AdminToolService;

@Controller
public class AdminAccountController {
	@Autowired
	AdminToolService adminToolService;
	
	/* Administrator account */
	
	@RequestMapping(value="admin/editadminaccount.html", method=RequestMethod.GET)
	public String editAdminAccount(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
		}
		
		String adminUserName = adminToolService.getAdminUserName();
		
		session.setAttribute(ATTR_ADMINUSERNAME, adminUserName);
		
		return EDITADMINACCOUNT_PAGE;
	}
	
	@RequestMapping(value="admin/saveadminaccount.html", method=RequestMethod.POST)
	public String saveAdminAccount(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (!isLogin(session)) {
			return REDIRECT_LOGIN;
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
		
		return REDIRECT_SHOWMENUITEMS;
	}

}
