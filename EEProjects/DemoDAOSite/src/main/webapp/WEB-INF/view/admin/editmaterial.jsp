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
				<form method="post" action="savematerial.html">
					<input type="hidden" name="materialid" value="${material.object_id}">
					<p>Name:<input type="text" name="name" size=60 value="${material.name}"></p>
					<p>Author:<input type="text" name="author" size=60 value="${author}"></p>
					<p>Content:</p>
					<p>
						<input type="button" value="P" onClick="addTagToTextArea('textarea_content', 'P', 'P');">
						<input type="button" value="B" onClick="addTagToTextArea('textarea_content', 'B', 'B');">
						<input type="button" value="U" onClick="addTagToTextArea('textarea_content', 'U', 'U');">
						<input type="button" value="I" onClick="addTagToTextArea('textarea_content', 'I', 'I');">
						<input type="button" value="A" onClick="addTagToTextArea('textarea_content', 'A href=URL', 'A');">
						<input type="button" value="IMG" onClick="addVoidTagToTextArea('textarea_content', 'IMG src=URL');">
					</p>
					<p>
						<textarea rows=25 cols=80 maxlength=1000 name="content" id="textarea_content">
							<jstl:out value="${content}"/>
						</textarea>
					</p>
					<p>
						<input type="submit" value="OK"> <input type="button" value="Cancel" onClick="location.href='showmaterials.html'">
					</p>
				</form>
			</div>
		</div>
		
		<div id="end_text">
		<p>Created by Alex@2014.</p>
		</div>
	
	</body>
</html>