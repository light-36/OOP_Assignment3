package com.pardus.model;

import java.sql.Timestamp;

public class Log {
    private int logId;
    private String logTitle;
    private String logContent;
    private Timestamp createTimestamp;
    private Timestamp updateTimestamp;
    private String logSeverity;
    private String status;
    
    // Default constructor
    public Log() {
        this.status = "ACTIVE";
    }
    
    // Constructor with required fields
    public Log(String logTitle, String logContent) {
        this.logTitle = logTitle;
        this.logContent = logContent;
        this.status = "ACTIVE";
    }
    
    // Constructor with all fields except timestamps
    public Log(String logTitle, String logContent, String logSeverity, String status) {
        this.logTitle = logTitle;
        this.logContent = logContent;
        this.logSeverity = logSeverity;
        this.status = status != null ? status : "ACTIVE";
    }
    
    // Full constructor
    public Log(int logId, String logTitle, String logContent, Timestamp createTimestamp, 
               Timestamp updateTimestamp, String logSeverity, String status) {
        this.logId = logId;
        this.logTitle = logTitle;
        this.logContent = logContent;
        this.createTimestamp = createTimestamp;
        this.updateTimestamp = updateTimestamp;
        this.logSeverity = logSeverity;
        this.status = status;
    }
    
    // Getters and Setters
    public int getLogId() {
        return logId;
    }
    
    public void setLogId(int logId) {
        this.logId = logId;
    }
    
    public String getLogTitle() {
        return logTitle;
    }
    
    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }
    
    public String getLogContent() {
        return logContent;
    }
    
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }
    
    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }
    
    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }
    
    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }
    
    public void setUpdateTimestamp(Timestamp updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
    
    public String getLogSeverity() {
        return logSeverity;
    }
    
    public void setLogSeverity(String logSeverity) {
        this.logSeverity = logSeverity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Log{" +
                "logId=" + logId +
                ", logTitle='" + logTitle + '\'' +
                ", logContent='" + logContent + '\'' +
                ", createTimestamp=" + createTimestamp +
                ", updateTimestamp=" + updateTimestamp +
                ", logSeverity='" + logSeverity + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
