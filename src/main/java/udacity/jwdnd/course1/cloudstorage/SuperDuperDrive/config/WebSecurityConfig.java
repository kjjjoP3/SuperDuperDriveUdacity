package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.UserDetailService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailService userDetailService;

    @Autowired
    public WebSecurityConfig(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests((rq) -> {
                    rq.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");
                    rq.antMatchers("/**").access("hasRole('ROLE_USER')");
                    rq.antMatchers("/**").permitAll();
                    rq.anyRequest().authenticated();
                })
                .formLogin()
                .loginProcessingUrl("/signup")
                .loginPage("/login")
                .defaultSuccessUrl("/?login_success")
                .successHandler(new SuccessHandler()).failureUrl("/login?error=true")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**"); // #3
    }

}
