package com.pardus;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Main class to start the embedded Tomcat server
 */
public class WebAppServer {
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws Exception {
        // Check if port is available
        checkPortAvailability(PORT);
        
        // Create Tomcat instance
        Tomcat tomcat = new Tomcat();
        configureTomcat(tomcat);
        
        try {
            // Start the server
            tomcat.start();
            System.out.println("Server started on port " + PORT);
            System.out.println("Visit http://localhost:" + PORT + " to access the application");
            
            // Keep the server running until it's explicitly stopped
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            System.err.println("Failed to start Tomcat server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Check if the port is available
     * 
     * @param port The port to check
     * @throws IOException If the port is not available
     */
    private static void checkPortAvailability(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Port " + port + " is available");
        } catch (IOException e) {
            System.err.println("Port " + port + " is already in use. Please choose another port or stop the process using this port.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Configure the Tomcat server
     * 
     * @param tomcat The Tomcat instance to configure
     * @throws IOException If an I/O error occurs
     */
    private static void configureTomcat(Tomcat tomcat) throws IOException {
        // Set port
        tomcat.setPort(PORT);
        
        // Create temp directory for Tomcat
        Path tempPath = Files.createTempDirectory("tomcat-base-dir");
        tomcat.setBaseDir(tempPath.toString());
        System.out.println("Tomcat base directory: " + tempPath);
        
        // Configure connector explicitly
        Connector connector = new Connector();
        connector.setPort(PORT);
        connector.setThrowOnFailure(true);
        tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);
        
        // Define web application directory
        String webappDir = new File("src/main/webapp").getAbsolutePath();
        System.out.println("Web application directory: " + webappDir);
        
        // Add web application
        Context context = tomcat.addWebapp("", webappDir);
        System.out.println("Context path: " + context.getPath());
        
        // Set up class loader
        File classesDir = new File("target/classes");
        if (classesDir.exists()) {
            System.out.println("Classes directory: " + classesDir.getAbsolutePath());
            WebResourceRoot resources = new StandardRoot(context);
            resources.addPreResources(new DirResourceSet(
                    resources, "/WEB-INF/classes", classesDir.getAbsolutePath(), "/"));
            context.setResources(resources);
        } else {
            System.err.println("Classes directory does not exist: " + classesDir.getAbsolutePath());
        }
        
        // Disable Tomcat's default JSP development mode for production-like settings
        context.setReloadable(false);
    }
}
