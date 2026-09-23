package com.artexhibit.model;

public class Feedback {
    private int feedbackId;
    private int userId;
    private int artworkId;
    private int rating;
    private String comment;
    private String submittedAt;

    public Feedback() {}

    public Feedback(int feedbackId, int userId, int artworkId, int rating, String comment, String submittedAt) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.artworkId = artworkId;
        this.rating = rating;
        this.comment = comment;
        this.submittedAt = submittedAt;
    }

    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getArtworkId() { return artworkId; }
    public void setArtworkId(int artworkId) { this.artworkId = artworkId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(String submittedAt) { this.submittedAt = submittedAt; }
}
