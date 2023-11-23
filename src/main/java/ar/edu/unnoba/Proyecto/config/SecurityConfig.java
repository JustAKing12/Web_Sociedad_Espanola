package ar.edu.unnoba.Proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
                        .requestMatchers("/autenticacion/**").permitAll()
                        .requestMatchers("/visitante/**").permitAll()
                        .requestMatchers("/administrador/**").permitAll()//authenticated() POR AHORA LO DEJO ASI PARA PRUEBAS
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/autenticacion/login")
                        .defaultSuccessUrl("/administrador/index", true)
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling //ESTO ESTA ACIENDO QUE SE GENEREN CONFLICTOS A LA HORA DE MOSTRAR QUE "EL USUARIO O CONTRANIA SON INCORRECTOS"
                        .authenticationEntryPoint((request, response, authException) ->
                                response.sendRedirect("/autenticacion/login?error=true"))// Redirige a login con parametro de error
                        .accessDeniedPage("/403") // Se cambia el error 403 por el mensaje en AutenticationController
                )
                .logout(logout -> logout
                        .logoutUrl("/salir") /* debe utilizarse con thymeleaf */
                        .logoutSuccessUrl("/login?salir")
                );
        return http.build();
    }
}