package com.artexhibit.controller;

import com.artexhibit.dao.UserDAO;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.User;
import com.artexhibit.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = ValidationUtil.trimToNull(request.getParameter("email"));
        String password = request.getParameter("password");

        if (ValidationUtil.isBlank(email) || ValidationUtil.isBlank(password)) {
            request.setAttribute("error", "Email and password are required.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Invalid email format.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findByEmail(email);

            if (user == null || !password.equals(user.getPassword())) {
                request.setAttribute("error", "Invalid email or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/adminDashboard.jsp");
            } else if ("ARTIST".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/artistDashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/gallery");
            }

        } catch (DatabaseException e) {
            throw new ServletException("Database error during login.", e);
        }
    }
}
