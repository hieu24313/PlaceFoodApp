/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nmhieu.configs;

import com.nmhieu.filters.CustomAccessDeniedHandler;
import com.nmhieu.filters.JwtAuthenticationTokenFilter;
import com.nmhieu.filters.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author HP
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.nmhieu.controllers",
    "com.nmhieu.repository",
    "com.nmhieu.service",
    "com.nmhieu.components"})
@Order(1)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
        JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
        jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return jwtAuthenticationTokenFilter;
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/api/**");
        http.authorizeRequests().antMatchers("/api/server/**").permitAll();
        http.authorizeRequests().antMatchers("/api/login/").permitAll();
        http.authorizeRequests().antMatchers("/api/login-google/").permitAll();
        http.authorizeRequests().antMatchers("/api/register/").permitAll();
        http.authorizeRequests().antMatchers("/api/current-user/").permitAll();
        http.authorizeRequests().antMatchers("/api/categories/").permitAll();
        http.authorizeRequests().antMatchers("/api/foodItems/").permitAll();
//        http.authorizeRequests().antMatchers("/api/foodItems/**").permitAll();
        http.authorizeRequests().antMatchers("/api/restaurants/").permitAll();
//        http.authorizeRequests().antMatchers("/api/restaurants/**").permitAll();
        http.authorizeRequests().antMatchers("/api/**/comments/").permitAll();
//        http.authorizeRequests().antMatchers("/api/admin/restaurants/**").permitAll();

//        http.authorizeRequests().antMatchers("/admin/**").hasRole("Admin");
//        http.authorizeRequests().antMatchers("/api/admin/users/").permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/**/comments/").permitAll();
        http.antMatcher("/api/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                //                .antMatchers(HttpMethod.GET, "/api/**").access("hasRole('ROLE_Admin') or hasRole('ROLE_RestaurantManager') or hasRole('ROLE_User')")
                .antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ROLE_Admin') or hasRole('ROLE_User') or hasRole('ROLE_RestaurantManager')")
                .antMatchers(HttpMethod.GET, "/api/admin/**").access("hasRole('ROLE_Admin')")
                .antMatchers(HttpMethod.POST, "/api/admin/**").access("hasRole('ROLE_Admin')")
                .antMatchers(HttpMethod.DELETE, "/api/admin/**").access("hasRole('ROLE_Admin')")
                .antMatchers(HttpMethod.GET, "/api/restaurantManager/**").access("hasRole('ROLE_RestaurantManager')")
                .antMatchers(HttpMethod.POST, "/api/restaurantManager/**").access("hasRole('ROLE_RestaurantManager')")
                .antMatchers(HttpMethod.DELETE, "/api/restaurantManager/**").access("hasRole('ROLE_RestaurantManager')").and()
                //                .antMatchers(HttpMethod.GET, "/api/restaurantManager/**").access("hasRole('ROLE_RestaurantManager')")
                //                .antMatchers(HttpMethod.POST, "/api/**").access("hasRole('ROLE_Admin') or hasRole('ROLE_User')")
                //                .antMatchers(HttpMethod.DELETE, "/api/**").access("hasRole('ROLE_Admin') or hasRole('ROLE_User')").and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
    }
}
