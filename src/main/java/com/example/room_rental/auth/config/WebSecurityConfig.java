package com.example.room_rental.auth.config;

import com.example.room_rental.auth.service.jwts.jwts.JwtAuthEntryPoint;
import com.example.room_rental.auth.service.jwts.jwts.JwtAuthTokenFilter;
import com.example.room_rental.auth.service.jwts.userdetail.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permit OPTIONS requests
                .antMatchers("/auth/**").permitAll() // Allow access to authentication endpoints
                .antMatchers("/email/**").permitAll() // Allow access to email-related endpoints
                .antMatchers("/cookie/**").permitAll() // Allow access to cookie-related endpoints
                .antMatchers("/accommodation/public/get").permitAll() // Allow access to cookie-related endpoints
                .antMatchers("/user/getProfile", "/user/updateProfile", "/user/uploadImage").authenticated()
                .antMatchers("/user/**").permitAll() // Allow access to user-related endpoints
                .antMatchers("/accommodation/room/public/**").permitAll()
                .antMatchers("/accommodation/create","accommodation/room").hasAnyAuthority("ROLE_ADMIN", "ROLE_PM")
                .antMatchers("/accommodation/**").permitAll() // Allow access to user-related endpoints
                .anyRequest().authenticated() // Require authentication for other endpoints
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
