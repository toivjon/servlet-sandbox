# servlet-sandbox
A sandbox to test Java servlet features.

## Some notes about filters
Servlet 4.0 API package does add two new filter abstractions.

- GenericFilter
- HttpFilter

They act in a similar way than how GenericServlet and HttpServlet act with Servlets. GenericFilter is an abstract filter class that implements Filter and FilterConfig, while HttpFilter is an abstract HTTP convenient filter class which extends GenericFilter and automatically casts Servlet{Request|Response} into a corresponding HttpServlet{Request|Response}.

## Some notes about servlet contexts
Servlet 4.0 API does add the following features in servlet contexts.

- {Get|Set} session timeout.
- {Get|Set} request character encoding.
- {Get|Set} response character encoding.
- Programmatically add a JSP page.


