<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1, h2 {
            color: #333;
        }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f5f5f5;
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
        .btn-info {
            background-color: #2196F3;
        }
        .btn-info:hover {
            background-color: #0b7dda;
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
        .filters {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .filters select, .filters input[type="text"] {
            padding: 8px;
            margin-right: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .filters button {
            padding: 8px 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .filters button:hover {
            background-color: #45a049;
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
    </style>
</head>
<body>
    <div class="container">
        <div class="nav">
            <a href="<c:url value='/'/>" class="btn btn-primary">Home</a>
            <a href="<c:url value='/home'/>" class="btn btn-primary">App Home</a>
        </div>

        <h1>Log Management</h1>

        <!-- Display success or error messages -->
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="message success">
                <p>${sessionScope.successMessage}</p>
            </div>
            <c:remove var="successMessage" scope="session" />
        </c:if>

        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="message error">
                <p>${sessionScope.errorMessage}</p>
            </div>
            <c:remove var="errorMessage" scope="session" />
        </c:if>

        <!-- Create new log button -->
        <div style="margin-bottom: 20px;">
            <a href="<c:url value='/logs/create'/>" class="btn btn-primary">Create New Log</a>
        </div>

        <!-- Log entries table -->
        <c:choose>
            <c:when test="${not empty logs}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Severity</th>
                            <th>Status</th>
                            <th>Created</th>
                            <th>Updated</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="log" items="${logs}">
                            <tr>
                                <td>${log.logId}</td>
                                <td>${log.logTitle}</td>
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
                                <td><fmt:formatDate value="${log.createTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td><fmt:formatDate value="${log.updateTimestamp}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                <td>
                                    <a href="<c:url value='/logs/view/${log.logId}'/>" class="btn btn-info">View</a>
                                    <a href="<c:url value='/logs/edit/${log.logId}'/>" class="btn btn-warning">Edit</a>
                                    <a href="#" onclick="confirmDelete(${log.logId})" class="btn btn-danger">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No log entries found.</p>
            </c:otherwise>
        </c:choose>
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
