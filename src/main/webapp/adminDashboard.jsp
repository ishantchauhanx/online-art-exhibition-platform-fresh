<%@ page import="com.artexhibit.model.User" %>
<%@ page import="com.artexhibit.model.Exhibition" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User sessionUser = (User) session.getAttribute("user");
    if (sessionUser == null || !"ADMIN".equalsIgnoreCase(sessionUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    List<Exhibition> pending = (List<Exhibition>) request.getAttribute("pendingExhibitions");
    List<User> users = (List<User>) request.getAttribute("users");
%>
<html>
<head>
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Admin Panel</a>
        <div class="ms-auto">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">Logout</a>
        </div>
    </div>
</nav>

<div class="container py-4">
    <h3>Welcome, <%= sessionUser.getName() %></h3>

    <h4 class="mt-4">User Accounts</h4>
    <table class="table table-striped table-bordered">
        <thead>
        <tr><th>Name</th><th>Email</th><th>Role</th></tr>
        </thead>
        <tbody>
        <% if (users != null) { for (User u : users) { %>
        <tr>
            <td><%= u.getName() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getRole() %></td>
        </tr>
        <% } } %>
        </tbody>
    </table>

    <h4 class="mt-4">Pending Exhibition Requests</h4>
    <% if (pending != null && !pending.isEmpty()) { %>
    <% for (Exhibition ex : pending) { %>
    <div class="card mb-3">
        <div class="card-body">
            <h5><%= ex.getTitle() %></h5>
            <p><%= ex.getDescription() %></p>
            <p><strong>Dates:</strong> <%= ex.getStartDate() %> to <%= ex.getEndDate() %></p>
            <form action="${pageContext.request.contextPath}/admin/approval" method="post" class="d-flex gap-2">
                <input type="hidden" name="exhibitId" value="<%= ex.getExhibitId() %>">
                <button type="submit" name="action" value="APPROVED" class="btn btn-success">Approve</button>
                <button type="submit" name="action" value="REJECTED" class="btn btn-danger">Reject</button>
            </form>
        </div>
    </div>
    <% } %>
    <% } else { %>
    <div class="alert alert-info">No pending exhibitions.</div>
    <% } %>
</div>
</body>
</html>
