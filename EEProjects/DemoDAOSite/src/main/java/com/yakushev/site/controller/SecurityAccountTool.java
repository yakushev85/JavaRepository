package com.yakushev.site.controller;

import javax.servlet.http.HttpSession;

import static com.yakushev.site.controller.AdminControllerEnvironment.*;

public class SecurityAccountTool {
	public static boolean isLogin(HttpSession session) {
		return session.getAttribute(ATTR_ISLOGGED) != null;
	}
	
	public static void setLoginAttribute(HttpSession session) {
		session.setAttribute(ATTR_ISLOGGED, true);
	}
}
