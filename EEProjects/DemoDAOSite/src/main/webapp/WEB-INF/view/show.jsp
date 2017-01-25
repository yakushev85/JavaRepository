<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>Demo Site with jQuery</title>
		<link href="<jstl:url value="/resources/jquery-ui.css"/>" rel="stylesheet">
		<link href="<jstl:url value="/resources/main.css"/>" rel="stylesheet">
		<script src="<jstl:url value="/resources/jquery.js"/>"></script>
		<script src="<jstl:url value="/resources/jquery-ui.js"/>"></script>
	</head>
	<body>
		<div id="mainhead">
			<p>Demo Site</p>
		</div>
		
		<div id="mainobjs">
			
			<div id="leftobjs">
				<ul id="somemenu">
					<jstl:forEach items="${menuitems}" var="menuitem">
						<li><a href="show.html?actionid=${menuitem.object_id}">
						<jstl:out value="${menuitem.data_value}"/>
						</a></li>
					</jstl:forEach>
				</ul>
			</div>
			
			
			<div id="centerobjs">
				<jstl:if test="${not empty content}">
					<div id="sometabs">
						<ul>
							<jstl:forEach items="${contentobjects}" var="contobjitem">
								<li><a href="#sometabs-${contobjitem.object_id}">${contobjitem.name}</a></li>
							</jstl:forEach>
						</ul>
			
						<jstl:forEach items="${contentobjects}" var="contobjitem">
							<jstl:forEach items="${content}" var="contitem">
								<jstl:if test="${contobjitem.object_id eq contitem.object_id}">
									<div id="sometabs-${contobjitem.object_id}">${contitem.data_value}</div>
								</jstl:if>
							</jstl:forEach>					
						</jstl:forEach>	
					</div>
				</jstl:if>
			</div>
		
			<div id="rightobjs">
				<div id="datechooser"></div>
			</div>
		</div>
	</body>
	<script>
		$("#somemenu").menu();
		$("#sometabs").tabs();
		$("#datechooser").datepicker({inline: true});
	</script>
</html>