package com.equipo15.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.equipo15.servicio.servicios.UsuarioServicio;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {

    @Autowired
    public UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> {
                    authz.requestMatchers("/admin/*", "/transaccion/modificar/*")
                            .hasRole("ADMIN");
                    authz.requestMatchers("/", "/login", "/registrar", "/registro", "/error")
                            .permitAll();
                    authz.requestMatchers("/resources/**", "/static/**", "/css/**", "/img/**", "/js/**")
                            .permitAll();
                    authz.anyRequest()
                            .authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/login");
                    form.loginProcessingUrl("/logincheck");
                    form.usernameParameter("email");
                    form.passwordParameter("password");
                    form.defaultSuccessUrl("/", true);
                    form.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/login");
                    logout.permitAll();
                })
                .csrf(CSRF -> {
                    CSRF.disable();
                })
                .build();
    }
}