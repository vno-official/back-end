package com.vno.auth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Small controller that exposes the auth service OpenAPI docs and UI under the
 * requested prefix: /api/auth/* -> forwards/redirects to the actual springdoc
 * endpoints.
 */
@Controller
@RequestMapping("/api/auth")
public class OpenApiRedirectController {

    @GetMapping("/v3/api-docs")
    public void apiDocs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to the grouped docs path produced by GroupedOpenApi (group name:
        // auth)
        request.getRequestDispatcher("/v3/api-docs/auth").forward(request, response);
    }

    @GetMapping("/v3/api-docs/**")
    public void apiDocsWildcard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward any nested /v3/api-docs/* path to the underlying springdoc endpoint
        String uri = request.getRequestURI(); // e.g. /api/auth/v3/api-docs/auth
        String forwardTo = uri.replaceFirst("/api/auth", ""); // -> /v3/api-docs/auth
        request.getRequestDispatcher(forwardTo).forward(request, response);
    }

    @GetMapping("/v3/api-docs/swagger-config")
    public void swaggerConfig(HttpServletResponse response) throws IOException {
        // Redirect to the global swagger-config endpoint, instructing it to use the
        // 'auth' group
        response.sendRedirect("/v3/api-docs/swagger-config?configUrl=/api/auth/v3/api-docs");
    }

    @GetMapping("/swagger-ui.html")
    public void swaggerUi(HttpServletResponse response) throws IOException {
        // Redirect to swagger UI, pointing it to the auth group docs
        response.sendRedirect("/swagger-ui/index.html?configUrl=/api/auth/v3/api-docs");
    }
}
