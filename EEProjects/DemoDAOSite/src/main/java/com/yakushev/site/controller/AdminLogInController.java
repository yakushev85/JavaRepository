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
public class AdminLogInController {
	private int attemptsToLogin = MAX_ATTEMPTS_TOLOGIN;
	
	@Autowired
	AdminToolService adminToolService;
	
	/* login and show */
	@RequestMapping(value="admin/index.html")
	public String index(HttpServletRequest request) {
		return login(request);
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
			setLoginAttribute(session);
			session.removeAttribute(ATTR_ERRORMSG);
			attemptsToLogin = MAX_ATTEMPTS_TOLOGIN;
			return REDIRECT_SHOWMENUITEMS;
		} else {
			session.setAttribute(ATTR_ERRORMSG, MSG_WRONGPASS);
			attemptsToLogin--;
		}
		
		return login(request);
	}

}
