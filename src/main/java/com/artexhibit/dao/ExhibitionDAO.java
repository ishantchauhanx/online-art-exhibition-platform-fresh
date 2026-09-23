package com.artexhibit.dao;

import com.artexhibit.config.DBConnection;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Exhibition;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionDAO {

    public void createExhibition(Exhibition exhibition) throws DatabaseException {
        String sql = "INSERT INTO exhibitions (title, description, start_date, end_date, status, created_by) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, exhibition.getTitle());
            ps.setString(2, exhibition.getDescription());
            ps.setDate(3, Date.valueOf(exhibition.getStartDate()));
            ps.setDate(4, Date.valueOf(exhibition.getEndDate()));
            ps.setString(5, exhibition.getStatus() == null ? "PENDING" : exhibition.getStatus());
            ps.setInt(6, exhibition.getCreatedBy());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error creating exhibition.", e);
        }
    }

    public List<Exhibition> getPendingExhibitions() throws DatabaseException {
        return getExhibitionsByStatus("PENDING");
    }

    public List<Exhibition> getApprovedExhibitions() throws DatabaseException {
        return getExhibitionsByStatus("APPROVED");
    }

    public void updateStatus(int exhibitId, String status) throws DatabaseException {
        String sql = "UPDATE exhibitions SET status = ? WHERE exhibit_id = ?";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, exhibitId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating exhibition status.", e);
        }
    }

    private List<Exhibition> getExhibitionsByStatus(String status) throws DatabaseException {
        String sql = "SELECT * FROM exhibitions WHERE status = ? ORDER BY start_date DESC";
        List<Exhibition> exhibitions = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    exhibitions.add(mapExhibition(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching exhibitions.", e);
        }

        return exhibitions;
    }

    private Exhibition mapExhibition(ResultSet rs) throws SQLException {
        Exhibition exhibition = new Exhibition();
        exhibition.setExhibitId(rs.getInt("exhibit_id"));
        exhibition.setTitle(rs.getString("title"));
        exhibition.setDescription(rs.getString("description"));
        exhibition.setStartDate(rs.getDate("start_date").toLocalDate());
        exhibition.setEndDate(rs.getDate("end_date").toLocalDate());
        exhibition.setStatus(rs.getString("status"));
        exhibition.setCreatedBy(rs.getInt("created_by"));
        return exhibition;
    }
}
