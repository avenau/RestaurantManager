<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<style>
.login-form {
    width: 340px;
    margin: 50px auto;
  	font-size: 15px;
}
.login-form form {
    margin-bottom: 15px;
    background: #f7f7f7;
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    padding: 30px;
}
.login-form h2 {
    margin: 0 0 15px;
}
.form-control, .btn {
    min-height: 38px;
    border-radius: 2px;
}
.btn {        
    font-size: 15px;
    font-weight: bold;
}
</style>
</head>
<body>
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${ not empty sessionScope.currentUser }"> 
			<h1>You have already logged in!</h1>
		</c:when>
		<c:otherwise>
			<div class="login-form">
				<form action="registerUser" method="POST">
					<h2 class="text-center">Register</h2>
		     		<div class="form-group">
	       				<input type="text" name="username" class="form-control" id="usernameTextbox" placeholder="Username">
		     		</div>
		     		<div class="form-group">
		      			<div class="form-group">
		       				<input type="password" name="password" class="form-control" id="passwordTextBox" placeholder="Password">
		     		 	</div>
		     		</div>
		     		<div class="form-group">
		      			<div class="form-group">
		       				<input type="password" name="confirmpassword" class="form-control" id="confirmPasswordTextBox" placeholder="Confirm password">
		     		 	</div>
		     		</div>
					<button type="submit" value="register" class="btn btn-primary btn-block">Register</button>
					<c:if test="${not empty requestScope.errorMessage }">
						<p>${requestScope.errorMessage} </p>
					</c:if>
				</form>
				<p class="text-center"><a href="getLogin">Login to your Account</a></p>
			</div>

		</c:otherwise>
	</c:choose>

</body>
</html>