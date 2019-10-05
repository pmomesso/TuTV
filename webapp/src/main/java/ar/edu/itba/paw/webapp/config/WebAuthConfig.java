package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.UrlAuthenticationFailureHandler;
import ar.edu.itba.paw.webapp.auth.UserDetailsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService)
                .sessionManagement()
                    .invalidSessionUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/login").anonymous()
                    .antMatchers("/**").permitAll()
                .and().formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false)
                    .loginPage("/login")
                    .failureHandler(new UrlAuthenticationFailureHandler())
                .and().rememberMe()
                    .rememberMeParameter("rememberme")
                    .userDetailsService(userDetailsService).key(getEncryptationKey()) // TODO	no	hacer	esto,	crear	una aleatoria	segura	suficiente mente	grande	y	colocarla	bajo	src/main/resources
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                    .accessDeniedPage("/403")
                .and().csrf()
                    .disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    private String getEncryptationKey() {
        return "mysupersecretketthatnobodyknowsabout ";

//        InputStream iStream = getClass()
//                .getClassLoader().getResourceAsStream("cryptoKey.key");
//        try {
//            return IOUtils.toString(iStream, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            throw new RuntimeException();
//        }
    }
}