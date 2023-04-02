<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Maccaz</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }"> 
			<ul class="nav justify-content-center shadow p-3 mb-5 bg-light rounded">
			  <li class="nav-item">
			    <a class="nav-link active" href="/SpringApp">Home</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link" href="getOrderPage">Make Order</a>
			  </li>
		  	  <li class="nav-item">
			    <a class="nav-link" href="getUserOrdersPage">My Orders</a>
			  </li>
			  <c:if test="${sessionScope.currentUser.userType == 'Admin'}">
				  <li class="nav-item dropdown">
				    <a class="nav-link dropdown-toggle" data-toggle="dropdown"
				       href="#" role="button" aria-haspopup="true" aria-expanded="false">
				       Admin Tools</a>
				    <div class="dropdown-menu">
				      <a class="dropdown-item" href="getManageOrderPage">Manage Order</a>
				      <a class="dropdown-item" href="getManageFoodPage">Manage Menu</a>
				      <a class="dropdown-item" href="getManageDiscountPage">Manage Discounts</a>
				    </div>
				  </li>
			  </c:if>
			  <li class="nav-item">
			    <a class="nav-link disabled">${sessionScope.currentUser.username }</a>
			  </li>
		   	  <li class="nav-item">
			    <a class="nav-link" href="logoutUser">Logout</a>
			  </li>
		
			</ul>
		</c:when>
		<c:otherwise>
			<ul class="nav justify-content-center shadow p-3 mb-5 bg-light rounded">
			  <li class="nav-item">
			    <a class="nav-link active" href="/SpringApp">Home</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link active" href="getRegister">Register</a>
			  </li>
			  <li class="nav-item">
			    <a class="nav-link" href="getLogin">Login</a>
			  </li>
		  </ul>
		</c:otherwise>
	</c:choose>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>	
</body>
</html>