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
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }">
			<c:choose>
				<c:when test="${sessionScope.currentUser.userType == 'Admin'}">
					<h1 class="text-center">Manage Orders</h1>
					<div class="card-columns p-3 d-flex justify-content-center flex-wrap align-content-center">
						<c:forEach items="${requestScope.allOrders }" var="order">
							<div class="p-3 d-flex justify-content-center flex-wrap align-content-center">
								<div class="card text-center p-3" style="width: 30rem;">
								  <div class="card-body">
								    <h4 class="card-title">Order #${order.order_id}</h4>
								    <h6 class="card-subtitle mb-2 text-muted">Customer: ${order.orderedBy.username }</h6>
								    <h5 class="card-subtitle mb-2 text-muted">
								    <c:choose>
								    	<c:when test="${order.orderStatus == 'Preparing' }">
								    		<span class="badge badge-warning">${order.orderStatus }</span>
								    	</c:when>
								    	<c:otherwise>
								    		<span class="badge badge-success">${order.orderStatus }</span>
								    	</c:otherwise>
								    </c:choose>
											    	
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
										<c:forEach items="${order.foodList }" var="food">
							
												<tr>
												  <td>${food.name }</td>
												  <td>$${food.price }</td>
												  <td></td>
												</tr>							  
										 </c:forEach>
										 <c:forEach items="${order.discountList }" var="discount">
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
											 	<h5><span class="badge badge-success">$${order.price }</span></h5>
											 </td>
				 							 <td >
												
											 </td>
										 </tr>	
										 <tr>
							
											 <td >
					 							<div>
													<form method="POST" action="changeOrderStatus">
												    	<div class="input-group">							    
												    		<select name="orderStatus" class="custom-select">
														        <option value="Preparing">Preparing</option>
														        <option value="Ready">Ready</option>
															</select>
															<input type="hidden" value="${order.order_id }" name="orderId">	
															<input type="submit" value="Change Status" class="btn btn-success">					    
												    	</div>						    								    	
												    </form>
												</div>
											 </td>
											 <td >
												<form method="POST" action="deleteOrder">
													<input type="hidden" value="${order.order_id }" name="deleteId">
													<input type="submit" value="Delete" class="btn btn-danger">						    						
												</form>
											 </td>
				 							 <td >
												
											 </td>
										 </tr>				 
									  </tbody>
									</table>				    
								  </div>
								</div>
							</div>															
						</c:forEach>
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

