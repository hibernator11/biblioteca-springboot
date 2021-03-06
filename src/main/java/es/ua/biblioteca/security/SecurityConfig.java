package es.ua.biblioteca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
	@Override
    protected void configure(HttpSecurity http) throws Exception{
        
        http
		.authorizeRequests()
			//.antMatchers("/", "/home").permitAll()
			.antMatchers(HttpMethod.GET, "/api/book/**").permitAll()
			.antMatchers(HttpMethod.GET, "/searchBook").permitAll()
			.antMatchers(HttpMethod.POST, "/createBook").hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/createBook").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, "/api/book/").hasRole("ADMIN")
	        .antMatchers(HttpMethod.PUT, "/api/book/**").hasRole("ADMIN")
	        .antMatchers(HttpMethod.DELETE, "/api/book/**").hasRole("ADMIN")
			//.anyRequest().authenticated()
			.and()
		.formLogin()
			//.loginPage("/login")
			.permitAll()
			.and()
		.logout()
			.permitAll();
    }
  
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        
        auth.inMemoryAuthentication()
        .withUser("user").password("{noop}password").roles("USER")
        .and()
        .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }
}
