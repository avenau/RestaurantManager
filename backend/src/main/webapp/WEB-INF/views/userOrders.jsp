<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Orders</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
	<%@ include file="navbar.jsp" %>
	<c:choose>
		<c:when test="${not empty sessionScope.currentUser }">
			<h1 class="text-center">My Orders</h1>

			<div class="card-columns p-3 d-flex justify-content-center flex-wrap align-content-center">
				<c:forEach items="${requestScope.userOrders }" var="order">
					<div class="card text-center p-3" style="width: 18rem;">
					  <div class="card-body">
					    <h4 class="card-title">Order #${order.order_id }</h4>
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
					    
					    <p class="card-text">
					     	Price: $${order.price }
					    </p>
					    <div class="row">
					     							
	 						<div class="col-sm">	
							    <c:if test="${order.orderStatus == 'Preparing' }">
									<form method="POST" action="deleteMyOrder">
										<input type="hidden" value="${order.order_id }" name="deleteId">
										<input type="submit" value="Delete" class="btn btn-danger">						    						
									</form>
								</c:if>
							</div>
						</div>
						

					  </div>
					</div>	
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<h1>You need to login to do this!</h1>
		</c:otherwise>
	</c:choose>

</body>
</html>