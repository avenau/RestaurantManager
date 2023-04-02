<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Confirmation</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }">
			<h1 class="text-center">Order Confirmation</h1>
			<div class="p-3 d-flex justify-content-center flex-wrap align-content-center">
				<div class="card text-center p-3" style="width: 25rem;">
				  <div class="card-body">
				    <h4 class="card-title">Order #${requestScope.currentOrder.order_id}</h4>
				    <h5 class="card-subtitle mb-2 text-muted">
					<span class="badge badge-warning">${requestScope.currentOrder.orderStatus }</span>		    	
				    </h5>
					<table class="table p-3 text-center table-striped">
					  <thead class="thead-dark">
					    <tr>
					      <th>Name</th>
					      <th>Price</th>
					      <th></th>
					    </tr>
					  </thead>
					  <tbody>					
						<c:forEach items="${requestScope.currentOrder.foodList }" var="food">
			
								<tr>
								  <td>${food.name }</td>
								  <td>$${food.price }</td>
								  <td></td>
								</tr>							  
						 </c:forEach>
						 <c:forEach items="${requestScope.currentOrder.discountList }" var="discount">
							<tr>
							  <td class="table-success">${discount.discountCode } (Discount)</td>
							  <td class="table-success"></td>
							  <td class="table-success"></td>
							</tr>		
						</c:forEach>
						 <tr>
			
							 <td >
								<strong>Total Price:</strong>
							 </td>
							 <td >
							 	<h5><span class="badge badge-success">$${requestScope.currentOrder.price }</span></h5>
							 </td>
 							 <td >
								
							 </td>
						 </tr>					 
					  </tbody>
					</table>				    
				    <div class="row">
				     							
							<div class="col-sm">	
						    <c:if test="${requestScope.currentOrder.orderStatus == 'Preparing' }">
								<form method="POST" action="deleteMyOrder">
									<input type="hidden" value="${requestScope.currentOrder.order_id }" name="deleteId">
									<input type="submit" value="Delete" class="btn btn-danger">						    						
								</form>
							</c:if>
							
						</div>
						<div class="col-sm">	
							<a type="button" href="getOrderPage" class="btn btn-primary">Make Another Order</a>
						</div>
					</div>
				  </div>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<h1>You need to login to do this!</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>