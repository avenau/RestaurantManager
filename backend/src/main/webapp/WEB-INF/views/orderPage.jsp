<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Page</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="js/bootstrap.min.js"></script>
</head>
<body>
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }">
			<h1 class="text-center">Make your Order</h1>
			<div class="shadow p-3 mb-5 bg-light rounded">		
				<table class="table p-3">
				  <thead class="thead-dark">
				    <tr>
				      <th>Name</th>
				      <th>Price</th>
				      <th></th>
				    </tr>
				  </thead>
				  <tbody>					
					<c:forEach items="${sessionScope.order }" var="food">
	
							<tr>
							  <td>${food.name }</td>
							  <td>$${food.price }</td>
							</tr>							  
					 </c:forEach>
					 <c:forEach items="${sessionScope.discounts }" var="discount">
						<tr>
						  <td class="table-success">${discount.discountCode } (Discount)</td>
						  <td class="table-success"></td>
						</tr>		
					</c:forEach>
					 <tr>

						 <td >
							<form method="POST" action="addToOrder" >
								<select name="foods" class="custom-select w-25">
								    <c:forEach var="food" items="${requestScope.allFoods}">
								        <option value="${food.food_id}">${food.name }  $${food.price }</option>
								    </c:forEach>
								</select>
								<input type="submit" class="btn btn-primary" value="Add to Order">
							</form>
							
							<form method="POST" action="addDiscountToOrder" class="form-inline">
								<input type="text" class="form-control mb-2 mt-2 mr-sm-1 w-25" name="discount" placeholder="Discount Code" id="discountBox">
								<input type="submit" class="btn btn-primary" value="Add Discount">
								<c:if test="${not empty requestScope.errorMessage }">
									<div class="alert alert-danger alert-dismissible fade show fixed-top" role="alert">
									  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
									    <span aria-hidden="true">&times;</span>
									  </button>
									  	${requestScope.errorMessage }
									</div>									
								</c:if>
								<br>
							</form>
								
						 	<a type="button" class="btn btn-danger" href="clearOrder">Clear Order</a>
						 	<a href="completeOrder" type="button" class="btn btn-primary">Complete Order</a>
						 </td>
						 <td >
						 	<h4><span class="badge badge-success">$${requestScope.orderPrice }</span></h4>
						 </td>
					 </tr>					 
				  </tbody>
				</table>
			</div>
			
			<br>
			<br>
			
			<c:forEach items="${sessionScope.discounts }" var="discount">
				<p>${discount }</p>
				<br>		
			</c:forEach>		
		</c:when>
		<c:otherwise>
			<h1>You need to login to do this!</h1>
		</c:otherwise>
	</c:choose>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>	
</body>
</html>