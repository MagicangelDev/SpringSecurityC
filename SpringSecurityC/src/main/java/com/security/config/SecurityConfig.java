package com.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//Se le indica que es confiugracion de spring security
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //Configuracion ONE
    //Interfaz para comportamiento de seguridad SPRING SECURITY
    //csrf -> Cross-Site Request Forgery (Vulnerabilidad app webs FORMS, LOGIN)
  @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/v1/index2").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }


    //Configuration TW0
 /*   @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> {
                        auth.requestMatchers("v1/index2").permitAll();
                        auth.anyRequest().authenticated();
                })
                .formLogin()
                    .successHandler(successHandler()) // URL a donde se va redirigir despues de iniciar SESION
                    .permitAll()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                // ALWAYS - IF_REQUIRED - NEVER - STALESS
                    .invalidSessionUrl("/login")
                    .maximumSessions(1)
                    .expiredUrl("/login") //Tiempo de inactividad
                    .sessionRegistry(sessionRegistry())
                .and()
                .sessionFixation()
                    .migrateSession() //Genera un nuevo id de sesion
                //migrateSession - newSession - none
                .and()
                .httpBasic() //basic auth (se manda en el header)
                .and()
                .build();
    }*/

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
            response.sendRedirect("/v1/session");
        });
    }
}
