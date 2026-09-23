package com.artexhibit.controller;

import com.artexhibit.dao.ArtworkDAO;
import com.artexhibit.dao.OrderDAO;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Artwork;
import com.artexhibit.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"ENTHUSIAST".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only art enthusiasts can purchase artwork.");
            return;
        }

        String artIdRaw = request.getParameter("artworkId");
        if (artIdRaw == null || artIdRaw.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Artwork ID is required.");
            return;
        }

        try {
            int artworkId = Integer.parseInt(artIdRaw);
            ArtworkDAO artworkDAO = new ArtworkDAO();
            Artwork artwork = artworkDAO.getArtworkById(artworkId);

            if (artwork == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Artwork not found.");
                return;
            }

            if (!"AVAILABLE".equalsIgnoreCase(artwork.getStatus())) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "This artwork is no longer available.");
                return;
            }

            new OrderDAO().placeOrder(user.getUserId(), artworkId, artwork.getPrice());
            artworkDAO.markAsSold(artworkId);
            response.sendRedirect(request.getContextPath() + "/orderSuccess.jsp");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid artwork ID.");
        } catch (DatabaseException e) {
            throw new ServletException("Checkout failed.", e);
        }
    }
}
