package com.school.school_api.model;

public class UploadStatus {

    private boolean inProgress;
    private int totalRecords;
    private int processedRecords;
    private String statusMessage;

    public UploadStatus() {
        this.inProgress = true;
        this.totalRecords = 0;
        this.processedRecords = 0;
        this.statusMessage = "Processing started...";
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getProcessedRecords() {
        return processedRecords;
    }

    public void setProcessedRecords(int processedRecords) {
        this.processedRecords = processedRecords;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}