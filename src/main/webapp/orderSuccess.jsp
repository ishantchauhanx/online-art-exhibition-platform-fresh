<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order Placed</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="alert alert-success text-center">
        <h3>Order placed successfully!</h3>
        <p>Your artwork purchase was recorded successfully.</p>
        <a href="${pageContext.request.contextPath}/gallery" class="btn btn-primary">Continue Shopping</a>
    </div>
</div>
</body>
</html>
