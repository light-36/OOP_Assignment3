<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty log.logId ? 'Create New Log' : 'Edit Log'}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], 
        textarea, 
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 14px;
        }
        textarea {
            height: 150px;
            resize: vertical;
        }
        .button-group {
            margin-top: 20px;
        }
        button {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 10px;
        }
        button:hover {
            background-color: #45a049;
        }
        .cancel-button {
            background-color: #f44336;
        }
        .cancel-button:hover {
            background-color: #d32f2f;
        }
        .navigation {
            margin-bottom: 20px;
        }
        .navigation a {
            color: #2196F3;
            text-decoration: none;
            margin-right: 15px;
        }
        .navigation a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="navigation">
            <a href="<c:url value='/' />">Home</a>
            <a href="<c:url value='/logs' />">Back to Log List</a>
        </div>
        
        <h1>${empty log.logId ? 'Create New Log' : 'Edit Log'}</h1>
        
        <c:choose>
            <c:when test="${empty log.logId}">
                <form action="<c:url value='/logs' />" method="post">
            </c:when>
            <c:otherwise>
                <form action="<c:url value='/logs/update/${log.logId}' />" method="post">
            </c:otherwise>
        </c:choose>
            <c:if test="${not empty log.logId}">
                <input type="hidden" name="id" value="${log.logId}" />
            </c:if>

            <%-- Log title input --%>
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="logTitle" value="${log.logTitle}" required />
            </div>

            <%--Log Message input--%>
            <div class="form-group">
                <label for="message">Message:</label>
                <textarea id="message" name="logContent" required>${log.logContent}</textarea>
            </div>

            <%--Severity selection--%>
            <div class="form-group">
                <label for="severity">Severity:</label>
                <select id="severity" name="logSeverity" required>
                    <option value="INFO" ${log.logSeverity == 'INFO' ? 'selected' : ''}>INFO</option>
                    <option value="WARNING" ${log.logSeverity == 'WARNING' ? 'selected' : ''}>WARNING</option>
                    <option value="ERROR" ${log.logSeverity == 'ERROR' ? 'selected' : ''}>ERROR</option>
                </select>
            </div>

            <%--Status selection--%>
            <div class="form-group">
                <label for="status">Status:</label>
                <select id="status" name="status" required>
                    <option value="ACTIVE" ${log.status == 'ACTIVE' ? 'selected' : ''}>ACTIVE</option>
                    <option value="INACTIVE" ${log.status == 'INACTIVE' ? 'selected' : ''}>INACTIVE</option>
                </select>
            </div>

            <%--Add buttons for saving or canceling the form--%>
            <div class="button-group">
                <button type="submit">Save</button>
                <button type="button" class="cancel-button" onclick="window.location.href='<c:url value='/logs' />'">Cancel</button>
            </div>
        </form>
    </div>
</body>
</html>
