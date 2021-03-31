package com.example.myweb;

import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "你好！";
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // Hello
        PrintWriter out = resp.getWriter();
        out.println("<html><meta charset=\"UTF-8\"><body>");
        out.println("<h1>" + message + "POST</h1>");
        out.println("</body></html>");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><meta charset=\"UTF-8\"><body>");
        out.println("<h1>" + message + "GET</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}