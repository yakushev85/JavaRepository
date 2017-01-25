package com.hripsite.controller;

public class AdminControllerEnvironment {
	public static int MAX_ATTEMPTS_TOLOGIN = 5;
	
	public static String LOGIN_PAGE = "admin/login";
	public static String SHOWMENUITEMS_PAGE = "admin/showmenuitems";
	public static String SHOWMATERIALS_PAGE = "admin/showmaterials";
	public static String EDITMATERIAL_PAGE = "admin/editmaterial";
	public static String EDITMENUITEM_PAGE = "admin/editmenuitem";
	public static String EDITADMINACCOUNT_PAGE = "admin/editadminaccount";
	
	public static String ATTR_ERRORMSG = "errormsg";
	public static String ATTR_ISLOGGED = "islogged";
	public static String ATTR_MENUITEMS = "menuitems";
	public static String ATTR_EDITMENUITEM = "editmenuitem";
	public static String ATTR_EDITMENUNAME = "editmenuname";
	public static String ATTR_NAMEMENUITEMS = "namemenuitems";
	public static String ATTR_MATERIALITEMS = "materialsitems";
	public static String ATTR_MATERIALAUTHORS = "materialauthors";
	public static String ATTR_MATERIALDATES = "materialdates";
	public static String ATTR_MATERIALSBYMENUITEM = "materialsbymenuitem";
	public static String ATTR_MATERIAL = "material";
	public static String ATTR_AUTHOR = "author";
	public static String ATTR_CONTENT = "content";
	public static String ATTR_ERRORADMINACCOUNT = "erroradminaccount";
	
	public static String PARAM_USER = "user";
	public static String PARAM_PASSWORD = "password";
	public static String PARAM_ID = "id";
	public static String PARAM_MATERIALID = "materialid";
	public static String PARAM_MENUID = "menuid";
	public static String PARAM_NAME = "name";
	public static String PARAM_INTERNALNAME = "internalname";
	public static String PARAM_AUTHOR = "author";
	public static String PARAM_CONTENT = "content";
	public static String PARAM_MENUITEMID = "menuitemid";
	public static String PARAM_NEWMATERIALID = "newmaterialid";
	public static String PARAM_ORDER = "order";
	public static String PARAM_OLDPASSWORD = "oldpassword";
	public static String PARAM_NEWPASSWORD = "newpassword";
	public static String PARAM_REPEATPASSWORD = "repeatpassword";
	
	public static String MSG_WRONGPASS = "Wrong user or password!!!";
	public static String MSG_NULLATTEMPTS = "You have exceeded the number of attempts to enter the user name and password.";
	public static String MSG_WRONGOLDPASS = "Wrong old password!";
	public static String MSG_WRONGREPATPASS = "Not equals value of fields new password and repeat password!";
}
