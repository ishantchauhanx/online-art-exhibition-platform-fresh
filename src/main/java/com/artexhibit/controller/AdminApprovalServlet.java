package com.artexhibit.controller;

import com.artexhibit.dao.ExhibitionDAO;
import com.artexhibit.dao.UserDAO;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Exhibition;
import com.artexhibit.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/approval")
public class AdminApprovalServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only admin can access this page.");
            return;
        }

        try {
            ExhibitionDAO exhibitionDAO = new ExhibitionDAO();
            UserDAO userDAO = new UserDAO();

            List<Exhibition> pendingExhibitions = exhibitionDAO.getPendingExhibitions();
            request.setAttribute("pendingExhibitions", pendingExhibitions);
            request.setAttribute("users", userDAO.getAllUsers());
            request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
        } catch (DatabaseException e) {
            throw new ServletException("Error loading admin dashboard.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String exhibitIdRaw = request.getParameter("exhibitId");
        if (action == null || exhibitIdRaw == null) {
            response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp");
            return;
        }

        try {
            int exhibitId = Integer.parseInt(exhibitIdRaw);
            String status = "APPROVED".equalsIgnoreCase(action) ? "APPROVED" : "REJECTED";
            new ExhibitionDAO().updateStatus(exhibitId, status);
            response.sendRedirect(request.getContextPath() + "/admin/approval");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid exhibition ID.");
        } catch (DatabaseException e) {
            throw new ServletException("Error updating exhibition status.", e);
        }
    }
}
