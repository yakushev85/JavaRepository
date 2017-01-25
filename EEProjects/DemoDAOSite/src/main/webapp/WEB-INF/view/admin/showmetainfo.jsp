<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Administrator page</title>
		<link rel="stylesheet" type="text/css" href="<jstl:url value="/resources/admin.css"/>"/>
		<script src="<jstl:url value="/resources/admin_lib.js"/>"></script>
	</head>
	<body>
		
		<div id="main_head">
			<a href="#">Administrator page</a>
		</div>
		
		<div id="main_text">
			<div id="main_text_left">
				<div id="head_menu">
					<jsp:include page="menu.jsp" />
				</div>
			</div>
			
			<div id="main_text_right">
				<p>
					<table>
						<caption>Attributes</caption>
						<tr>
							<th>ID</th>
							<th>Name</th>
							<th>Description</th>
						</tr>
						
						<jstl:forEach items="${allatributes}" var="attritem">
							<tr>
								<td><jstl:out value="${attritem.attribute_id}"/></td>
								
								<td><jstl:out value="${attritem.name}"/></td>
								
								<td><jstl:out value="${attritem.description}"/></td>
							</tr>
						</jstl:forEach>
					</table>
				</p>
				
				<p>
				<table>
					<caption>Object types</caption>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Description</th>
					</tr>
					
					<jstl:forEach items="${allobjecttypes}" var="otitem">
						<tr>
							<td><jstl:out value="${otitem.object_type_id}"/></td>
							
							<td><jstl:out value="${otitem.name}"/></td>
							
							<td><jstl:out value="${otitem.description}"/></td>
						</tr>
					</jstl:forEach>
				</table>
				</p>
				
			</div>
		</div>
		
		<div id="end_text">
			<p>Created by Alex@2014.</p>
		</div>
		
	</body>
</html>