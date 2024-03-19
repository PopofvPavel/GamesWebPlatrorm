package com.example.webprojectgames.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery("SELECT username, role_name FROM users INNER JOIN roles ON users.role_id = roles.role_id WHERE username=?");

    /* auth
            .inMemoryAuthentication()
            .withUser("user").password("{noop}password").roles("USER"); */
    }

    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/static/**", "/css/**",
                        "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/users/**").hasAnyRole("EDITOR","USER")
                .antMatchers("/games", "/games/{id:\\d+}", "/games/{id:\\d+}/rate","/games/{id:\\d+}/add-review",
                        "/games/collection", "/games/search","/games/{id:\\d+}/save-to-collection")
                    .hasAnyRole("USER", "EDITOR")
                .antMatchers("/games/**").hasRole("EDITOR")
                .antMatchers("/moderator/**").hasRole("MODERATOR")
                .antMatchers("/static/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }
}
