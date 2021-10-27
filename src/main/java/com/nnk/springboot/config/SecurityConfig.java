package com.nnk.springboot.config;

import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("testUser").password(passwordEncoder().encode("testUserPass")).roles("USER")
                .and()
                .withUser("testAdmin").password(passwordEncoder().encode("testAdminPass")).roles("ADMIN");
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/admin/**", "/app/secure/article-details").hasRole("ADMIN")
                    .antMatchers("/anonymous*").anonymous()
                    .antMatchers("/", "/app/login*", "/bidList/list", "/curvePoint/list", "/rating/list", "ruleName/list", "trade/list").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/app/login")
                    .usernameParameter("username").passwordParameter("password")
                    .loginProcessingUrl("/app/login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/app/login?error")
                    .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutUrl("/app-logout")
                    .permitAll()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/app/error");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**");
    }
}
