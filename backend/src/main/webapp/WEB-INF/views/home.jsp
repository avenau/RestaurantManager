<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<title>Maccaz</title>
</head>
<body>
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }"> 	
			<div class="jumbotron text-center">
			  <h1 class="display-3"><strong>Welcome Back ${sessionScope.currentUser.username }!</strong></h1>
			  <hr class="my-2">
			  <p></p>
			  <br>
			  <p class="lead">
			    <a class="btn btn-primary btn-lg" href="getOrderPage" role="button">Make An Order</a>
			  </p>
			</div>
		</c:when>
		<c:otherwise>	
			<div class="jumbotron text-center">
			  <h1 class="display-3"><strong>Welcome to Burgers!</strong></h1>
			  <hr class="my-2">
			  <p></p>
			  <p class="lead">
			    <a class="btn btn-primary btn-lg" href="getRegister" role="button">Signup</a>
			    <a class="btn btn-primary btn-lg" href="getLogin" role="button">Login</a>
			  </p>
			</div>
		</c:otherwise>
	</c:choose>

</body>
</html>

