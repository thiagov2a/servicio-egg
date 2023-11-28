package com.equipo15.servicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.equipo15.servicio.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        
        auth.userDetailsService(usuarioServicio)
                
                .passwordEncoder(new BCryptPasswordEncoder());
        
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                
                .authorizeRequests()
                    .antMatchers("/admin/*").hasRole("ADMIN")
                    .antMatchers("/css/*","/js/*", "/img/*", "/**")
                    .permitAll()
                .and().formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/logincheck")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll().
                and().csrf()
                    .disable();
                
                     
        
    }
    
    
    
    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> {
                    // authz.requestMatchers("/admin/*")
                    // .hasRole("ADMIN");
                    // authz.requestMatchers("/", "/login", "/registrar", "/registro",
                    // "/servicio/registrar",
                    // "/servicio/registro")
                    // .permitAll();
                    authz.requestMatchers("/css/**", "/js/**", "/img/**", "/**")
                            .permitAll();
                    // authz.anyRequest()
                    // .authenticated();
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
<<<<<<< Updated upstream
    }
}

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import
// org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SeguridadWeb extends WebSecurityConfigurerAdapter {

// @Autowired
// public UsuarioServicio usuarioServicio;

// @Autowired
// public void configureGlobal(AuthenticationManagerBuilder auth) throws
// Exception {
// auth.userDetailsService(usuarioServicio).passwordEncoder(new
// BCryptPasswordEncoder());
// }

// @Override
// protected void configure(HttpSecurity http) throws Exception {
// http
// .authorizeRequests()
// .antMatchers("/admin/*").hasRole("ADMIN")
// .antMatchers("/css/*", "/js/*", "/img/*", "/**")
// .permitAll()
// .and().formLogin()
// .loginPage("/login")
// .loginProcessingUrl("/logincheck")
// .usernameParameter("email")
// .passwordParameter("password")
// .defaultSuccessUrl("/inicio")
// .permitAll()
// .and().logout()
// .logoutUrl("/logout")
// .logoutSuccessUrl("/login")
// .permitAll()
// .and().csrf()
// .disable();
// }
// }
=======
    }*/
}
>>>>>>> Stashed changes
