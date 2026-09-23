<%@ page import="com.artexhibit.model.Artwork" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Artwork> artworks = (List<Artwork>) request.getAttribute("artworks");
%>
<html>
<head>
    <title>Gallery</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Virtual Gallery</a>
        <div class="ms-auto">
            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-outline-light">Login</a>
        </div>
    </div>
</nav>

<div class="container py-4">
    <h3 class="mb-4">Available Artworks</h3>
    <div class="row">
        <% if (artworks != null) { for (Artwork artwork : artworks) { %>
        <div class="col-md-4 mb-4">
            <div class="card h-100">
                <img src="<%= artwork.getImageUrl() %>" class="card-img-top" style="height:240px;object-fit:cover;">
                <div class="card-body">
                    <h5><%= artwork.getTitle() %></h5>
                    <p class="text-muted"><%= artwork.getMedium() %></p>
                    <p><%= artwork.getDescription() %></p>
                    <h5>₹<%= artwork.getPrice() %></h5>
                    <form action="${pageContext.request.contextPath}/checkout" method="post">
                        <input type="hidden" name="artworkId" value="<%= artwork.getArtId() %>">
                        <button type="submit" class="btn btn-primary">Buy Now</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/feedback" method="post" class="mt-3">
                        <input type="hidden" name="artworkId" value="<%= artwork.getArtId() %>">
                        <div class="input-group">
                            <select name="rating" class="form-select">
                                <option value="5">5 Stars</option>
                                <option value="4">4 Stars</option>
                                <option value="3">3 Stars</option>
                                <option value="2">2 Stars</option>
                                <option value="1">1 Star</option>
                            </select>
                            <input type="text" name="comment" class="form-control" placeholder="Comment">
                            <button type="submit" class="btn btn-outline-secondary">Rate</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <% } } %>
    </div>
</div>
</body>
</html>
