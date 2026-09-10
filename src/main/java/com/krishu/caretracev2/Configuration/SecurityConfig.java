package com.krishu.caretracev2.Configuration;

import com.krishu.caretracev2.ClientRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtfilter;

    public SecurityConfig(JwtFilter jwtfilter) {
        this.jwtfilter = jwtfilter;
    }

    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain getFilter(HttpSecurity https){
        https.csrf(AbstractHttpConfigurer::disable);
        https.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        https.authorizeHttpRequests(request->request.requestMatchers("/auth/signup","/auth/signin")
                .permitAll().anyRequest().authenticated());
        https.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        return https.build();
    }
}
