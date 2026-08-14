package com.recipestore.recipe_manager_api.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.recipestore.recipe_manager_api.service.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/*
What a request is

An HTTP request is a text message the browser sends your server. Literally:

POST /api/recipes HTTP/1.1          ← method + path
Host: localhost:8080                 ← headers start
Authorization: Bearer eyJhbGciOi...
Content-Type: application/json
Origin: http://localhost:4200
                                     ← blank line
{"title": "Tacos", "servings": 4}    ← body

Four parts: method (GET/POST/PUT/DELETE), path, headers (metadata as key-value pairs), and an 
optional body (the JSON payload).

Spring wraps that text in an HttpServletRequest object — that's your request parameter. 
request.getHeader("Authorization") just reads one of those header lines.

What a filter is

Code that runs before your controller, gets the whole request, and can inspect it, modify it, or stop it. 
Spring Security is essentially a stack of these.

The three you asked about

Filter	Question it answers	Failure
CORS	Is this browser origin allowed to call me?	403, blocked by browser
Your JwtFilter	Is there a valid token? If so, who is this?	Passes through anonymous
Authorization	Does this identity have permission for this endpoint?	401 or 403

ORDER MATTERS: Authentication must run before authorization, because you can't check someone's
permissions before knowing who they are.

The full path of one request:
Browser sends POST /api/recipes with Bearer token
  ↓ CORS filter — origin allowed? yes, continue
  ↓ JwtFilter — token valid? yes → set SecurityContext = "ethan, ROLE_USER"
  ↓ Authorization filter — /api/recipes needs auth; is anyone set? yes → continue
  ↓ RecipeController.create() runs
  ↓ Response travels back out through the filters

Authentication vs authorization is Q31 and Q68 on their list,
and this walkthrough is the answer. Worth getting solid.
  
*/


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService){
        this.jwtService = jwtService;

    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException{

            System.out.println("JwtFilter running...");
            String authHeader = request.getHeader("Authorization"); // pull token from the authorization header

            System.out.println("Authorization header: " + authHeader);

            // Validate token from the Authorization header
            // if no token or not valid then dont authenticate, instead...
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);    // user not authenticated, pass request to next filter
                return;                                     // Stop This filter's work
                /*
                Reason why you pass off as unauthenticated is because some endpoints are public for ex
                api/auth/register and /api/auth/login have no token by definition. If your filter rejected
                every tokenless request, nobody could ever log in. Your filter's only job is "if there's a
                valid token, record who the user is." Deciding what requires authentication happens in 
                SecurityConfig.
                */
            }

            String token = authHeader.substring(7); // get token after the word 'Bearer' : eyJhbGciOi...

            if (jwtService.isTokenValid(token)) {
                String username = jwtService.extractUsername(token);
                String role = jwtService.extractRole(token);

                System.out.println("Username from token: " + username);
                System.out.println("Role from token: " + role);

                // token found and valid so set it to :  UsernamePasswordAuthenticationToken authToken
                // Basically create or set identity of user and role
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    List.of(new SimpleGrantedAuthority(role)) // gives the role of the authenticated user
                );

                // store identity and role of user so that controller can read data from SecurityContextHolder.
                /*
                Mechanically it's a ThreadLocal — storage scoped to the current thread. Since one HTTP request is handled by one thread, each request gets its own isolated context. Ethan's request and someone else's can't see each other's authentication even though they hit the same static SecurityContextHolder. Spring clears it when the request finishes.

                What reads it afterward:

                The authorization filter — "is anyone authenticated? do they have the required role?" If your line never ran, it sees nothing and returns 401.
                @PreAuthorize("hasRole('ADMIN')") — checks the authorities you put in that List.of(new SimpleGrantedAuthority(role)).
                Your controllers — @AuthenticationPrincipal or SecurityContextHolder.getContext().getAuthentication().getName() gets you the current username, which is how you'd fetch only this user's recipes.

                That last one is the practical payoff: it's how GET /api/recipes returns your recipes and not everyone's.
                */
                SecurityContextHolder.getContext().setAuthentication(authToken); 
        
            } else {
                System.out.println("Invalid JWT token"); // token not valid
            }

            // token was checked. If valid, SecurityContext now holds the identity.
            // If invalid, it's still empty. passes request to next filter in filterchain 
            // and responds travels back through filter. Either way the request keeps moving.
            // Your filter never rejects anything; it only records what it found.
            filterChain.doFilter(request, response);
    }

}
