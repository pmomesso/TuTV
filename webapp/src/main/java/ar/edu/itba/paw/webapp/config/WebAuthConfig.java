package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.*;
import ar.edu.itba.paw.webapp.auth.basic.BasicAuthenticationProvider;
import ar.edu.itba.paw.webapp.auth.jwt.JwtAuthenticationProvider;
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

import javax.ws.rs.HttpMethod;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BasicAuthenticationProvider basicAuthenticationProvider;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private OurAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private OurAuthenticationFailureHandler authenticationFailureHandler;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Bean
    public PasswordEncoder passwordEncoder(){
        return bCryptPasswordEncoder;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(basicAuthenticationProvider).authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                    .antMatchers(HttpMethod.PUT,"/api/series/{\\d+}/rating").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/api/series/{\\d+}/reviews").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/api/series/{\\d+}/reviews").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/series/reviews/{\\d+}/comments").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/api/series/{\\d+}/reviews/{\\d+}/like").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/api/series/{\\d+}/reviews/{\\d+}/comments/{\\d+}/like").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/api/series/{\\d+}/seasons/{\\d+}/viewed").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/api/series/{\\d+}/seasons/{\\d+}/episodes/{\\d+}/viewed").hasRole("USER")
                    .antMatchers("/api/users/{\\d+}/notifications/**").hasRole("USER")
                    .antMatchers("/api/users/{\\d+}/following/**").hasRole("USER")
                    .antMatchers("/api/users/{\\d+}/lists/{\\d+}/**").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/api/users/{\\d+}/lists").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE,"/api/users/{\\d+}/lists/{\\d+}").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/api/users/{\\d+}/watchlist").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/api/users/{\\d+}/stats").hasRole("USER")
                    .antMatchers(HttpMethod.GET,"/api/users/{\\d+}/upcoming").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/api/users/{\\d+}/username").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/api/users/{\\d+}/banned").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/api/user").hasRole("USER")
                    .antMatchers("/**").permitAll()
                .and().exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                    .addFilterBefore(ourAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf()
                    .disable();

    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/");
    }

    @Bean
    public OurAuthenticationFilter ourAuthenticationFilter() throws Exception {
        OurAuthenticationFilter ourAuthenticationFilter = new OurAuthenticationFilter();
        ourAuthenticationFilter.setAuthenticationManager(authenticationManager());
        ourAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        ourAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return ourAuthenticationFilter;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}