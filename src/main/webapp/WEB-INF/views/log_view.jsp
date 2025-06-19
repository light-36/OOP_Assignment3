<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Log - ${log.logTitle}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            color: #333;
        }
        .btn {
            display: inline-block;
            padding: 8px 12px;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 5px;
            color: white;
        }
        .btn-primary {
            background-color: #4CAF50;
        }
        .btn-primary:hover {
            background-color: #45a049;
        }
        .btn-warning {
            background-color: #ff9800;
        }
        .btn-warning:hover {
            background-color: #e68a00;
        }
        .btn-danger {
            background-color: #f44336;
        }
        .btn-danger:hover {
            background-color: #da190b;
        }
        .nav {
            margin-bottom: 20px;
        }
        .nav a {
            margin-right: 10px;
        }
        .log-details {
            margin-top: 20px;
        }
        .log-details table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        .log-details th, .log-details td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .log-details th {
            width: 150px;
            background-color: #f2f2f2;
        }
        .log-content {
            margin-top: 20px;
            padding: 15px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 4px;
            white-space: pre-wrap;
        }
        .severity-info {
            background-color: #d1ecf1;
            color: #0c5460;
            padding: 2px 6px;
            border-radius: 3px;
        }
        .severity-warning {
            background-color: #fff3cd;
            color: #856404;
            padding: 2px 6px;
            border-radius: 3px;
        }
        .severity-error {
            background-color: #f8d7da;
            color: #721c24;
            padding: 2px 6px;
            border-radius: 3px;
        }
        .status-active {
            background-color: #d4edda;
            color: #155724;
            padding: 2px 6px;
            border-radius: 3px;
        }
        .status-inactive {
            background-color: #e2e3e5;
            color: #383d41;
            padding: 2px 6px;
            border-radius: 3px;
        }
        .actions {
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="nav">
            <a href="<c:url value='/'/>" class="btn btn-primary">Home</a>
            <a href="<c:url value='/home'/>" class="btn btn-primary">App Home</a>
            <a href="<c:url value='/logs'/>" class="btn btn-primary">Back to Logs</a>
        </div>

        <h1>Log Details</h1>

        <div class="log-details">
            <table>
                <tr>
                    <th>ID</th>
                    <td>${log.logId}</td>
                </tr>
                <tr>
                    <th>Title</th>
                    <td>${log.logTitle}</td>
                </tr>
                <tr>
                    <th>Severity</th>
                    <td>
                        <c:choose>
                            <c:when test="${log.logSeverity eq 'INFO'}">
                                <span class="severity-info">INFO</span>
                            </c:when>
                            <c:when test="${log.logSeverity eq 'WARNING'}">
                                <span class="severity-warning">WARNING</span>
                            </c:when>
                            <c:when test="${log.logSeverity eq 'ERROR'}">
                                <span class="severity-error">ERROR</span>
                            </c:when>
                            <c:otherwise>
                                ${log.logSeverity}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>
                        <c:choose>
                            <c:when test="${log.status eq 'ACTIVE'}">
                                <span class="status-active">ACTIVE</span>
                            </c:when>
                            <c:when test="${log.status eq 'INACTIVE'}">
                                <span class="status-inactive">INACTIVE</span>
                            </c:when>
                            <c:otherwise>
                                ${log.status}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th>Created</th>
                    <td><fmt:formatDate value="${log.createTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>Last Updated</th>
                    <td><fmt:formatDate value="${log.updateTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </table>

            <h2>Log Content</h2>
            <div class="log-content">
                ${log.logContent}
            </div>

            <div class="actions">
                <a href="<c:url value='/logs/edit/${log.logId}'/>" class="btn btn-warning">Edit</a>
                <a href="#" onclick="confirmDelete(${log.logId})" class="btn btn-danger">Delete</a>
            </div>
        </div>
    </div>

    <script>
        function confirmDelete(logId) {
            if (confirm('Are you sure you want to delete this log entry?')) {
                // Create a form and submit it
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = '<c:url value="/logs/delete/"/>' + logId;
                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</body>
</html>
