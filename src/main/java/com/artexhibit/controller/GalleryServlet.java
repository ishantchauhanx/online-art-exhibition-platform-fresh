package com.artexhibit.controller;

import com.artexhibit.dao.ArtworkDAO;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Artwork;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/gallery")
public class GalleryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Artwork> artworks = new ArtworkDAO().getAvailableArtworks();
            request.setAttribute("artworks", artworks);
            request.getRequestDispatcher("/gallery.jsp").forward(request, response);
        } catch (DatabaseException e) {
            throw new ServletException("Database error while loading gallery.", e);
        }
    }
}
