package com.artexhibit.controller;

import com.artexhibit.dao.FeedbackDAO;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Feedback;
import com.artexhibit.model.User;
import com.artexhibit.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String artworkIdStr = request.getParameter("artworkId");
        String ratingStr = request.getParameter("rating");
        String comment = ValidationUtil.trimToNull(request.getParameter("comment"));

        if (artworkIdStr == null || ratingStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Artwork ID and rating are required.");
            return;
        }

        try {
            int artworkId = Integer.parseInt(artworkIdStr);
            int rating = Integer.parseInt(ratingStr);

            if (rating < 1 || rating > 5) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Rating must be between 1 and 5.");
                return;
            }

            Feedback feedback = new Feedback();
            feedback.setUserId(user.getUserId());
            feedback.setArtworkId(artworkId);
            feedback.setRating(rating);
            feedback.setComment(comment == null ? "" : comment);

            new FeedbackDAO().submitFeedback(feedback);
            response.sendRedirect(request.getContextPath() + "/gallery");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric value.");
        } catch (DatabaseException e) {
            throw new ServletException("Error submitting feedback.", e);
        }
    }
}
