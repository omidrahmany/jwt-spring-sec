package ro.omid.jwtsample.config;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import ro.omid.jwtsample.enums.ExceptionCodeEnum;
import ro.omid.jwtsample.exception.UnauthenticatedException;
import ro.omid.jwtsample.util.ExceptionModel;
import ro.omid.jwtsample.util.SecurityConstant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    private final static Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Authentication authentication = getAuthentication(request, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (UnauthenticatedException exception) {
            exception.printStackTrace();
            response.addHeader("Exception-Code", ExceptionCodeEnum.UNAUTHENTICATED.toString());
//            chain.doFilter(request, response);
            return;
        }
        /*if (Objects.isNull(authentication)) {
            chain.doFilter(request, response);
            return;
        }*/


    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(SecurityConstant.TOKEN_HEADER);
        if (!ObjectUtils.isEmpty(token) && token.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            try {
                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(SecurityConstant.JWT_SECRET.getBytes())
                        .parseClaimsJws(token.replace("Bearer ", ""));
                String username = parsedToken.getBody().getSubject();
                List<SimpleGrantedAuthority> authorities = ((List<String>) parsedToken
                        .getBody()
                        .get("rol"))
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                if (!ObjectUtils.isEmpty(username)) {
                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
                }
            } catch (ExpiredJwtException e) {
                LOG.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
            } catch (UnsupportedJwtException e) {
                LOG.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
            } catch (MalformedJwtException e) {
                LOG.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
            } catch (SignatureException e) {
                LOG.warn("Request to parse invalid signature  : {} failed : {}", token, e.getMessage());
            } catch (IllegalArgumentException e) {
                LOG.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
            }
        }
        LOG.info("authenticated process failed.");
        throw new UnauthenticatedException(new ExceptionModel("UNAUTHENTICATED",
                HttpStatus.NON_AUTHORITATIVE_INFORMATION));
    }
}
