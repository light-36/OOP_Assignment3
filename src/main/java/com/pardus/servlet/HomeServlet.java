package com.pardus.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Home servlet that handles requests to the application's home page
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Set some attributes that can be used in the JSP
        request.setAttribute("pageTitle", "Java Web Application");
        request.setAttribute("welcomeMessage", "Welcome to our Java Web Application!");
        
        // Forward the request to the JSP page
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Handle POST requests if needed
        doGet(request, response);
    }
}
