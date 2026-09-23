package com.artexhibit.model;

import java.math.BigDecimal;

public class Artwork {
    private int artId;
    private int artistId;
    private String title;
    private String description;
    private String medium;
    private BigDecimal price;
    private String imageUrl;
    private String status;
    private String createdAt;

    public Artwork() {}

    public Artwork(int artId, int artistId, String title, String description, String medium,
                   BigDecimal price, String imageUrl, String status, String createdAt) {
        this.artId = artId;
        this.artistId = artistId;
        this.title = title;
        this.description = description;
        this.medium = medium;
        this.price = price;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getArtId() { return artId; }
    public void setArtId(int artId) { this.artId = artId; }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getMedium() { return medium; }
    public void setMedium(String medium) { this.medium = medium; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
