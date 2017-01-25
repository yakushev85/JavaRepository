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
<table>
<caption>Materials</caption>
<tr>
<th>ID</th>
<th>Material name</th>
<th>Author</th>
<th>Date</th>
<th></th>
</tr>

<jstl:forEach items="${materialsitems}" var="materialitem">
<tr>
<td><jstl:out value="${materialitem.object_id}"/></td>

<td><jstl:out value="${materialitem.name}"/></td>

<td>
<jstl:forEach items="${materialauthors}" var="authoritem">
<jstl:if test="${materialitem.object_id eq authoritem.id}">
<jstl:out value="${authoritem.value}"/>
</jstl:if>
</jstl:forEach>
</td>

<td>
<jstl:forEach items="${materialdates}" var="dateitem">
<jstl:if test="${materialitem.object_id eq dateitem.id}">
<jstl:out value="${dateitem.value}"/>
</jstl:if>
</jstl:forEach>
</td>

<td>
<a href="editmaterial.html?id=${materialitem.object_id}">Edit</a>
<a href="delmaterial.html?id=${materialitem.object_id}">Delete</a>
</td>
</tr>
</jstl:forEach>

<tr>
<td></td>
<td></td>
<td></td>
<td></td>
<td>
<a href="newmaterial.html">New</a>
</td>
</tr>

</table>
</div>
</div>

<div id="end_text">
<p>Created by Alex@2014.</p>
</div>

</body>
</html>