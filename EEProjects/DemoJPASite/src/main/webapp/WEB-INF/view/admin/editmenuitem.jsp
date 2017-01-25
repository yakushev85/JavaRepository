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

<div id="main_text">
<div id="main_text_left">
<div id="head_menu">
<ul>
<li><a href="showmenuitems.html">Menu items</a></li>
<li><a href="showmaterials.html">Materials</a></li>
<li><a href="editadminaccount.html">Edit admin account</a></li>
<li><a href="login.html">Logout</a></li>
<ul>
</div>
</div>

<div id="main_text_right">

<form method="post" action="savemenuitem.html">
<input type="hidden" name="menuitemid" value="${editmenuitem.object_id}">
<p>Name:<input type="text" name="internalname" size=40 value="${editmenuitem.name}"></p>
<p>Title:<input type="text" name="name" size=60 value="${editmenuname}"></p>

<p>Using materials:</p>

<jstl:forEach items="${materialsbymenuitem}" var="materialitem">
<p>
<a href="editmaterial.html?id=${materialitem.object_id}">
<jstl:forEach items="${materialsitems}" var="objitem">
<jstl:if test="${objitem.object_id eq materialitem.object_id}">
<jstl:out value="${objitem.name}"/>
</jstl:if>
</jstl:forEach>
</a>
 - <a href="dellinkmaterial.html?menuid=${editmenuitem.object_id}&materialid=${materialitem.object_id}">
 Delete link to material
 </a>
 </p>
</jstl:forEach>

<p>New material:
<select size=1 name="newmaterialid">
<option selected value="0"></option>
<jstl:forEach items="${materialsitems}" var="objitem">
<option value="${objitem.object_id}">
<jstl:out value="${objitem.name}"/>
</option>
</jstl:forEach>

</select>
</p>

<p>Order:<input type="text" size=10 name="order" value="${editmenuitem.order_num}"></p>
<p><input type="submit" value="OK"> <input type="button" value="Cancel" onClick="location.href='showmenuitems.html'"></p>
</form>

<div id="end_text">
<p>Created by Alex@2014.</p>
</div>

</body>
</html>