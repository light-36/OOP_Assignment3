package com.pardus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Main class to start the embedded Jetty server
 */
public class WebAppServer {
    public static void main(String[] args) throws Exception {
        // Create a new server instance
        Server server = new Server(8080);

        // Create a web app context
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        context.setResourceBase("src/main/webapp");
        context.setParentLoaderPriority(true);

        // Add the web app to the server
        server.setHandler(context);

        try {
            // Start the server
            server.start();
            System.out.println("Server started on port 8080");
            System.out.println("Visit http://localhost:8080 to access the application");
            
            // Keep the server running until it's explicitly stopped
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
