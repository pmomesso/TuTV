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

import javax.ws.rs.HttpMethod;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                    .antMatchers("/users").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET,"/series/watchlist").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/series/{\\d+}/reviews").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/series/reviews/{\\d+}/comments").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/{\\d+}/reviews/{\\d+}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/reviews/comments").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/{\\d+}/seasons/{\\d+}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/series/{\\d+}/seasons/{\\d+}/episodes/{//d+}").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/series/{\\d+}/follows").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE,"/series/{\\d+}/follows").hasRole("USER")
                    .antMatchers(HttpMethod.POST,"/users/{\\d+}/lists").hasRole("USER")
                    .antMatchers(HttpMethod.PUT,"/users/{\\d+}/lists").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE,"/users/{\\d+}/lists/{\\d+}").hasRole("USER")
                    .antMatchers("/users/login").anonymous()
                    .antMatchers("/users/register").anonymous()
                    .antMatchers("/**").permitAll()
                .and().exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf()
                    .disable();

    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**")
                .antMatchers("/users/mailconfirm");
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