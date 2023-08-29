package springREST.com.example.springREST.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import springREST.com.example.springREST.service.CustomAdminDetailsService;
import springREST.com.example.springREST.service.CustomUserDetailsService;
import springREST.com.example.springREST.service.JwtService;

import java.io.IOException;

@Component
public class jwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
//
//    @Autowired
//    @Lazy
//    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService service;

    @Autowired
    private CustomAdminDetailsService adminDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtService.extractUsername(token);
            System.out.println(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = service.loadUserByUsername(userName);
            if (userDetails == null) {
                userDetails = adminDetailsService.loadUserByUsername(userName);
            }

            if (jwtService.isTokenValid(token, userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
