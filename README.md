# servlet-sandbox
A sandbox to test Java servlet features.

## Some notes about filters
Servlet 4.0 API package does now have two new filter types.

- GenericFilter
- HttpFilter

They act in a similar way than how GenericServlet and HttpServlet act with Servlets. GenericFilter is an abstract filter class that implements Filter and FilterConfig, while HttpFilter is an abstract HTTP convenient filter class that automatically casts Servlet{Request|Response} into a corresponding HttpServlet{Request|Response}.
