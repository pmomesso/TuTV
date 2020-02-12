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
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/users").hasRole("ADMIN")
                    .antMatchers("/viewSeason").hasRole("USER")
                    .antMatchers("/unviewSeason").hasRole("USER")
                    .antMatchers("/viewEpisode").hasRole("USER")
                    .antMatchers("/unviewEpisode").hasRole("USER")
                    .antMatchers("/addSeries").hasRole("USER")
                    .antMatchers("/unfollowSeries").hasRole("USER")
                    .antMatchers("/likePost").hasRole("USER")
                    .antMatchers("/unlikePost").hasRole("USER")
                    .antMatchers("/rate").hasRole("USER")
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