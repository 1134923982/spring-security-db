package com.example.springsecuritydb;

import com.example.springsecuritydb.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
//    @Bean
//    UserService userService(){
//        return new UserService();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                System.out.println("++++++++++++++++"+(String)charSequence);
                return (String)charSequence;
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                System.out.println("----------"+(String)charSequence+s+"------------");
                return s.equals((String)charSequence);
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/admin/**")
                .access("hasAnyRole('admin')")
                .antMatchers("/user/**")
                .access("hasAnyRole('admin','user')")
                .antMatchers("/db/**")
                .access("hasAnyRole('admin') and hasAnyRole('dba')")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("name")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication auth) throws IOException, ServletException {
                        Object principal = auth.getPrincipal();
                        resp.setContentType("application/json; charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 200);
                        map.put("msg", principal);
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        resp.setContentType("application/json; charset=utf-8");
                        PrintWriter out = resp.getWriter();
                        resp.setStatus(401);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 401);
                        if(e instanceof LockedException) {
                            map.put("msg", "账号被锁定，登录失败");
                        } else if(e instanceof BadCredentialsException){
                            map.put("msg", "账号或密码错误，登录失败");
                        } else if(e instanceof DisabledException){
                            map.put("msg", "账号禁用，登录失败");
                        } else if(e instanceof AccountExpiredException){
                            map.put("msg", "账号已过期，登录失败");
                        } else if(e instanceof CredentialsExpiredException){
                            map.put("msg", "账号已过期，登录失败");
                        }else {
                            map.put("msg", "登录失败");
                        }
                        ObjectMapper om = new ObjectMapper();
                        out.write(om.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException, ServletException {
                        res.sendRedirect("/login_page");
                    }
                })
                .and();
    }
}
