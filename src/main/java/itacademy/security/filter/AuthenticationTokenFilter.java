package itacademy.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import itacademy.dto.response.ErrorResponseDTO;
import itacademy.service.security.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private TokenService tokenService;
    private UserDetailsService userDetailsService;

    public AuthenticationTokenFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpServletResponse, FilterChain chain) throws IOException, ServletException {
        try {
            String token = getToken(httpRequest);
            if (token != null && tokenService.validate(token)) {
                String username = tokenService.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(httpRequest, httpServletResponse);
        } catch (Exception e) {
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.BAD_REQUEST, e.getMessage());
            httpServletResponse.setStatus(errorResponseDTO.getHttpStatus().value());
            httpServletResponse.setContentType("application/json");
            new ObjectMapper().writeValue(httpServletResponse.getWriter(), errorResponseDTO);
        }

    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        return authHeader != null && authHeader.startsWith(BEARER)
                ? authHeader.replace(BEARER, "") : authHeader;
    }
}
