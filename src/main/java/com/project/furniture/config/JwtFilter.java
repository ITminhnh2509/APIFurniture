package com.project.furniture.config;


import com.project.furniture.model.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    //    @Value("${api.prefix}")
    private final String apiPrefix="api";
    private final UserDetailsService userDetailsService;
    private final JwtToken jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            System.out.println("Processing request: " + request.getMethod() + " " + request.getServletPath());
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "authHeader null or not started with Bearer");
                return;
            }
            final String token = authHeader.substring(7);
            final String username = jwtTokenUtil.extractUsername(token);
            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response); //enable bypass
        }catch (Exception e) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }
    }
    private boolean isBypassToken1(@NonNull HttpServletRequest request) {
//        final List<Pair<String, String>> bypassTokens = Arrays.asList(
////                Pair.of(String.format("%s/products**", apiPrefix), "GET"),
////                Pair.of(String.format("%s/categories**", apiPrefix), "GET"),
////                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
////                Pair.of(String.format("%s/users/refresh-token", apiPrefix), "POST"),
//               Pair.of(String.format("%s/user/login", apiPrefix), "POST"),
//                Pair.of(String.format("%s/user/register", apiPrefix), "POST"),
//                Pair.of(String.format("%s/furniture**", apiPrefix), "GET"),
//                Pair.of(String.format("%s/furniture**", apiPrefix), "POST"),
//                Pair.of(String.format("%s/furniture/**", apiPrefix), "POST")
//        );
//        for(Pair<String, String> bypassToken: bypassTokens) {
//            if (request.getServletPath().contains(bypassToken.getFirst()) &&
//                    request.getMethod().equals(bypassToken.getSecond())) {
//                return true;
//            }
//        }
//        return false;


        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/user/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/user/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/furniture(/.*)?", apiPrefix), "GET"),  // Matches /api/furniture and /api/furniture/anything
                Pair.of(String.format("%s/furniture(/.*)?", apiPrefix), "POST")  // Matches /api/furniture and /api/furniture/anything
        );

        for (Pair<String, String> bypassToken : bypassTokens) {
            String pathPattern = bypassToken.getFirst();
            if (request.getServletPath().matches(pathPattern)
                    && request.getMethod().equalsIgnoreCase(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("/%s/user/login", apiPrefix), "POST"),
                Pair.of(String.format("/%s/user/register", apiPrefix), "POST"),
                Pair.of(String.format("/%s/furniture/**", apiPrefix), "POST"),
                Pair.of(String.format("/%s/furniture/**", apiPrefix), "GET") ,// If needed
                Pair.of(String.format("/%s/admin/**", apiPrefix), "POST"),
                Pair.of(String.format("/%s/admin/**", apiPrefix), "GET") // If needed
        );

        String servletPath = request.getServletPath();
        String method = request.getMethod();

        // Debugging output
        System.out.println("Request Path: " + servletPath);
        System.out.println("Request Method: " + method);

        for (Pair<String, String> bypassToken : bypassTokens) {
            String pattern = bypassToken.getFirst();
            if (matchPattern(servletPath, pattern) && method.equalsIgnoreCase(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
    private boolean matchPattern(String path, String pattern) {
        // Change ** in pattern to regex to compare, with ^ for start anchor
        String regexPattern = "^" + pattern.replace("**", ".*");
        return path.matches(regexPattern);
    }
}
