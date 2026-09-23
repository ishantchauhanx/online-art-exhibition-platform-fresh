package com.artexhibit.controller;

import com.artexhibit.dao.ArtworkDAO;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Artwork;
import com.artexhibit.model.User;
import com.artexhibit.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/artist/upload-artwork")
public class ArtworkUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"ARTIST".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            return;
        }

        String title = ValidationUtil.trimToNull(request.getParameter("title"));
        String description = ValidationUtil.trimToNull(request.getParameter("description"));
        String medium = ValidationUtil.trimToNull(request.getParameter("medium"));
        String priceString = ValidationUtil.trimToNull(request.getParameter("price"));
        String imageUrl = ValidationUtil.trimToNull(request.getParameter("imageUrl"));

        if (ValidationUtil.isBlank(title) || ValidationUtil.isBlank(medium)
                || ValidationUtil.isBlank(priceString) || ValidationUtil.isBlank(imageUrl)) {
            request.setAttribute("error", "Title, medium, price, and image URL are required.");
            request.getRequestDispatcher("/uploadArtwork.jsp").forward(request, response);
            return;
        }

        try {
            BigDecimal price = new BigDecimal(priceString);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                request.setAttribute("error", "Price must be greater than zero.");
                request.getRequestDispatcher("/uploadArtwork.jsp").forward(request, response);
                return;
            }

            Artwork artwork = new Artwork();
            artwork.setArtistId(user.getUserId());
            artwork.setTitle(title);
            artwork.setDescription(description == null ? "" : description);
            artwork.setMedium(medium);
            artwork.setPrice(price);
            artwork.setImageUrl(imageUrl);
            artwork.setStatus("AVAILABLE");

            new ArtworkDAO().saveArtwork(artwork);
            response.sendRedirect(request.getContextPath() + "/artistDashboard.jsp");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Price must be a valid number.");
            request.getRequestDispatcher("/uploadArtwork.jsp").forward(request, response);
        } catch (DatabaseException e) {
            throw new ServletException("Database error while uploading artwork.", e);
        }
    }
}
