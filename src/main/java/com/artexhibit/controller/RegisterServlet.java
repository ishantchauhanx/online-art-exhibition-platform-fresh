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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = ValidationUtil.trimToNull(request.getParameter("name"));
        String email = ValidationUtil.trimToNull(request.getParameter("email"));
        String password = request.getParameter("password");
        String role = ValidationUtil.trimToNull(request.getParameter("role"));

        if (ValidationUtil.isBlank(name) || ValidationUtil.isBlank(email)
                || ValidationUtil.isBlank(password) || ValidationUtil.isBlank(role)) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("error", "Please enter a valid email address.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        try {
            User existingUser = userDAO.findByEmail(email);
            if (existingUser != null) {
                request.setAttribute("error", "This email is already registered.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role.toUpperCase());

            userDAO.registerUser(user);

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
            throw new ServletException("Database error during registration.", e);
        }
    }
}
