package com.artexhibit.dao;

import com.artexhibit.config.DBConnection;
import com.artexhibit.exception.DatabaseException;
import com.artexhibit.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public void submitFeedback(Feedback feedback) throws DatabaseException {
        String sql = "INSERT INTO feedback (user_id, artwork_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, feedback.getUserId());
            ps.setInt(2, feedback.getArtworkId());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComment());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error submitting feedback.", e);
        }
    }

    public List<Feedback> getFeedbackForArtwork(int artworkId) throws DatabaseException {
        String sql = "SELECT * FROM feedback WHERE artwork_id = ? ORDER BY submitted_at DESC";
        List<Feedback> feedbackList = new ArrayList<>();

        try (Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, artworkId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setFeedbackId(rs.getInt("feedback_id"));
                    feedback.setUserId(rs.getInt("user_id"));
                    feedback.setArtworkId(rs.getInt("artwork_id"));
                    feedback.setRating(rs.getInt("rating"));
                    feedback.setComment(rs.getString("comment"));
                    feedback.setSubmittedAt(rs.getString("submitted_at"));
                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving artwork feedback.", e);
        }

        return feedbackList;
    }
}
