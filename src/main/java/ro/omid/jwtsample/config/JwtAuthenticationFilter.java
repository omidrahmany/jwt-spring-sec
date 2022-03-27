package ro.omid.jwtsample.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.omid.jwtsample.util.SecurityConstant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(SecurityConstant.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
//        byte[] jwtSecretBytes = SecurityConstant.JWT_SECRET.getBytes();
        String token = Jwts.builder()
//                .signWith(Keys.hmacShaKeyFor(jwtSecretBytes),SignatureAlgorithm.HS512)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SECRET.getBytes())
                .setHeaderParam("typ", SecurityConstant.TOKEN_TYPE)
                .setIssuer(SecurityConstant.TOKEN_ISSUER)
                .setSubject(user.getUsername())
                .setAudience(SecurityConstant.TOKEN_AUDIENCE)
                .setExpiration(new Date(System.currentTimeMillis() + 1000000000))
                .claim("rol", roles)
                .compact();


        response.addHeader("authorization", "Bearer ".concat(token));
        response.addHeader("param1", "XXXXXXXX");
        response.addHeader("param2", "YYYYYYYY");
    }
}



/*
        response.addHeader("Access-Control-Expose-Headers","authorization, param1, param2");

        response.addHeader("authorization", "Bearer ".concat(token));
        response.addHeader("param1", "XXXXXXXX");
        response.addHeader("param2", "YYYYYYYY");
*/

//        response.addHeader("Access-Control-Allow-Headers"," Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Custom-header");





/*response.addHeader("Access-Control-Expose-Headers"," My-Fucking-Header, Another-Header, ".concat(SecurityConstant.TOKEN_HEADER) );
        response.addHeader("My-Fucking-Header", "XXXXXXXXXXYYYYYYYYYYYYYYY");
        response.addHeader("Another-Header", "OOOOOOOOOOOOOOOOOOOOPS");*/
