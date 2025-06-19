package com.pardus.dao;

import com.pardus.db.DatabaseConnection;
import com.pardus.model.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

    public LogDAO() {
        // No initialization needed
    }

    public int createLog(Log log) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int generatedId = -1;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO log (log_title, log_content, log_severity, status) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, log.getLogTitle());
            stmt.setString(2, log.getLogContent());
            stmt.setString(3, log.getLogSeverity());
            stmt.setString(4, log.getStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                    log.setLogId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating log entry: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }

        return generatedId;
    }

    /**
     * Get a log entry by its ID
     *
     * @param logId The ID of the log entry
     * @return The log entry or null if not found
     */
    public Log getLogById(int logId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Log log = null;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM log WHERE log_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, logId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                log = mapResultSetToLog(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving log entry: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }

        return log;
    }

    /**
     * Get all log entries
     *
     * @return List of all log entries
     */
    public List<Log> getAllLogs() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Log> logs = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM log ORDER BY create_timestamp DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all log entries: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }

        return logs;
    }

    /**
     * Get log entries by severity
     *
     * @param severity The severity level to filter by
     * @return List of log entries with the specified severity
     */
    public List<Log> getLogsBySeverity(String severity) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Log> logs = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM log WHERE log_severity = ? ORDER BY create_timestamp DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, severity);
            rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving log entries by severity: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }

        return logs;
    }

    /**
     * Get log entries by status
     *
     * @param status The status to filter by
     * @return List of log entries with the specified status
     */
    public List<Log> getLogsByStatus(String status) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Log> logs = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM log WHERE status = ? ORDER BY create_timestamp DESC";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving log entries by status: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }

        return logs;
    }

    /**
     * Update an existing log entry
     *
     * @param log The log entry to update
     * @return true if successful, false otherwise
     */
    public boolean updateLog(Log log) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE log SET log_title = ?, log_content = ?, log_severity = ?, status = ? WHERE log_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, log.getLogTitle());
            stmt.setString(2, log.getLogContent());
            stmt.setString(3, log.getLogSeverity());
            stmt.setString(4, log.getStatus());
            stmt.setInt(5, log.getLogId());

            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating log entry: " + e.getMessage());
        } finally {
            closeResources(null, stmt, conn);
        }

        return success;
    }

    /**
     * Delete a log entry by its ID
     *
     * @param logId The ID of the log entry to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteLog(int logId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "DELETE FROM log WHERE log_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, logId);

            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting log entry: " + e.getMessage());
        } finally {
            closeResources(null, stmt, conn);
        }

        return success;
    }

    /**
     * Search for log entries by title or content
     *
     * @param searchTerm The term to search for
     * @return List of matching log entries
     */
    public List<Log> searchLogs(String searchTerm) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Log> logs = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM log WHERE log_title LIKE ? OR log_content LIKE ? ORDER BY create_timestamp DESC";
            stmt = conn.prepareStatement(sql);
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            rs = stmt.executeQuery();

            while (rs.next()) {
                logs.add(mapResultSetToLog(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching log entries: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }

        return logs;
    }

    /**
     * Map a ResultSet row to a Log object
     *
     * @param rs The ResultSet containing log data
     * @return A Log object
     * @throws SQLException if a database access error occurs
     */
    private Log mapResultSetToLog(ResultSet rs) throws SQLException {
        Log log = new Log();
        log.setLogId(rs.getInt("log_id"));
        log.setLogTitle(rs.getString("log_title"));
        log.setLogContent(rs.getString("log_content"));
        log.setCreateTimestamp(rs.getTimestamp("create_timestamp"));
        log.setUpdateTimestamp(rs.getTimestamp("update_timestamp"));
        log.setLogSeverity(rs.getString("log_severity"));
        log.setStatus(rs.getString("status"));
        return log;
    }

    /**
     * Close database resources (ResultSet, Statement, and Connection)
     *
     * @param rs ResultSet to close
     * @param stmt Statement to close
     * @param conn Connection to close
     */
    private void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error closing ResultSet: " + e.getMessage());
            }
        }
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error closing PreparedStatement: " + e.getMessage());
            }
        }
        
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }
}
