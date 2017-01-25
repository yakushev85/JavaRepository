<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Administrator page</title>
<link rel="stylesheet" type="text/css" href="<jstl:url value="/resources/admin.css"/>"/>
</head>
<body>

<div id="main_head">
<a href="#">Administrator page</a>
</div>

<div id="login_form">
<form method="post" action="show.html">
<table>
<tr><td>User:</td><td><input type="text" name="user" size=40></td></tr>
<tr><td>Password:</td><td><input type="password" name="password" size=40></td></tr>
<tr><td></td><td><input type="submit" value="Login"></td></tr>
</table>
</form>
<p><jstl:out value="${errormsg}"/></p>
</div>

<div id="end_text">
<p>Created by Alex@2014.</p>
</div>

</body>
</html>