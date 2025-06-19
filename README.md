# Java Web Application

This is a simple Java web application built using core Java dependencies. The application uses:

- Java Servlet API
- JSP (JavaServer Pages)
- JSTL (JavaServer Pages Standard Tag Library)
- Embedded Jetty Server

## Project Structure

```
log/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── pardus/
│   │   │           ├── App.java
│   │   │           ├── WebAppServer.java
│   │   │           └── servlet/
│   │   │               └── HomeServlet.java
│   │   └── webapp/
│   │       ├── index.jsp
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           └── views/
│   │               └── home.jsp
│   └── test/
└── pom.xml
```

## How to Run

You can run the application in two ways:

### 1. Using the Embedded Jetty Server

```bash
mvn compile exec:java -Dexec.mainClass="com.pardus.WebAppServer"
```

### 2. Using the Jetty Maven Plugin

```bash
mvn jetty:run
```

Once the server is running, access the application at:
- http://localhost:8080

## Features

- Simple web application structure
- Servlet-based request handling
- JSP views with JSTL support
- Modern UI with responsive design

## Development

To modify the application:

1. Edit servlets in `src/main/java/com/pardus/servlet/`
2. Edit JSP views in `src/main/webapp/WEB-INF/views/`
3. Add new servlets by creating new classes in the servlet package and annotating them with `@WebServlet`

## Dependencies

The application uses the following core Java dependencies:

- javax.servlet-api (4.0.1)
- javax.servlet.jsp-api (2.3.3)
- jstl (1.2)
- Jetty Server (9.4.48.v20220622)
