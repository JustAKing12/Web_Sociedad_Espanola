package ar.edu.unnoba.Proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService1) {
        this.userDetailsService = userDetailsService1;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/**").authenticated()
                        .requestMatchers("/imagenes/**").permitAll()  // permite la visualización de las imagenes
                        .requestMatchers("/autenticacion/**").permitAll()
                        .requestMatchers("/visitante/**").permitAll()
                        .requestMatchers("/administrador/**").authenticated()//authenticated() POR AHORA LO DEJO ASI PARA PRUEBAS
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/autenticacion/login")
                        .defaultSuccessUrl("/administrador/index", true)
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/salir") /* debe utilizarse con thymeleaf */
                        .logoutSuccessUrl("/login?salir")
                );
        return http.build();
    }



    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}