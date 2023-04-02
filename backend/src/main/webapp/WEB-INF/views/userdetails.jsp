<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Details</title>
</head>
<body>
	<h1>User Details</h1>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }">
			<h2>${sessionScope.currentUser.username }</h2>
			<br>
			<p>Password: ${sessionScope.currentUser.password }</p>

		</c:when>
		<c:otherwise><p>You have not logged in yet</p></c:otherwise>
	</c:choose>
	
	<a href="/SpringApp">Click here to return home!</a>

</body>
</html>l>