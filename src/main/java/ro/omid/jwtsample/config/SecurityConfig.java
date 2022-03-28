package ro.omid.jwtsample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ro.omid.jwtsample.util.SecurityConstant;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${cross-origin-domains}")
    private String crossOriginDomain;

    private List<String> allowedOrigins;

    @PostConstruct
    public void postConstruct() {
        allowedOrigins = new ArrayList<>();
        allowedOrigins = Arrays.asList(this.crossOriginDomain.split(","));
    }

    private static final String DEFAULT_PASSWORD = new
            BCryptPasswordEncoder().encode("test@123");

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/public").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("omid").password(DEFAULT_PASSWORD).roles("ADMIN")
                .and()
                .withUser("rahman").password(DEFAULT_PASSWORD).roles("USER")
                .and()
                .withUser("yasin").password(DEFAULT_PASSWORD).roles("MANAGER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        /** All domains which are allowed to send request to here, server side. */
        configuration.setAllowedOrigins(allowedOrigins);

        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));

        /**
         * setAllowCredentials(true) is important, otherwise:
         * The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the
         * request's credentials mode is 'include'. */
        configuration.setAllowCredentials(true);

        /** setAllowedHeaders is important! Without it, OPTIONS preflight request
         will fail with 403 Invalid CORS request*/
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        /** allow header "SecurityConstant.TOKEN_HEADER" to be read by clients */
        configuration.addExposedHeader(SecurityConstant.TOKEN_HEADER);
        configuration.addExposedHeader("Exception-Code");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());

        return source;
    }
}
