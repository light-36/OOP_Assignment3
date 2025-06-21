package com.pardus.servlet;

import com.pardus.dao.LogDAO;
import com.pardus.model.Log;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to handle log management operations
 */
@WebServlet("/logs/*")
public class LogManagementServlet extends HttpServlet {

    private LogDAO logDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        logDAO = new LogDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // List all logs
            handleListLogs(request, response);
        } else if (pathInfo.startsWith("/view/")) {
            // View a specific log
            handleViewLog(request, response);
        } else if (pathInfo.equals("/create")) {
            // Show create log form
            request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/edit/")) {
            // Show edit log form
            handleEditForm(request, response);
        } else {
            // Invalid path
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Create a new log
            handleCreateLog(request, response);
        } else if (pathInfo.startsWith("/update/")) {
            // Update an existing log
            handleUpdateLog(request, response);
        } else if (pathInfo.startsWith("/delete/")) {
            // Delete a log
            handleDeleteLog(request, response);
        } else if (pathInfo.equals("/save")) {
            // Alternative endpoint for saving (both create and update)
            handleSaveLog(request, response);
        } else {
            // Invalid path
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handle listing all logs
     */
    private void handleListLogs(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Log> logs = logDAO.getAllLogs();

        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/WEB-INF/views/log_list.jsp").forward(request, response);
    }

    /**
     * Handle viewing a specific log
     */
    private void handleViewLog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Extract log ID from path
            String pathInfo = request.getPathInfo();
            String logIdStr = pathInfo.substring("/view/".length());
            int logId = Integer.parseInt(logIdStr);

            // Get log by ID
            Log log = logDAO.getLogById(logId);

            if (log != null) {
                request.setAttribute("log", log);
                request.getRequestDispatcher("/WEB-INF/views/log_view.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Log not found");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid log ID");
        }
    }

    /**
     * Handle showing the edit form for a log
     */
    private void handleEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Extract log ID from path
            String pathInfo = request.getPathInfo();
            String logIdStr = pathInfo.substring("/edit/".length());
            int logId = Integer.parseInt(logIdStr);

            // Get log by ID
            Log log = logDAO.getLogById(logId);

            if (log != null) {
                request.setAttribute("log", log);
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Log not found");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid log ID");
        }
    }

    /**
     * Handle creating a new log
     */
    private void handleCreateLog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String logTitle = request.getParameter("logTitle");
        String logContent = request.getParameter("logContent");
        String logSeverity = request.getParameter("logSeverity");
        String status = request.getParameter("status");

        // Validate required fields
        if (logTitle == null || logTitle.trim().isEmpty() ||
            logContent == null || logContent.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Title and content are required");
            request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
            return;
        }

        // Create log object
        Log log = new Log(logTitle, logContent, logSeverity, status);

        // Save to database
        int logId = logDAO.createLog(log);

        if (logId > 0) {
            // Redirect to view the created log
            response.sendRedirect(request.getContextPath() + "/logs/view/" + logId);
        } else {
            // Show error
            request.setAttribute("errorMessage", "Failed to create log entry");
            request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
        }
    }

    /**
     * Handle updating an existing log
     */
    private void handleUpdateLog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Extract log ID from path
            String pathInfo = request.getPathInfo();
            String logIdStr = pathInfo.substring("/update/".length());
            int logId = Integer.parseInt(logIdStr);

            // Get form parameters
            String logTitle = request.getParameter("logTitle");
            String logContent = request.getParameter("logContent");
            String logSeverity = request.getParameter("logSeverity");
            String status = request.getParameter("status");

            // Validate required fields
            if (logTitle == null || logTitle.trim().isEmpty() ||
                logContent == null || logContent.trim().isEmpty()) {

                request.setAttribute("errorMessage", "Title and content are required");
                request.setAttribute("isEdit", true);
                request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
                return;
            }

            // Create log object
            Log log = new Log();
            log.setLogId(logId);
            log.setLogTitle(logTitle);
            log.setLogContent(logContent);
            log.setLogSeverity(logSeverity);
            log.setStatus(status);

            // Update in database
            boolean success = logDAO.updateLog(log);

            if (success) {
                // Redirect to view the updated log
                response.sendRedirect(request.getContextPath() + "/logs/view/" + logId);
            } else {
                // Show error
                request.setAttribute("errorMessage", "Failed to update log entry");
                request.setAttribute("isEdit", true);
                request.setAttribute("log", log);
                request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid log ID");
        }
    }
    
    /**
     * Handle saving a log (both create and update)
     */
    private void handleSaveLog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if this is an update (has ID) or create (no ID)
        String logIdStr = request.getParameter("id");
        
        if (logIdStr != null && !logIdStr.trim().isEmpty()) {
            // This is an update
            try {
                int logId = Integer.parseInt(logIdStr);
                
                // Get form parameters
                String logTitle = request.getParameter("logTitle");
                String logContent = request.getParameter("logContent");
                String logSeverity = request.getParameter("logSeverity");
                String status = request.getParameter("status");
                
                // Validate required fields
                if (logTitle == null || logTitle.trim().isEmpty() ||
                    logContent == null || logContent.trim().isEmpty()) {
                    
                    request.setAttribute("errorMessage", "Title and content are required");
                    request.setAttribute("isEdit", true);
                    request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
                    return;
                }
                
                // Create log object
                Log log = new Log();
                log.setLogId(logId);
                log.setLogTitle(logTitle);
                log.setLogContent(logContent);
                log.setLogSeverity(logSeverity);
                log.setStatus(status);
                
                // Update in database
                boolean success = logDAO.updateLog(log);
                
                if (success) {
                    // Redirect to view the updated log
                    response.sendRedirect(request.getContextPath() + "/logs/view/" + logId);
                } else {
                    // Show error
                    request.setAttribute("errorMessage", "Failed to update log entry");
                    request.setAttribute("isEdit", true);
                    request.setAttribute("log", log);
                    request.getRequestDispatcher("/WEB-INF/views/log_form.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid log ID");
            }
        } else {
            // This is a create
            handleCreateLog(request, response);
        }
    }

    /**
     * Handle deleting a log
     */
    private void handleDeleteLog(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Extract log ID from path
            String pathInfo = request.getPathInfo();
            String logIdStr = pathInfo.substring("/delete/".length());
            int logId = Integer.parseInt(logIdStr);

            // Delete from database
            boolean success = logDAO.deleteLog(logId);

            // Redirect to log list with appropriate message
            if (success) {
                request.getSession().setAttribute("successMessage", "Log entry deleted successfully");
            } else {
                request.getSession().setAttribute("errorMessage", "Failed to delete log entry");
            }

            response.sendRedirect(request.getContextPath() + "/logs");
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid log ID");
        }
    }
}
