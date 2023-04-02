<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Food</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }">
			<c:choose>
				<c:when test="${sessionScope.currentUser.userType == 'Admin'}">
					<h1>Manage Menu</h1>
					<div class="shadow p-3 mb-5 bg-light rounded">
						<form method="POST" action="deleteFood">
							<table class="table">
							  <thead class="thead-dark">
							    <tr>
							      <th>#</th>
							      <th>Name</th>
							      <th>Price</th>
							      <th></th>
							    </tr>
							  </thead>
							  <tbody>					
								<c:forEach items="${requestScope.allFoods }" var="food">
		 
										<tr>
										  <th scope="row">${food.food_id }</th>
										  <td>${food.name }</td>
										  <td>${food.price }</td>
										  <td >
												<input type="hidden" value="${food.food_id }" name="foodId">	
												<input type="submit" value="Delete" class="btn btn-danger">
										  </td>
										</tr>							  
								 </c:forEach>
								 <tr>
									 <td >
									 </td>
									 <td >
									 </td>
									 <td >
									 </td>
									 <td >
									 </td>
								 </tr>					 
							  </tbody>
							</table>
						</form>
						<div>
							<form class="form-inline" action="addFood" method="POST">
							  <label class="sr-only" for="foodNameTextbox">Food Name: </label>
							  <input type="text" class="form-control mb-2 mr-sm-2" name="name" id="foodNameTextbox" placeholder="Food Name">
							  
							  <label class="sr-only" for="foodPriceTextBox">Food Price: </label>
							  <input type="number" step="0.00001" class="form-control mb-2 mr-sm-2" name="price" id="foodPriceTextbox" placeholder="Food Price">					
							
							  <button type="submit" class="btn btn-primary mb-2">Add Food</button>
							</form>	
						</div>	
																
					</div>
				</c:when>
				<c:otherwise>
					<h1>You do not have permissions for this!</h1>
				</c:otherwise>		
			</c:choose>	
		</c:when>
		<c:otherwise>
			<h1>You need to login to do this!</h1>
		</c:otherwise>
	</c:choose>

</body>
</html>