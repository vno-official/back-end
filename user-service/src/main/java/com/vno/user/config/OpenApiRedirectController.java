package com.vno.user.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/api/user")
public class OpenApiRedirectController {

    @GetMapping("/v3/api-docs")
    public void apiDocs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the grouped docs path produced by GroupedOpenApi (group name:
        // user)
        request.getRequestDispatcher("/v3/api-docs/user").forward(request, response);
    }

    @GetMapping("/v3/api-docs/**")
    public void apiDocsWildcard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward any nested /v3/api-docs/* path to the underlying springdoc endpoint
        String uri = request.getRequestURI(); // e.g. /api/user/v3/api-docs/user
        String forwardTo = uri.replaceFirst("/api/user", ""); // -> /v3/api-docs/user
        request.getRequestDispatcher(forwardTo).forward(request, response);
    }

    @GetMapping("/v3/api-docs/swagger-config")
    public void swaggerConfig(HttpServletResponse response) throws IOException {
        // Redirect to the global swagger-config endpoint, instructing it to use the
        // 'user' group
        response.sendRedirect("/v3/api-docs/swagger-config?configUrl=/api/user/v3/api-docs");
    }

    @GetMapping("/swagger-ui.html")
    public void swaggerUi(HttpServletResponse response) throws IOException {
        // Redirect to swagger UI, pointing it to the user group docs
        response.sendRedirect("/swagger-ui/index.html?configUrl=/api/user/v3/api-docs");
    }
}
