<%@ page import="com.artexhibit.model.User" %>
<%@ page import="com.artexhibit.model.Artwork" %>
<%@ page import="com.artexhibit.dao.ArtworkDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User sessionUser = (User) session.getAttribute("user");
    if (sessionUser == null || !"ARTIST".equalsIgnoreCase(sessionUser.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    List<Artwork> artworks = new ArtworkDAO().getArtworksByArtist(sessionUser.getUserId());
%>
<html>
<head>
    <title>Artist Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Art Exhibition</a>
        <div class="ms-auto">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light">Logout</a>
        </div>
    </div>
</nav>

<div class="container py-4">
    <h3>Welcome, <%= sessionUser.getName() %></h3>
    <div class="d-flex gap-2 mt-3">
        <a href="${pageContext.request.contextPath}/uploadArtwork.jsp" class="btn btn-primary">Upload Artwork</a>
        <a href="${pageContext.request.contextPath}/gallery" class="btn btn-secondary">View Gallery</a>
    </div>

    <h4 class="mt-4">My Artworks</h4>
    <div class="row">
        <% for (Artwork artwork : artworks) { %>
        <div class="col-md-4 mt-3">
            <div class="card h-100">
                <img src="<%= artwork.getImageUrl() %>" class="card-img-top" style="height:220px;object-fit:cover;">
                <div class="card-body">
                    <h5><%= artwork.getTitle() %></h5>
                    <p class="text-muted"><%= artwork.getMedium() %></p>
                    <p>₹<%= artwork.getPrice() %></p>
                    <span class="badge bg-success"><%= artwork.getStatus() %></span>
                </div>
            </div>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>
