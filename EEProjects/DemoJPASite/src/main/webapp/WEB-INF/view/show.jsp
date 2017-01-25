<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
    
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Simple html</title>
<link rel="stylesheet" type="text/css" href="<jstl:url value="/resources/main.css"/>"/>
</head>
<body>

<div id="main_head">
<a href="index.html">Simple site!</a>
</div>

<div id="main_text">
<div id="main_text_left">
<div id="head_menu">
<ul>
<jstl:forEach items="${menuitems}" var="menuitem">
<li><a href="show.html?actionid=${menuitem.object_id}">
<jstl:out value="${menuitem.data_value}"/>
</a></li>
</jstl:forEach>
<ul>
</div>
</div>

<jstl:if test="${not empty content}">
<div id="main_text_right">
<jstl:forEach items="${content}" var="contitem">
<p>
${contitem.data_value}
</p>
</jstl:forEach>
</div>
</div>
</jstl:if>

<jstl:if test="${empty content}">
<div id="main_text_right_noborder">
</div>
</jstl:if>

<div id="end_text">
<p>Created by Alex@2014.</p>
</div>

</body>
</html>