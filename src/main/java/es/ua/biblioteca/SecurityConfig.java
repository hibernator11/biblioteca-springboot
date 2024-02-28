package es.ua.biblioteca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();

        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("USER", "ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         
        http.cors()
          .and()
          .csrf().disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.DELETE, "/api/**").permitAll() // primero damos permisos al API
          .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
          .antMatchers(HttpMethod.POST, "/api/**").permitAll()
          .antMatchers(HttpMethod.GET, "/api/**").permitAll()
          .antMatchers("/login").permitAll() // permitimos acceso a login
          .antMatchers("/**").hasAnyRole("USER", "ADMIN") // a partir del login cualquier user debe tener estos roles
          .and()
            .formLogin()
            .failureUrl("/login")
            .permitAll()
          .and()
            .logout()
            .logoutSuccessUrl("/login")
            .permitAll();
    	
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}