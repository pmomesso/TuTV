package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.*;
import ar.edu.itba.paw.webapp.auth.jwt.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.ws.rs.HttpMethod;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Bean
    public PasswordEncoder passwordEncoder(){
        return bCryptPasswordEncoder;
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://localhost:5000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/series/watchlist").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/series/{\\d+}/reviews").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/series/{\\d+}/reviews").permitAll()
                    .antMatchers(HttpMethod.POST,"/series/reviews/{\\d+}/comments").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/{\\d+}/reviews/{\\d+}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/reviews/comments").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/{\\d+}/seasons/{\\d+}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/{\\d+}/seasons/{\\d+}/episodes/{//d+}").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/users/{\\d+}/lists").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/users/{\\d+}/lists").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE,"/users/{\\d+}/lists/{\\d+}").hasRole("USER")
                    .antMatchers("/users/{\\d+}/notifications/**").authenticated()
                    .antMatchers(HttpMethod.PUT,"/users/{\\d+}").authenticated()
                    .antMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/users").permitAll()
                    .antMatchers("/**").permitAll()
                .and().exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(corsFilter(), SessionManagementFilter.class)
                .csrf()
                    .disable();

    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/");
    }

    private String getEncryptationKey() {
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("remember.key");
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new OurAuthenticationSuccessHandler());
        return jwtAuthenticationFilter;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}