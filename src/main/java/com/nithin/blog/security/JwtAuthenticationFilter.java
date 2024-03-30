package com.nithin.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


// @Component
// @RequiredArgsConstructor  // This annotation injects the required dependencies via constructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final UserDetailsService userDetailsService;
//     private final JwtTokenHelper jwtTokenHelper;

//     @Override
//     protected void doFilterInternal(
//             @NonNull HttpServletRequest request,
//             @NonNull HttpServletResponse response,
//             @NonNull FilterChain filterChain)
//             throws ServletException, IOException {

//         String authToken = request.getHeader("Authorization");
//         String username = null;
//         String token = null;
        
//         if (authToken != null && authToken.startsWith("Bearer ")) {
//             token = authToken.substring(7);
//             try {
//                 username = this.jwtTokenHelper.extractUsername(token);
//             } catch (IllegalArgumentException e) {
//                 System.out.println("Unable to get the jwt token");
//             } catch (ExpiredJwtException ex) {
//                 System.out.println("JWT Token has expired");
//             } catch (MalformedJwtException e) {
//                 System.out.println("Invalid JWT Token");
//             }
//         }
        
//         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//             UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//             if (this.jwtTokenHelper.isTokenValid(token, userDetails)) {
//                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                         userDetails,
//                         null,
//                         userDetails.getAuthorities());
//                 usernamePasswordAuthenticationToken
//                         .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//             } else {
//                 System.out.println("Invalid or Expired JWT token");
//             }
//         } else {
//             System.out.println("username is null or context is not null");
//         }

//         filterChain.doFilter(request, response);
//     }
// }


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;



    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // 1 . get the token
        String authToken = request.getHeader("Authorization");

        // Bearer24243j3r2mjbm2
        System.out.println(authToken);

        String username = null;
        String token = null;
        if (authToken != null && authToken.startsWith("Bearer ")) {

            token = authToken.substring(7);
            try {
                username = this.jwtTokenHelper.extractUsername(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get the jwt token");
            } catch (ExpiredJwtException ex) {
                System.out.println("JWT Token has expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT Token");
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.isTokenValid(token, userDetails)) {
                // this means everything is working fine and now we should do authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            } else {
                System.out.println("Invalid or Expired JWT token");
            }
        } else {
            System.out.println("username is null or context is not null");
        }

        // once we get token, validate now

        filterChain.doFilter(request, response);
    }

}
