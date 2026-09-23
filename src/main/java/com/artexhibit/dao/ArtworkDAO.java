package com.artexhibit.dao;

import com.artexhibit.config.DBConnection;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Artwork;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtworkDAO implements Repository<Artwork> {

    public void saveArtwork(Artwork artwork) throws DatabaseException {
        String sql = "INSERT INTO artworks (artist_id, title, description, medium, price, image_url, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, artwork.getArtistId());
            ps.setString(2, artwork.getTitle());
            ps.setString(3, artwork.getDescription());
            ps.setString(4, artwork.getMedium());
            ps.setBigDecimal(5, artwork.getPrice());
            ps.setString(6, artwork.getImageUrl());
            ps.setString(7, artwork.getStatus() == null ? "AVAILABLE" : artwork.getStatus());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error saving artwork.", e);
        }
    }

    public List<Artwork> getAvailableArtworks() throws DatabaseException {
        String sql = "SELECT * FROM artworks WHERE status = 'AVAILABLE' ORDER BY created_at DESC";
        return fetchArtworks(sql, null);
    }

    public List<Artwork> getArtworksByArtist(int artistId) throws DatabaseException {
        String sql = "SELECT * FROM artworks WHERE artist_id = ? ORDER BY created_at DESC";
        return fetchArtworks(sql, artistId);
    }

    public Artwork getArtworkById(int artworkId) throws DatabaseException {
        String sql = "SELECT * FROM artworks WHERE art_id = ?";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, artworkId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapArtwork(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching artwork by id.", e);
        }
        return null;
    }

    public void markAsSold(int artworkId) throws DatabaseException {
        String sql = "UPDATE artworks SET status = 'SOLD' WHERE art_id = ?";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, artworkId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error marking artwork as sold.", e);
        }
    }

    public List<Artwork> getAllArtworks() throws DatabaseException {
        return fetchArtworks("SELECT * FROM artworks ORDER BY created_at DESC", null);
    }

    @Override
    public List<Artwork> findAll() throws DatabaseException {
        return getAllArtworks();
    }

    private List<Artwork> fetchArtworks(String sql, Integer artistId) throws DatabaseException {
        List<Artwork> artworks = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (artistId != null) {
                ps.setInt(1, artistId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    artworks.add(mapArtwork(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving artworks.", e);
        }

        return artworks;
    }

    private Artwork mapArtwork(ResultSet rs) throws SQLException {
        Artwork artwork = new Artwork();
        artwork.setArtId(rs.getInt("art_id"));
        artwork.setArtistId(rs.getInt("artist_id"));
        artwork.setTitle(rs.getString("title"));
        artwork.setDescription(rs.getString("description"));
        artwork.setMedium(rs.getString("medium"));
        artwork.setPrice(rs.getBigDecimal("price"));
        artwork.setImageUrl(rs.getString("image_url"));
        artwork.setStatus(rs.getString("status"));
        artwork.setCreatedAt(rs.getString("created_at"));
        return artwork;
    }
}
