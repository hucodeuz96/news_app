package hucode.news_app.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import hucode.news_app.exception.GlobalHandlerException;
import hucode.news_app.exception.ResourceNotFoundException;
import hucode.news_app.service.AuthService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    public String sessionToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.substring(7);
            sessionToken = token;
            try{
                String email = jwtProvider.getEmailFromToken(token);
                UserDetails userDetails = authService.loadUserByUsername(email);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
            } catch (ExpiredJwtException e){
                response.setStatus(400);
                ErrorMessage jwt_expired = new ErrorMessage("Jwt expired");
                logger.error(jwt_expired.getMessage()+"  "+jwt_expired.getId());
                new ObjectMapper().writeValue(response.getWriter(),jwt_expired);
                return;
            }catch (SignatureException e){
                response.setStatus(400);
                ErrorMessage jwt_expired = new ErrorMessage("Jwt invalid");
                logger.error(jwt_expired.getMessage()+"  "+jwt_expired.getId());
                new ObjectMapper().writeValue(response.getWriter(),jwt_expired);
                return;
            }catch (Exception e){
                response.setStatus(400);
                ErrorMessage jwt_expired = new ErrorMessage(e.getMessage());
                logger.error(jwt_expired.getMessage()+"  "+jwt_expired.getId());
                new ObjectMapper().writeValue(response.getWriter(),jwt_expired);
                return;
            }
        }
        doFilter(request, response, filterChain);
    }

}
