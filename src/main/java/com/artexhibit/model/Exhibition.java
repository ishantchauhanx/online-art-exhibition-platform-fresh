package com.artexhibit.model;

import java.time.LocalDate;

public class Exhibition {
    private int exhibitId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private int createdBy;

    public Exhibition() {}

    public Exhibition(int exhibitId, String title, String description, LocalDate startDate,
                      LocalDate endDate, String status, int createdBy) {
        this.exhibitId = exhibitId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdBy = createdBy;
    }

    public int getExhibitId() { return exhibitId; }
    public void setExhibitId(int exhibitId) { this.exhibitId = exhibitId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
}
